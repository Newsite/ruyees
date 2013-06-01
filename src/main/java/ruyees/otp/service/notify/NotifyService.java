package ruyees.otp.service.notify;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.action.view.model.VNotify;
import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.common.utils.ComUtils;
import ruyees.otp.model.flow.BamCourseware;
import ruyees.otp.model.flow.BamCoursewareItem;
import ruyees.otp.model.flow.BamIndex;
import ruyees.otp.model.flow.BamPackage;
import ruyees.otp.model.flow.Grade;
import ruyees.otp.model.notify.NotifyAcceptUser;
import ruyees.otp.model.notify.NotifyEnum;
import ruyees.otp.model.notify.NotifyTodo;
import ruyees.otp.model.notify.NotifyTypeEnum;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.BaseService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;

/**
 * 
 * 消息service
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:39:58
 */
@Service
@Transactional(readOnly = false)
public class NotifyService extends BaseService {
	@Autowired
	private NotifyTodoService notifyTodoService;

	/**
	 * 获取用户的消息
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	public Pagination findNotifyByUid(String userId, int pageNo) {
		Finder finder = Finder.create("from NotifyTodo n");

		Pagination page = getPage(finder, pageNo);

		return page;
	}

	/**
	 * 获取用户发送的消息
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@Transactional(readOnly = false)
	public Pagination findNotifyByUidOfSent(String userId, int pageNo,
			String notifyType) {
		Finder finder = Finder
				.create("select n from NotifyTodo n left join fetch n.acceptUser u where n.sendUser.fdId = :userId");
		if (notifyType != null && !notifyType.equals(NotifyTypeEnum.ALL.name())) {
			finder.append(" and n.notifyTypeEnum = :notifyType ").setParam(
					"notifyType", NotifyTypeEnum.valueOf(notifyType));
		}
		finder.setParam("userId", userId);
		finder.append("order by u.sendTime desc");
		Pagination page = getPage(finder, pageNo);
		return page;
	}

	/**
	 * 获取用户接收的消息
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	public Pagination findNotifyByUidOfReceived(String userId, int pageNo,
			String notifyType) {
		Finder finder = Finder.create("from NotifyAcceptUser na");
		if (notifyType != null && !notifyType.equals(NotifyTypeEnum.ALL.name())) {
			finder.append(" left join fetch na.notifyTodo todo where todo.notifyTypeEnum = :notifyType and na.person.fdId = :userId");
			finder.setParam("notifyType", NotifyTypeEnum.valueOf(notifyType));
			finder.setParam("userId", userId);
		} else {
			finder.append(" where na.person.fdId = :userId").setParam("userId",
					userId);
		}
		finder.append("order by na.sendTime desc");
		Pagination page = getPage(finder, pageNo);
		setUnreadByUid(userId);
		return page;
	}

	@Transactional(readOnly = false)
	public void deleteNotifyById(String fdId) {

	}

	@SuppressWarnings("unchecked")
	private void setUnreadByUid(String userId) {
		Finder finder = Finder
				.create("from NotifyAcceptUser na where na.isRead = :isRead and na.person.fdId = :userId");
		finder.setParam("isRead", false);
		finder.setParam("userId", userId);
		List<NotifyAcceptUser> list = find(finder);
		if (list != null && list.size() > 0) {
			for (NotifyAcceptUser notifyAcceptUser : list) {
				notifyAcceptUser.setIsRead(true);
				update(notifyAcceptUser);
			}
		}
	}

	/**
	 * 获取用户接收的消息数
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUnreadNotifyCount(String userId) {
		Finder finder = Finder.create("from NotifyAcceptUser na where na.isRead = :isRead and na.person.fdId = :userId");
		finder.setParam("isRead", false);
		finder.setParam("userId", userId);

		List<NotifyAcceptUser> list = find(finder);

		if (list != null && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 设置消息已读
	 * 
	 * @param fdId
	 * @return
	 */
	public void setRead(String fdId) {
		NotifyAcceptUser notifyAcceptUser = getNotifyAcceptUser(fdId);

		notifyAcceptUser.setIsRead(true);

		save(notifyAcceptUser);
	}

	/**
	 * 获取消息信息
	 * 
	 * @param fdId
	 * @return
	 */
	public VNotify getVNotify(String fdId) {
		VNotify vNotify = new VNotify();
		NotifyAcceptUser notifyAcceptUser = this.getNotifyAcceptUser(fdId);

		vNotify.setFdId(notifyAcceptUser.getFdId());
		vNotify.setTitle(notifyAcceptUser.getNotifyTodo().getTitle());
		vNotify.setContent(notifyAcceptUser.getNotifyTodo().getBody());
		vNotify.setReveicer(notifyAcceptUser.getPerson().getRealName());
		vNotify.setSender(notifyAcceptUser.getNotifyTodo().getSendUser()
				.getRealName());
		if (notifyAcceptUser.getSendTime() != null) {
			vNotify.setTime(notifyAcceptUser.getSendTime().toString());
		}

		return vNotify;
	}

