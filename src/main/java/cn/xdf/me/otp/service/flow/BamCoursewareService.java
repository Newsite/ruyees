package cn.xdf.me.otp.service.flow;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.hibernate4.Updater.UpdateMode;
import cn.xdf.me.otp.common.utils.MyBeanUtils;
import cn.xdf.me.otp.model.flow.BamCourseware;
import cn.xdf.me.otp.model.flow.BamCoursewareItem;
import cn.xdf.me.otp.model.flow.BamProject;
import cn.xdf.me.otp.model.flow.BamProjectMember;
import cn.xdf.me.otp.model.flow.BamProjectPhase;
import cn.xdf.me.otp.model.flow.BamStage;
import cn.xdf.me.otp.service.notify.NotifyService;

/**
 * 
 * 课件业务数据
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:29:27
 */
@Service
@Transactional(readOnly = true)
public class BamCoursewareService extends BamBaseService {
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<BamCourseware> getEntityClass() {
		return BamCourseware.class;
	}

	@Autowired
	private BamProjectPhaseService bamProjectPhaseService;

	@Autowired
	private TraineeService traineeService;

	@Autowired
	private NotifyService notifyService;

	/**
	 * 设置课件通过
	 * 
	 * @param bamCourseware
	 * @return
	 */
	@Transactional(readOnly = false)
	public void setThrough(BamCourseware bamCourseware, String newTeachId) {
		Updater<BamCourseware> updater = new Updater<BamCourseware>(
				bamCourseware);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("fdComment");
		updater.include("through");
		bamCourseware = updateByUpdater(updater);
		// 若课件驳回，其下所有课件分项都驳回并给新教师发消息
		if (!bamCourseware.getThrough()) {
			List<BamCoursewareItem> itemList = bamCourseware.getItems();
			for (BamCoursewareItem item : itemList) {
				item.setThrough(false);
				item.setStatus(3);// 驳回
				Updater<BamCoursewareItem> updater2 = new Updater<BamCoursewareItem>(
						item);
				updater2.setUpdateMode(UpdateMode.MIN);
				updater2.include("status");
				updater2.include("through");
				updateByUpdater(updater2);
			}
			// 发消息
			notifyService.guidToDoNewTeach(bamCourseware, 3);
		}
		// 若第四步要复制，若第六步则不复制
		BamCourseware bCourseware = get(bamCourseware.getFdId());
		int stegId = bCourseware.getPhase().getStage().getFdIndex();
		boolean through = bamCourseware.getThrough();
		if (stegId == 4 && through) {
			BamProjectPhase phase = bamProjectPhaseService
					.getPointPhaseByPhase(newTeachId, bCourseware.getPhase(),
							6, 1);
			// 若第六步有数据则先删掉再复制
			if (phase.getCoursewares() != null) {
				if (phase.getCoursewares().size() > 0) {
					BamCourseware bamCour = phase.getCoursewares().get(0);
					for (BamCoursewareItem item : bamCour.getItems()) {
						// bamCoursewareItemService.deleteEntity(item);
						delete(BamCoursewareItem.class, item.getFdId());
					}
					deleteEntity(bamCour);
				}
			}

			// 把第四步内容复制到第六步
			BamCourseware bamCourse = new BamCourseware();
			MyBeanUtils.copyProperties(bCourseware, bamCourse);
			bamCourse.setFdId(null);
			bamCourse.setPhase(phase);
			bamCourse.setThrough(false);
			bamCourse.setFdComment(null);
			save(bamCourse);

			List<BamCoursewareItem> coursewareItems = bCourseware.getItems();
			if (coursewareItems != null) {
				BamCoursewareItem bamCoursewareItem = null;
				for (BamCoursewareItem bamItem : coursewareItems) {
					bamCoursewareItem = new BamCoursewareItem();
					MyBeanUtils.copyProperties(bamItem, bamCoursewareItem);
					bamCoursewareItem.setFdId(null);
					bamCoursewareItem.setThrough(false);
					bamCoursewareItem.setStatus(1);// 已上传
					bamCoursewareItem.setRemark(null);
					bamCoursewareItem.setBamCourseware(bamCourse);
					save(bamCoursewareItem);
					// bamCoursewareItemService.save(bamCoursewareItem);
				}
			}

		} else if (stegId == 6 && bamCourseware.getThrough()) {
			// 写入最终分数
			String flowId = bCourseware.getPhase().getStage().getProject()
					.getFdId();
			savaFinalScore(flowId, newTeachId);
		}
	}

	/**
	 * 保存最终得分信息
	 * 
	 * @param flowId
	 * @param newTeachId
	 * @return
	 */
	@Transactional(readOnly = false)
	public void savaFinalScore(String flowId, String newTeachId) {
		String finalScore = traineeService.getSuccessScore(newTeachId, flowId);

		Finder finder = Finder
				.create("from BamProjectMember m left join fetch m.project p where p.fdId=:projectId and m.newteachId=:newteachId");
		finder.setParam("projectId", flowId);// 对应流程
		finder.setParam("newteachId", newTeachId);// 对应新教师
		BamProjectMember bamProjectMember = findUnique(finder);

		bamProjectMember.setEndTime(new Timestamp(new Date().getTime()));
		bamProjectMember.setFinalScore(Float.valueOf(finalScore));

		save(bamProjectMember);
	}

	/**
	 * 保存流程课件信息 根据一个课件信息修改整个流程当前步骤阶段下的所有课件信息
	 * 
	 * @param bamCourseware
	 * @return
	 */
	@Transactional(readOnly = false)
	@SuppressWarnings("unchecked")
	public void saveFlowCourseware(BamCourseware bamCourseware) {
		BamCourseware courseware = load(bamCourseware.getFdId());
		BamProjectPhase bamProjectPhase = courseware.getPhase();
		int step = bamProjectPhase.getFdIndex();
		BamStage bamStage = bamProjectPhase.getStage();
		int stage = bamStage.getFdIndex();
		String flowId = bamStage.getProject().getFdId();

		Finder finder = Finder.create("from BamCourseware b ");
		finder.append("left join fetch b.phase phase ");
		finder.append("left join fetch phase.stage stage ");
		finder.append("left join fetch stage.project project ");

		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		finder.append("and stage.fdIndex=:stageIndex").setParam("stageIndex",
				stage);// 阶段
		finder.append("and phase.fdIndex=:phaseIndex").setParam("phaseIndex",
				step == 1 ? 2 : step);// 步骤

		List<BamCourseware> bamCoursewares = find(finder);
		if (bamCoursewares != null && bamCoursewares.size() > 0) {
			for (BamCourseware bo : bamCoursewares) {
				bo.setCouReq(bamCourseware.getCouReq());
				bo.setCouNeedInfo(bamCourseware.getCouNeedInfo());
				save(bo);
			}
		}
	}

}
