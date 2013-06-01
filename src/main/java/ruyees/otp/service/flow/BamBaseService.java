package ruyees.otp.service.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Updater;
import ruyees.otp.common.hibernate4.Updater.UpdateMode;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.common.utils.ComUtils;
import ruyees.otp.model.BamAbsPhase;
import ruyees.otp.model.BamProcess;
import ruyees.otp.model.IdEntity;
import ruyees.otp.model.flow.BamProjectMember;
import ruyees.otp.model.flow.BamProjectPhase;
import ruyees.otp.model.flow.BamStage;
import ruyees.otp.model.notify.NotifyAcceptUser;
import ruyees.otp.model.notify.NotifyEnum;
import ruyees.otp.model.notify.NotifyTodo;
import ruyees.otp.model.notify.NotifyTypeEnum;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.BaseService;
import ruyees.otp.utils.ShiroUtils;

public abstract class BamBaseService extends BaseService {

	public int updateByNamedQuery(final String queryName,
			final Map<String, ?> parameters) {
		throw new RuntimeException("error runing");
	}

	@Transactional(readOnly = false)
	public <T extends IdEntity> T update(T entity) {
		T targetEnitity = super.update(entity);
		bindInject(targetEnitity);
		return targetEnitity;
	}

	@Transactional(readOnly = false)
	public <T extends IdEntity> T save(T entity) {
		T targetEnitity = super.save(entity);
		bindInject(targetEnitity);
		return targetEnitity;
	}

	@Transactional(readOnly = false)
	public <T> T updateByUpdater(Updater<T> updater) {

		Object entity = updater.getBean();
		if (entity instanceof BamProcess) {
			BamProcess process = (BamProcess) entity;
			if (process.getThrough()) {
				Finder finder = Finder.create("from ").append(
						entity.getClass().getName());
				finder.append("where ").append(BamProcess.PRI_FIELD)
						.append("=:fid").setParam("fid", process.getFdId());
				BamProcess targetProcess = findUnique(finder);
				if (targetProcess.getThrough()) {
					return super.updateByUpdater(updater);
				}
			}
			T targetEnitity = super.updateByUpdater(updater);
			bindInject(targetEnitity);
			return targetEnitity;
		}

		return super.updateByUpdater(updater);
	}

	/**
	 * 流程过滤器
	 * 
	 * @param entity
	 */
	private void bindInject(Object entity) {
		if (entity instanceof BamProcess) {
			BamProcess process = (BamProcess) entity;
			if (!process.getThrough())
				return;

			// 0-----------获取更新的实例---------

			setPhaseStatus(process);
		}
	}

	/**
	 * 更新阶段，步骤，流程状态
	 * 
	 * @param process
	 */
	private void setPhaseStatus(BamProcess process) {

		BamProjectPhase phase = process.getPhase();
		//
		if (!ShiroUtils.getUser().getId().equals(phase.getNewteachId())) {
			if (!ShiroUtils.getUser().getId().equals(phase.getGuidId())) {
				return;
			}
		}
		BamStage stage = phase.getStage();
		List<BamProcess> bamProcess = phase.getProcess(stage);
		if (bamProcess == null) {
			throw new RuntimeException("无效的资源数据");
		}
		boolean throught = true;
		for (Iterator<BamProcess> iterator = bamProcess.iterator(); iterator
				.hasNext();) {
			BamProcess p = iterator.next();
			if (!p.getThrough()) {
				throught = false;
				break;
			}
		}
		if (!throught) {
			return;
		}
		// 更新步骤
		updateFlowIsThrough(phase);
		//
		addPhaseStatus(stage, phase);
	}

	/**
	 * 更新阶段， 人员所处的状态
	 * 
	 * <pre>
	 * 调用此方法后代表此步骤已经通过
	 * </pre>
	 * 
	 * @param phase
	 */
	private void addPhaseStatus(BamStage stage, BamProjectPhase targetPhase) {
		// 获取下一个未通过的步骤
		List<BamAbsPhase> phases = new ArrayList<BamAbsPhase>();
		BamProjectPhase nextPhase = targetPhase.nextByAllAndNotThrought(phases);
		if (!CollectionUtils.isEmpty(phases)) {
			for (BamAbsPhase p : phases) {
				updateFlowIsThrough(p);
			}
		}

		updateFlowIsThrough(stage);

		Finder finder = Finder
				.create("select m from BamProjectMember m left join m.project p");
		finder.append("where m.newteachId=:nid and p.fdId=:fdId");
		finder.setParam("nid", stage.getNewteachId());
		finder.setParam("fdId", stage.getProject().getFdId());
		BamProjectMember member = findUnique(finder);
		updateFlowIsThrough(member, nextPhase);
	}