	/**
	 * 获取消息信息
	 * 
	 * @param fdId
	 * @return
	 */
	public NotifyAcceptUser getNotifyAcceptUser(String fdId) {
		Finder finder = Finder
				.create("from NotifyAcceptUser n where n.fdId = :fdId");
		finder.setParam("fdId", fdId);

		return findUnique(finder);
	}

	/**
	 * 指导老师驳回作业、课件时给新教师发消息
	 * 
	 * @param fdId
	 * @return
	 */
	public void guidToDoNewTeach(Object obj, int index) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String sendId = user.getId();
		SysOrgPerson sendUser = (SysOrgPerson) findUniqueByProperty(
				SysOrgPerson.class, Value.eq("fdId", sendId));

		String newId = "";
		String body = "";
		String href = "";
		switch (index) {
		case 1:
			BamPackage bamPack = (BamPackage) obj;
			newId = bamPack.getPhase().getNewteachId();
			body = "作业包:" + bamPack.getFdName() + "中的所有作业指标都被驳回,请查看";
			break;
		case 2:
			BamIndex bamObj = (BamIndex) obj;
			newId = bamObj.getBamOperation().getBamPackage().getPhase()
					.getNewteachId();
			href = "http://otp.xdf.cn/otp/trainee/main/"
					+ bamObj.getBamOperation().getBamPackage().getPhase()
							.getStage().getProjectId() + "/2/1";
			body = "作业包:"
					+ bamObj.getBamOperation().getBamPackage().getFdName()
					+ "中,作业指标 :" + bamObj.getFdIndexName() + "被驳回,请<a href="
					+ href + ">查看</a>";
			break;
		case 3:
			BamCourseware bamCour = (BamCourseware) obj;
			int stage = bamCour.getPhase().getStageIndex();
			newId = bamCour.getPhase().getNewteachId();
			if (stage == 4) {
				body = "课件审核中，所有课件都被驳回,请查看";
				break;
			} else {
				body = "课件确认中，所有课件都被驳回,请查看";
				break;
			}
		case 4:
			BamCoursewareItem bamItem = (BamCoursewareItem) obj;
			newId = bamItem.getBamCourseware().getPhase().getNewteachId();
			href = "http://otp.xdf.cn/otp/trainee/main/"
					+ bamItem.getBamCourseware().getPhase().getStage()
							.getProjectId() + "/4/1";
			body = "课件审核中，课件:" + bamItem.getName() + "被驳回,请<a href=" + href
					+ ">查看</a>";
			break;
		case 5:
			Grade grade = (Grade) obj;
			newId = grade.getUid();
			href = "http://otp.xdf.cn/otp/trainee/main/"
					+ grade.getPhase().getStage().getProjectId() + "/5/2";
			body = "你的批课已结束，快去给你的批课老师<a href=" + href + ">打分</a>吧！";
			break;
		case 6:
			BamCoursewareItem bam6 = (BamCoursewareItem) obj;
			newId = bam6.getBamCourseware().getPhase().getNewteachId();
			href = "http://otp.xdf.cn/otp/trainee/main/"
					+ bam6.getBamCourseware().getPhase().getStage()
							.getProjectId() + "/6/1";
			body = "课件确认中，课件:" + bam6.getName() + "被驳回,请<a href=" + href
					+ ">查看</a>";
			break;
		}
		String title = "驳回通知";
		if (index == 5) {
			title = "打分通知";
		}
		SysOrgPerson person = (SysOrgPerson) findUniqueByProperty(
				SysOrgPerson.class, Value.eq("fdId", newId));

		NotifyTodo notifyTodo = new NotifyTodo();
		List<NotifyAcceptUser> accepList = new ArrayList<NotifyAcceptUser>();
		NotifyAcceptUser accept = new NotifyAcceptUser();
		accept.setSendTime(ComUtils.now());
		accept.setNotifyTodo(notifyTodo);
		accept.setPerson(person);
		accepList.add(accept);
		notifyTodo.setTitle(title);
		notifyTodo.setBody(body);
		notifyTodo.setSendUser(sendUser);
		notifyTodo.setAcceptUser(accepList);
		notifyTodo.setNotifyEnum(NotifyEnum.MESSAGE);
		notifyTodo.setNotifyTypeEnum(NotifyTypeEnum.TODO);
		save(notifyTodo);
	}

	@Transactional(readOnly = true)
	public void save(NotifyTodo notifyTodo) {
		super.save(notifyTodo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<NotifyTodo> getEntityClass() {
		return NotifyTodo.class;
	}

}
