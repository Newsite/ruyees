package cn.xdf.me.otp.service.flow;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.hibernate4.Updater.UpdateMode;
import cn.xdf.me.otp.model.flow.BamOperation;
import cn.xdf.me.otp.model.flow.BamProject;
import cn.xdf.me.otp.model.flow.BamProjectPhase;
import cn.xdf.me.otp.model.flow.BamStage;

/**
 * 
 * 作业对应步骤业务数据
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:30:02
 */
@Service
@Transactional(readOnly = true)
public class BamOperationService extends BamBaseService {
	
	@SuppressWarnings("unchecked")
	@Override
	public Class<BamOperation> getEntityClass() {
		return BamOperation.class;
	}

	/**
	 * 设置通过
	 * 
	 * @param bamCourseware
	 * @return
	 */
	@Transactional(readOnly = false)
	public BamOperation setThrough(BamOperation bamOperation) {
		Updater<BamOperation> updater = new Updater<BamOperation>(bamOperation);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("through");
		return updateByUpdater(updater);
	}

	/**
	 * 保存流程课件作业包描述信息 根据一个作业包下的步骤信息修改整个流程当前步骤阶段下的所有作业信息
	 * 
	 * @param bamOperation
	 * @return
	 */
	@Transactional(readOnly = false)
	@SuppressWarnings("unchecked")
	public void saveFlowOperation(BamOperation bamOperation) {
		BamOperation operation = load(bamOperation.getFdId());
		BamProjectPhase bamProjectPhase = operation.getBamPackage().getPhase();
		int step = bamProjectPhase.getFdIndex();
		BamStage bamStage = bamProjectPhase.getStage();
		int stage = bamStage.getFdIndex();
		String flowId = bamStage.getProject().getFdId();

		Finder finder = Finder.create("from BamOperation b ");
		finder.append("left join fetch b.bamPackage package ");
		finder.append("left join fetch package.phase phase ");
		finder.append("left join fetch phase.stage stage ");
		finder.append("left join fetch stage.project project ");

		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		finder.append("and stage.fdIndex=:stageIndex").setParam("stageIndex",
				stage);// 阶段
		finder.append("and phase.fdIndex=:phaseIndex").setParam("phaseIndex",
				step);// 步骤
		finder.append("and b.fdName=:fdName").setParam("fdName",
				operation.getFdName());

		List<BamOperation> bamOperations = find(finder);
		if (bamOperations != null && bamOperations.size() > 0) {
			for (BamOperation bo : bamOperations) {
				bo.setFdReferBook(bamOperation.getFdReferBook());
				bo.setFdRequest(bamOperation.getFdRequest());
				save(bo);
			}
		}
	}

}