	/*
	*//**
	 * 更新整个流程的状态
	 * 
	 * @param bamStage
	 */
	/*
	 * private void updateFlowIsThrough(BamProject bamProject) { BamProject
	 * targetProject = new BamProject();
	 * targetProject.setFdId(bamProject.getFdId()); Updater<BamProject> updater
	 * = new Updater<BamProject>(targetProject);
	 * updater.setUpdateMode(UpdateMode.MIN); updater.include("through");
	 * updateByUpdater(updater); }
	 */

	/**
	 * 更新此人对应的流程通过
	 * 
	 * @param member
	 */
	private void updateFlowIsThrough(BamProjectMember member,
			BamProjectPhase next) {
		if (next != null) {
			if (next.getThrough()) {
				return;
			}
		}
		BamProjectMember pm = new BamProjectMember();
		pm.setFdId(member.getFdId());

		if (next == null) {
			// pm.setThrough(true);
			pm.setItemId(null);
			pm.setContentId(null);
		} else {
			// pm.setThrough(false);
			pm.setItemId(next.getStage().getFdId());
			pm.setContentId(next.getFdId());
		}

		Updater<BamProjectMember> updater = new Updater<BamProjectMember>(pm);
		updater.setUpdateMode(UpdateMode.MIN);
		// updater.include("through");
		updater.include("itemId");
		updater.include("contentId");
		super.updateByUpdater(updater);
	}

	/**
	 * 更新阶段状态
	 * 
	 * @param bamStage
	 */
	@SuppressWarnings("unchecked")
	private void updateFlowIsThrough(BamStage bamStage) {
		if (bamStage.getThrough()) {
			return;
		}
		if (bamStage != null) {
			Finder finder = Finder
					.create("from BamProjectPhase where stage.fdId=:fdId");
			finder.setParam("fdId", bamStage.getFdId());
			List<BamProjectPhase> phasesByStages = find(finder);
			boolean isAllThroug = true;
			for (BamProjectPhase bamProjectPhase : phasesByStages) {
				if (!bamProjectPhase.getThrough()) {
					isAllThroug = false;
				}
			}
			if (!isAllThroug) {
				return;
			}

		}
		BamStage targetStage = new BamStage();
		targetStage.setFdId(bamStage.getFdId());
		targetStage.setThrough(true);
		bamStage.setEndTime(ComUtils.now());
		Updater<BamStage> updater = new Updater<BamStage>(targetStage);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("through");
		updater.include("endTime");
		super.updateByUpdater(updater);

		// 给新教师发邮件
		String newId = bamStage.getNewteachId();
		String guidId = bamStage.getGuidId();
		SysOrgPerson person = (SysOrgPerson) findUniqueByProperty(
				SysOrgPerson.class, Value.eq("fdId", newId));
		SysOrgPerson sendUser = (SysOrgPerson) findUniqueByProperty(
				SysOrgPerson.class, Value.eq("fdId", guidId));

		NotifyTodo notifyTodo = new NotifyTodo();
		List<NotifyAcceptUser> accepList = new ArrayList<NotifyAcceptUser>();
		NotifyAcceptUser accept = new NotifyAcceptUser();
		accept.setSendTime(ComUtils.now());
		accept.setNotifyTodo(notifyTodo);
		accept.setPerson(person);
		accepList.add(accept);
		// otp/trainee/main/13c19e0757c40593ab387064733a2c86/2/2
		String next = String.valueOf(bamStage.getFdIndex() + 1);
		String href = "http://otp.xdf.cn/otp/trainee/main/"
				+ bamStage.getProjectId() + "/" + next + "/1";
		String strHref = "恭喜你通过第" + bamStage.getFdIndex() + "关，请进入第<a href="
				+ href + ">" + next + "</a>关";
		if (bamStage.getFdIndex() == 6) {
			next = "证书";
			href = "http://otp.xdf.cn/otp/trainee/success/"
					+ bamStage.getProjectId();
			strHref = "恭喜你通过6关，请查看自己的<a href=" + href + ">" + next + "</a>";
		}

		notifyTodo.setTitle("通关提醒");
		notifyTodo.setBody(strHref);
		notifyTodo.setSendUser(sendUser);
		notifyTodo.setAcceptUser(accepList);
		notifyTodo.setNotifyEnum(NotifyEnum.EMAIL);
		notifyTodo.setNotifyTypeEnum(NotifyTypeEnum.TODO);
		super.save(notifyTodo);
	}

	/**
	 * 更新步骤状态
	 * 
	 * @param phase
	 */
	private void updateFlowIsThrough(BamAbsPhase phase) {
		if (phase.getThrough()) {// 步骤已经完成，无需更新
			return;
		}

		BamProjectPhase targetPhase = new BamProjectPhase();
		targetPhase.setFdId(phase.getFdId());
		targetPhase.setThrough(true);
		targetPhase.setEndTime(ComUtils.now());

		Updater<BamProjectPhase> updater = new Updater<BamProjectPhase>(
				targetPhase);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("through");
		updater.include("endTime");
		super.updateByUpdater(updater);
	}

}
