package ruyees.otp.service.flow;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.model.flow.BamProject;
import ruyees.otp.model.flow.BamProjectPhase;
import ruyees.otp.service.SimpleService;

/**
 * 
 * 流程操作步骤Service
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:30:29
 */
@Service
@Transactional(readOnly = true)
public class BamProjectPhaseService extends SimpleService {

	/**
	 * 根据某一步骤找到指定的步骤
	 * 
	 * @param phase
	 * @param stageIndex
	 * @param stepIndex
	 * @return
	 */
	public BamProjectPhase getPointPhaseByPhase(String newTeachId,
			BamProjectPhase phase, int stageIndex, int stepIndex) {
		BamProject project = phase.getStage().getProject();
		Finder finder = Finder
				.create("select phase from BamProjectPhase phase left join fetch phase.stage stage left join fetch stage.project project");
		finder.append("where project.fdId=:fdId").setParam("fdId",
				project.getFdId());
		finder.append("and stage.fdIndex=:stageIndex").setParam("stageIndex",
				stageIndex);
		finder.append("and phase.fdIndex=:phaseIndex").setParam("phaseIndex",
				stepIndex);
		finder.append("and phase.newteachId=:newteachId").setParam(
				"newteachId", newTeachId);
		return findUnique(finder);
	}

	/**
	 * 更新流程状态
	 * 
	 * <pre>
	 * 调用此方法就代表流程步骤将要通过
	 * </pre>
	 * 
	 * @param userId
	 *            :新教师
	 * @param phase
	 *            :步骤
	 */
	public void addPhaseStatus(String userId, BamProjectPhase phase) {

	}

}
