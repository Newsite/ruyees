package cn.xdf.me.otp.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import cn.xdf.me.otp.common.utils.MyBeanUtils;
import cn.xdf.me.otp.common.utils.array.ArrayUtils;
import cn.xdf.me.otp.common.utils.array.SortType;
import cn.xdf.me.otp.model.flow.BamCourseware;
import cn.xdf.me.otp.model.flow.BamExamCategory;
import cn.xdf.me.otp.model.flow.BamPackage;
import cn.xdf.me.otp.model.flow.BamProjectPhase;
import cn.xdf.me.otp.model.flow.BamSeeCw;
import cn.xdf.me.otp.model.flow.BamStage;
import cn.xdf.me.otp.model.flow.Grade;
import cn.xdf.me.otp.utils.PhaseUtils;

/**
 * 流程步骤基类
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:06:02
 */
public abstract class BamAbsPhase extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 对应阶段
	 * 
	 * @return
	 */
	public abstract BamStage getStage();

	/**
	 * 新教师提交的课件
	 * 
	 * @return
	 */
	public abstract List<BamCourseware> getCoursewares();

	/**
	 * 查看视频
	 * 
	 * @return
	 */
	public abstract List<BamSeeCw> getCws();

	/**
	 * 作业包
	 * 
	 * @return
	 */
	public abstract BamPackage getBamPackage();

	/**
	 * 答题
	 * 
	 * @return
	 */
	public abstract List<BamExamCategory> getExamCategories();

	/**
	 * 批课打分
	 * 
	 * @return
	 */
	public abstract List<Grade> getGrades();

	/**
	 * 是否通过
	 * 
	 * @return
	 */
	public abstract boolean getThrough();

	/**
	 * 是否通过
	 * 
	 * @param through
	 */
	public abstract void setThrough(boolean through);

	/**
	 * 步骤索引
	 * 
	 * @return
	 */
	public abstract int getFdIndex();

	public abstract String getFiled();

	/**
	 * 此步骤下没有资源并且在到通过阶段。
	 */
	private List<BamProjectPhase> nextNotProceesAndNotThrought;

	/**
	 * 此步骤下没有资源并且在到通过阶段。
	 * 
	 * @return
	 */
	public List<BamProjectPhase> getNextNotProceesAndNotThrought() {
		return nextNotProceesAndNotThrought;
	}

	/**
	 * 此步骤下没有资源并且在到通过阶段。
	 * 
	 * @param nextNotProceesAndNotThrought
	 */
	public void setNextNotProceesAndNotThrought(
			List<BamProjectPhase> nextNotProceesAndNotThrought) {
		this.nextNotProceesAndNotThrought = nextNotProceesAndNotThrought;
	}

	/**
	 * 获取阶段的索引
	 * 
	 * @return
	 */
	@Transient
	public int getStageIndex() {
		return getStage().getFdIndex();
	}

	/**
	 * 获取此阶段下的下一个步骤
	 * 
	 * @return
	 */
	@Transient
	public BamProjectPhase next() {
		BamStage stage = this.getStage();
		List<BamProjectPhase> phases = stage.getBamProjectPhases();
		// 对集合重新排序
		ArrayUtils.sortListByProperty(phases, "fdIndex", SortType.HIGHT);
		for (Iterator<BamProjectPhase> iterator = phases.iterator(); iterator
				.hasNext();) {
			BamProjectPhase bamProjectPhase = iterator.next();
			if (bamProjectPhase.getFdId().equals(fdId)) {
				if (iterator.hasNext()) {
					BamProjectPhase phase = (BamProjectPhase) iterator.next();
					if (!CollectionUtils.isEmpty(phase.getProcess(phase
							.getStage()))) {
						return phase;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 同一个阶段下 上一个
	 * 
	 * @return
	 */
	public BamProjectPhase last() {
		if (getFdIndex() == 1)
			return null;
		BamStage stage = this.getStage();
		List<BamProjectPhase> phases = stage.getBamProjectPhases();
		// 对集合重新排序
		ArrayUtils.sortListByProperty(phases, "fdIndex", SortType.HIGHT);
		for (int i = 0; i < phases.size(); i++) {
			BamProjectPhase bamProjectPhase = phases.get(i);
			if (bamProjectPhase.getFdId().equals(fdId)) {
				if (i == 0)
					return null;
				return phases.get(i - 1);
			}
		}
		return null;
	}

	public BamProjectPhase lastAll() {
		if (getFdIndex() == 1)
			return null;
		BamStage stage = this.getStage();
		List<BamProjectPhase> phases = stage.getBamProjectPhases();
		// 对集合重新排序
		ArrayUtils.sortListByProperty(phases, "fdIndex", SortType.HIGHT);
		for (int i = 0; i < phases.size(); i++) {
			BamProjectPhase bamProjectPhase = phases.get(i);
			if (bamProjectPhase.getFdId().equals(fdId)) {
				if (i == 0) {
					BamStage lastStage = stage.last();
					if (lastStage != null) {
						List<BamProjectPhase> lastPhases = stage
								.getBamProjectPhases();
						if (lastPhases != null) {
							return lastPhases.get(lastPhases.size() - 1);
						}
					}
					return null;
				}
				return phases.get(i - 1);
			}
		}
		return null;
	}

	@Transient
	public boolean getEnable() {
		BamProjectPhase lastPhase = last();
		if (lastPhase == null) {
			BamStage lastStage = this.getStage().last();
			if (lastStage == null) {
				return true;
			}
			List<BamProjectPhase> lastPhases = lastStage.getBamProjectPhases();
			for (BamProjectPhase ph : lastPhases) {
				if (!ph.getThrough())
					return false;
			}
		}
		try {
			return lastPhase.getThrough();
		} catch (Exception ex) {
			return true;
		}

	}

	/**
	 * 获取下一个未完成的步骤
	 * 
	 * @return
	 */
	@Transient
	public BamProjectPhase nextByAllAndNotThrought(List<BamAbsPhase> notProcess) {
		BamProjectPhase phase = (BamProjectPhase) this;
		phase = phase.nextByAll(fdId, notProcess);
		if (phase == null)
			return null;
		if (!phase.getThrough()) {
			return phase;
		}
		return phase;
	}

	/**
	 * 获取备课流程的所有步骤下的下一个步骤
	 * 
	 * @param fdId
	 *            当前节点的ID
	 * @param notProcess
	 *            未有资源的步骤
	 * @return
	 */
	@Transient
	public BamProjectPhase nextByAll(String id, List<BamAbsPhase> notProcess) {
		BamStage stage = getStage();
		BamProjectPhase targetPhase = null;
		List<BamProjectPhase> phases = stage.getBamProjectPhases();
		// 对集合重新排序
		ArrayUtils.sortListByProperty(phases, "fdIndex", SortType.HIGHT);
		Iterator<BamProjectPhase> iterator = phases.iterator();
		while (iterator.hasNext()) {
			targetPhase = iterator.next();
			if (targetPhase.getFdId().equals(id)) {
				if (iterator.hasNext()) {
					BamProjectPhase phase = iterator.next();
					if (phase.getStage().getFdIndex() > 3)
						return phase;
					if (!CollectionUtils.isEmpty(phase.getProcess(phase
							.getStage()))) {
						return phase;
					} else {
						notProcess.add(this);
					}
				}
			}
		}
		stage = stage.next();
		if (stage == null)
			return null;
		BamProjectPhase firstPhase = stage.firstPhase();
		if (stage.getFdIndex() > 3)
			return firstPhase;
		if (!CollectionUtils.isEmpty(firstPhase.getProcess(firstPhase
				.getStage()))) {
			return firstPhase;
		}
		return firstPhase.nextByAll(firstPhase.getFdId(), notProcess);
	}

	/**
	 * 获取步骤下的资源项
	 * 
	 * @param stage
	 * @return
	 */
	@Transient
	@SuppressWarnings("unchecked")
	public List<BamProcess> getProcess(BamStage stage) {
		String resouceField = getFiled();
		if (StringUtils.isBlank(resouceField)) {
			resouceField = PhaseUtils.fetchField(stage.getFdIndex(),
					getFdIndex());
		}
		Object obj = MyBeanUtils.getGetFieldValue(this, resouceField);
		if (obj == null)
			return null;
		List<BamProcess> lists = null;
		if (obj instanceof BamPackage) {
			lists = new ArrayList<BamProcess>();
			lists.add((BamPackage) obj);
		} else {
			lists = (List<BamProcess>) obj;
		}
		return lists;
	}
}
