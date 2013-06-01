package ruyees.otp.service.base;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ruyees.otp.action.view.model.VGrade;
import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Updater;
import ruyees.otp.common.hibernate4.Updater.UpdateMode;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.common.json.JsonUtils;
import ruyees.otp.common.utils.TimeUtils;
import ruyees.otp.model.base.EvalItem;
import ruyees.otp.model.flow.BamProjectMember;
import ruyees.otp.model.flow.BamProjectPhase;
import ruyees.otp.model.flow.Grade;
import ruyees.otp.model.flow.GradeItem;
import ruyees.otp.model.flow.Tickling;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;
import ruyees.otp.service.flow.BamBaseService;
import ruyees.otp.service.flow.TraineeService;
import ruyees.otp.service.notify.NotifyService;

/**
 * 
 * 批课计划
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:27:58
 */
@Service
@Transactional(readOnly = true)
public class GradeService extends BamBaseService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TraineeService traineeService;

	@Autowired
	private GradeItemService gradeItemService;

	@Autowired
	private NotifyService notifyService;

	/**
	 * 更新新教师得分
	 * 
	 * @param model
	 * @param Grade
	 *            批课计划
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateGradeValue(Grade grade) {
		// update GradeItem
		DecimalFormat df = new DecimalFormat("#.#");
		Float totalValue = new Float(0);
		List<GradeItem> items = grade.getGradeItems();
		for (GradeItem item : items) {
			totalValue += item.getValue();
			Updater<GradeItem> updater1 = new Updater<GradeItem>(item);
			item.setValue(Float.parseFloat(df.format(item.getValue())));
			updater1.setUpdateMode(UpdateMode.MIN);
			updater1.include("value");
			gradeItemService.updateByUpdater(updater1);
		}
		// update Grade
		Float avgValue = totalValue / items.size();
		grade.setValue(Float.parseFloat(df.format(avgValue)));
		Updater<Grade> updater2 = new Updater<Grade>(grade);
		updater2.setUpdateMode(UpdateMode.MIN);
		updater2.include("value");
		updater2.include("remark");
		grade = updateByUpdater(updater2);
		// 打分完给新教师发消息
		notifyService.guidToDoNewTeach(grade, 5);
	}

	/**
	 * 新教师给批课老师打完分之后更新步骤状态
	 * 
	 * @param model
	 * @param Grade
	 *            批课计划
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateGradeThrough(Grade grade) {
		Updater<Grade> updater = new Updater<Grade>(grade);
		grade.setThrough(true);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("through");
		updateGradeDuration(grade);// 更新批课时长
		updateByUpdater(updater);
	}

	/**
	 * 更新批课时长
	 * 
	 * @param model
	 * @param Grade
	 *            批课计划
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void updateGradeDuration(Grade grade) {
		Grade g = get(grade.getFdId());
		String newTeachId = g.getPhase().getNewteachId();
		BamProjectMember bamProjectMember = null;
		List<BamProjectMember> bamProjectMembers = g.getPhase().getStage()
				.getProject().getBamProjectMembers();
		for (BamProjectMember member : bamProjectMembers) {
			if (newTeachId.equals(member.getNewteachId())) {
				bamProjectMember = member;
				break;
			}
		}
		if (bamProjectMember != null) {
			Float gradeDuration = getGradeDuration(grade);
			bamProjectMember.setGradeDuration(gradeDuration);
			save(bamProjectMember);
		}
	}

	/**
	 * 获取批课时长
	 * 
	 * @param model
	 * @param Grade
	 *            批课计划
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Float getGradeDuration(Grade grade) {
		float gradeDuration = 0;

		Finder finder = Finder
				.create("from GradeItem gi where gi.grade.fdId = :fdId");
		finder.setParam("fdId", grade.getFdId());

		List<GradeItem> gradeItems = find(finder);
		for (GradeItem gradeItem : gradeItems) {
			gradeDuration += TimeUtils.getBetweenHours(
					gradeItem.getStartTime(), gradeItem.getEndTime());
		}

		return gradeDuration;
	}

	/**
	 * 第五关指导教师批课
	 * 
	 * @param model
	 * @param content
	 *            内容BamProjectPhase
	 * @param uId
	 *            新教师
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<GradeItem> getGradeItemList(BamProjectPhase content, String uId) {
		Finder finder = Finder
				.create("from GradeItem g left join fetch g.grade grade left join fetch grade.phase phase where g.uid=:uId and phase.fdId=:phaseId");
		finder.setParam("uId", uId);
		finder.setParam("phaseId", content.getFdId());
		finder.append("order by g.startTime asc");

		List<GradeItem> items = find(finder);
		for (GradeItem item : items) {
			item.setNewTeach(accountService.findById(item.getUid()));
			item.setAdvier(accountService.findById(item.getAdvierId()));
		}
		return items;
	}

	/**
	 * 
	 * @param model
	 * @param gradeId
	 * @return
	 */
	public Grade getGradeById(String gradeId) {
		return get(gradeId);
	}

	/**
	 * 新教师批课计划
	 * 
	 * @param model
	 * @param content
	 *            内容BamProjectPhase
	 * @param uId
	 *            新教师
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public Grade getGrade(BamProjectPhase content, String uId) {
		Finder finder = Finder
				.create("from Grade g left join fetch g.phase phase where g.uid=:uId and phase.fdId=:phaseId");
		finder.setParam("uId", uId);
		finder.setParam("phaseId", content.getFdId());

		List<Grade> lists = find(finder);
		if (lists != null && lists.size() > 0) {
			Grade grade = lists.get(0);
			for (Tickling tick : grade.getTicklings()) {
				tick.setAdvier(accountService.findById(tick.getGuidId()));
			}
			return grade;
		}
		return null;
	}

	/**
	 * 保存批果计划
	 * 
	 * @param Grade
	 * @param guidId
	 * @param projectId
	 * @param uid
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveGradeAndTickling(Grade grade, String guidId,
			String projectId, String uid) {
		BamProjectPhase phase = traineeService
				.findBamStep(uid, projectId, 5, 1);
		grade.setPhase(phase);
		grade.setGuidId(guidId);

		List<GradeItem> lists = grade.getGradeItems();
		for (GradeItem item : lists) {
			item.setUid(uid);
			item.setGrade(grade);
		}

		// 批课人单独处理
		/*
		 * List<Tickling> ticks = grade.getTicklings(); for (Tickling tick :
		 * ticks) { tick.setNewTeachId(uid); tick.setGrade(grade); }
		 */

		save(grade);
	}

	/**
	 * 第五关给指导教师评价分项
	 * 
	 * @param model
	 * @param content
	 *            内容BamProjectPhase
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<EvalItem> getEvalItemList(BamProjectPhase content) {
		Finder finder = Finder
				.create("from EvalItem e left join fetch e.templateContent content where content.fdId=:contentId");
		finder.setParam("contentId", content.getContent().getFdId());
		finder.append("order by e.fdIndex asc");
		List<EvalItem> evals = find(finder);
		return evals;
	}

	/**
	 * 第五关日历json
	 * 
	 * @param model
	 * @param items
	 *            内容List<GradeItem>
	 * @param step
	 *            步骤
	 * @return
	 */

	public String getGradeListJson(List<GradeItem> items, String step) {
		boolean pass = step.equals("1") ? false : true;
		List<VGrade> gradeList = new ArrayList<VGrade>();
		SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm");
		for (GradeItem item : items) {
			VGrade vGrade = new VGrade();
			vGrade.setTitle(item.getGrade().getTitle());
			vGrade.setPass(pass);
			vGrade.setDate(dateSdf.format(item.getStartTime()));
			vGrade.setTime(timeSdf.format(item.getStartTime()) + " - "
					+ timeSdf.format(item.getEndTime()));
			vGrade.setPlace(item.getAddress());
			vGrade.setTeach(item.getAdvier().getRealName());
			vGrade.setNewteach(item.getNewTeach().getRealName());
			vGrade.setRemark(item.getRemark());
			gradeList.add(vGrade);
		}
		return JsonUtils.writeObjectToJson(gradeList);
	}

	/**
	 * 若批课老师打完分就直接进入5.2
	 * 
	 * @param model
	 * @param items
	 * @param step
	 *            步骤
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getGradeStep(String name, String stageIndex, String phaseIndex) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		Finder finder = Finder
				.create("from Grade g left join fetch g.phase phase left join fetch phase.stage stage left join fetch stage.project project");
		finder.append("where g.uid=:uid").setParam("uid", uId);
		finder.append("and phase.fdIndex=:phaseIndex")
				.setParam("phaseIndex", 1);// 步骤
		finder.append("and stage.fdIndex=:stageIndex")
				.setParam("stageIndex", 5);// 阶段
		finder.append("and project.fdId=:projectId")
				.setParam("projectId", name); // 流程
		List<Grade> grades = find(finder);
		if (grades != null && grades.size() > 0) {
			Grade grade = grades.get(0);
			if (grade.getValue() != null) {
				phaseIndex = String.valueOf(2);
			}
		}

		return phaseIndex;
	}

	/**
	 * 校验主题唯一
	 * 
	 * @param name
	 * @return
	 */
	public boolean hasGradeByName(String name) {
		List<Grade> lists = findByCriteria(Grade.class, Value.eq("title", name));
		return !(CollectionUtils.isEmpty(lists));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Grade> getEntityClass() {
		return Grade.class;
	}
}
