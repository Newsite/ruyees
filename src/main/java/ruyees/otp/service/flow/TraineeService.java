package ruyees.otp.service.flow;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.model.IdEntity;
import ruyees.otp.model.base.Diction;
import ruyees.otp.model.flow.BamIndex;
import ruyees.otp.model.flow.BamOperation;
import ruyees.otp.model.flow.BamProject;
import ruyees.otp.model.flow.BamProjectMember;
import ruyees.otp.model.flow.BamProjectPhase;
import ruyees.otp.model.flow.BamStage;
import ruyees.otp.model.flow.Grade;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.model.template.ProTemplate;
import ruyees.otp.model.template.TemplateContent;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.SimpleService;
import ruyees.otp.service.base.DictionService;
import ruyees.otp.utils.Cn2SpellUtil;
import ruyees.otp.utils.PhaseUtils;
import ruyees.otp.utils.model.FlowModel;

/**
 * 
 * 新教师备课阶段 基本的流程为: 1：获取用户对应的备课流程。 2：用户点击某一项备课流程，传递流程名称(
 * 
 * @see ruyees.otp.model.flow.BamProject name )
 *      <p/>
 *      3: 根据点击的备课名称+用户的id,获取用户所有的流程阶段和步骤信息(对应用户未执行的步骤以下阶段未不可执行)
 *      <p/>
 *      4: 校验用户是否入职，如果未入职，用户可以点击前两关，不可以点击第三关及以后关口。如果用户已经入职，可以点击所有关口。
 *      <p/>
 *      5：如果用户已经通过的关口，不可以再次提交任何的东西，只允许用户查看。
 *      <p/>
 * @author Zaric
 * @date 2013-6-1 上午1:31:03
 */
@Service
@Transactional(readOnly = true)
public class TraineeService extends SimpleService {

	private static final Logger log = LoggerFactory
			.getLogger(TraineeService.class);

	@Autowired
	private BamProjectService bamProjectService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private DictionService dictionService;

	public static final int QUERY_TYPE_ALL = 0;

	public static final int QUERY_TYPE_ALREADY_THROUGH = 1;

	public static final int QUERY_TYPE_NOT_THROUGH = 2;

	public List<FlowModel> findEnableUserFlowModels(String userId) {
		Diction diction = dictionService.findCoursePartials();
		List<BamProject> bamProjectByUids = findBamProjectByUid(userId,
				QUERY_TYPE_NOT_THROUGH);
		return PhaseUtils.getFlowModels(diction, bamProjectByUids);
	}

	/**
	 * <pre>
	 * 1:查询所有的流程信息
	 * </pre>
	 * 
	 * <pre>
	 * 2：把对应当前用户的流程的enableByUser值值为true
	 * </pre>
	 * 
	 * @param userId
	 * @return
	 */
	public List<BamProject> findEnableUserBamProjectByUid(String userId) {
		List<BamProject> allProjects = findAllBamProject();
		List<BamProject> bamProjectByUids = findBamProjectByUid(userId,
				QUERY_TYPE_NOT_THROUGH);
		for (BamProject bamProject : allProjects) {
			for (BamProject targetProject : bamProjectByUids) {
				if (bamProject.getFdId().equals(targetProject.getFdId())) {
					bamProject.setEnableByUser(true);
				}
			}
		}
		return allProjects;
	}

	@SuppressWarnings(value = "unchecked")
	public List<BamProject> findAllBamProject() {
		Finder finder = Finder.create(
				"select p from BamProject p where p.status=:status").setParam(
				"status", BamProject.IS_OPEN);
		return find(finder);
	}

	/**
	 * 获取用户对应的流程信息
	 * <p/>
	 * 1:根据状态获取流程信息
	 * 
	 * @param userId
	 * @param queryType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BamProject> findBamProjectByUid(String userId, int queryType) {
		Finder finder = Finder
				.create("select p from BamProject p join fetch p.bamProjectMembers m where p.status=:status")
				.setParam("status", BamProject.IS_OPEN);
		finder.append("and m.newteachId=:nid").setParam("nid", userId);

		switch (queryType) {
		case QUERY_TYPE_ALL:
			break;
		case QUERY_TYPE_ALREADY_THROUGH:
			finder.append("and m.through=:through").setParam("through", true);// 通过
			break;
		case QUERY_TYPE_NOT_THROUGH:
			finder.append("and m.through=:through").setParam("through", false);// 未通过
			break;
		}

		return find(finder);
	}

	/**
	 * 获取用户对应的所有流程信息
	 * <p/>
	 * 1:过滤用户完成的流程。
	 * <p/>
	 * 2:读取有效的流程
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BamProject> findBamProjectByUid(String userId) {
		Finder finder = Finder
				.create("select p from BamProject p join fetch p.bamProjectMembers m where p.status=:status")
				.setParam("status", BamProject.IS_OPEN);
		finder.append("and m.newteachId=:nid").setParam("nid", userId);
		finder.append("and m.through=:through").setParam("through", false);// 未通过
		return find(finder);
	}

	/**
	 * 根据人员ID和流程获取人员对应
	 * 
	 * @param userId
	 * @param flowId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BamProjectMember findProjectMemberByUidAndFlowName(String userId,
			String flowId) {
		Finder finder = Finder
				.create("select m from BamProjectMember m left join fetch m.project p ");
		finder.append("where p.fdId=:fdId").setParam("fdId", flowId);
		finder.append("and p.status=:status").setParam("status",
				BamProject.IS_OPEN);
		finder.append("and m.newteachId=:nid").setParam("nid", userId);
		List<BamProjectMember> members = find(finder);
		if (members != null && members.size() > 0) {
			BamProjectMember member = members.get(0);
			member.setNewTeach(accountService.findById(member.getNewteachId()));
			member.setGuid(accountService.findById(member.getGuidId()));
			return member;
		}
		return null;
	}

	/**
	 * 根据人员和流程id获取对应的流程阶段
	 * 
	 * @param userId
	 * @param flowId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BamStage> findStageByUidAndFlowName(String userId, String flowId) {
		Finder finder = Finder
				.create("select stage from BamStage stage left join stage.project p left join p.bamProjectMembers m");
		finder.append("where stage.newteachId=:nid").setParam("nid", userId);
		finder.append("and m.newteachId=:newId").setParam("newId", userId);
		finder.append("and p.fdId=:fdId").setParam("fdId", flowId);
		return find(finder);
	}

	/**
	 * 根据人员编号和流程名称获取对应的流程 (资源项)
	 * 
	 * @param userId
	 * @param flowId
	 * @return
	 */
	@SuppressWarnings(value = "unchecked")
	@Transactional(readOnly = true)
	public BamProject findBamProjectByUidAndFlowName(String userId,
			String flowId) {
		Finder finder = Finder
				.create("select p from BamProject p join fetch p.bamProjectMembers m where p.status=:status")
				.setParam("status", BamProject.IS_OPEN);
		finder.append("and p.fdId=:fdId").setParam("fdId", flowId);
		finder.append("and m.newteachId=:nid").setParam("nid", userId);
		finder.append("and m.through=:through").setParam("through", false); // 未通过
		List<BamProject> bamProjects = find(finder);
		if (bamProjects != null && bamProjects.size() > 0)
			return bamProjects.get(0);
		return null;
	}

	/**
	 * 获取针对人员流程步骤信息
	 * 
	 * @param userId
	 *            人员Id
	 * @param flowId
	 *            流程Id
	 * @param stepId
	 *            流程步骤Id
	 * @return
	 */
	public BamProjectPhase findBamStep(String userId, String flowId, int stage,
			int step) {
		if (stage == 2 && step == 2) {
			step = 1;
		}
		if (stage == 4 && step == 2) {
			step = 1;
		}
		if (stage == 5 && step == 2) {
			step = 1;
		}
		if (stage == 6 && step == 2) {
			step = 1;
		}

		Finder finder = Finder
				.create("from BamProjectPhase phase left join fetch phase.stage stage left join fetch stage.project project left join fetch phase.content content left join fetch content.item item");

		// finder.append(PhaseUtils.fetchModel("phase", stage, step));// 抓取资源项

		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		finder.append("and stage.fdIndex=:stageIndex").setParam("stageIndex",
				stage);// 阶段
		finder.append("and phase.fdIndex=:phaseIndex").setParam("phaseIndex",
				step);// 步骤
		finder.append("and phase.newteachId=:nid").setParam("nid", userId);// 对应新教师
		finder.setCacheable(true);
		return findUnique(finder);
	}

	/**
	 * 查询用户对应的步骤(资源项) 1: 校验用户是否有权限执行此步骤 2: 遍历用户对应的所有步骤 3：校驗用戶是否已經執行了此步驟
	 * 
	 * @param userId
	 * @param flowId
	 *            (流程ID，唯一)
	 * @param stage
	 * @param step
	 * @return
	 */
	@Transactional(readOnly = true)
	@SuppressWarnings(value = "unchecked")
	public TemplateContent findStep(String userId, String flowId, int stage,
			int step) {
		BamProject project = findBamProjectByUidAndFlowName(userId, flowId);
		if (project == null)
			return null;
		ProTemplate template = project.getTemplate();
		Finder finder = Finder
				.create("SELECT c from TemplateContent c left join fetch c.item i");
		finder.append("where i.template.fdId=:fdid").setParam("fdid",
				template.getFdId());
		finder.append("and i.fdIndex=:itemIndex").setParam("itemIndex", stage);
		finder.append("and c.fdIndex=:cIndex").setParam("cIndex", step);
		List<TemplateContent> contents = find(finder);
		if (contents == null || contents.size() < 1)
			return null;
		return contents.get(0);
	}

	/**
	 * 获取资源的index 主要针对第一关第二步、第三关第二步、第二关的资源切换
	 * 
	 * @param resourceid
	 * @param bamProjectPhase
	 * @param stage
	 * @param step
	 * @return index
	 */
	@SuppressWarnings("rawtypes")
	public int getResourceIndex(String resourceid,
			BamProjectPhase bamProjectPhase, String stage, String step) {
		List list = null;

		if ("2".equals(stage)) {// 第二关（第一步、第二步）
			list = bamProjectPhase.getBamPackage().getBamOperation();
		} else if (("1".equals(stage) && "2".equals(step)) // 第一关（第二步）
				|| "3".equals(stage) && "2".equals(step)) {// 第三关（第二步）
			list = bamProjectPhase.getExamCategories();
		} else {
			return 0;
		}

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				IdEntity idEntity = (IdEntity) list.get(i);
				if (resourceid.equals(idEntity.getFdId())) {
					return i;
				}
			}
		}

		return 0;
	}

	/**
	 * 获取当前用户的流程下阶段和步骤的通过状态
	 * 
	 * @param userId
	 * @param flowId
	 * @param stage
	 *            指定阶段则只获取阶段
	 * 
	 * @return throughStatus 状态二维数组，0：未通过；1：通过； 为增加页面展现代码可读性，index值为0的数据可忽略
	 *         一维有效index为1-6，对应阶段1-6 二维有效index为1-4，对应步骤1-4
	 */
	@SuppressWarnings("unchecked")
	public int[][] getStepThroughStatus(String userId, String flowId, int stage) {
		List<BamProjectPhase> list = null;

		Finder finder = Finder
				.create("from BamProjectPhase phase left join fetch phase.stage stage left join fetch stage.project project ");
		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		if (stage != 0) {
			finder.append("and stage.fdIndex=:stageIndex").setParam(
					"stageIndex", stage);// 阶段
		}
		finder.append("and phase.newteachId=:nid").setParam("nid", userId);// 对应新教师
		finder.append("order by stage.fdIndex,phase.fdIndex");

		list = find(finder);

		int[][] throughStatus = new int[7][5];
		for (BamProjectPhase bph : list) {
			bph.getStage().getFdIndex();
			bph.getFdIndex();
			bph.getThrough();
			throughStatus[bph.getStage().getFdIndex()][bph.getFdIndex()] = bph
					.getThrough() == true ? 1 : 0;

			log.info("(" + bph.getFdId() + ")" + "第"
					+ bph.getStage().getFdIndex() + "关，第" + bph.getFdIndex()
					+ "步，状态：" + bph.getThrough());
		}

		return throughStatus;
	}

	/**
	 * 获取当前用户的通关信息
	 * 
	 * @param userId
	 * @param flowId
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap getSuccessResult(String userId, String flowId) {
		HashMap successResult = new HashMap();

		SysOrgPerson sysOrgPerson = accountService.load(userId);
		String endTime = getSuccessTime(userId, flowId);
		String successScore = getSuccessScore(userId, flowId);

		successResult.put("realname", sysOrgPerson.getRealName());
		successResult.put("englishname",
				Cn2SpellUtil.converterToSpell(sysOrgPerson.getRealName())
						.toUpperCase());
		successResult.put("endtime", endTime);
		successResult.put("poto", sysOrgPerson.getPoto());
		successResult.put("successScore", successScore);

		return successResult;
	}

	/**
	 * 获取通关时间
	 * 
	 * @param userId
	 * @param flowId
	 * 
	 * @return
	 */
	public String getSuccessTime(String userId, String flowId) {
		String endTime = "";

		Finder finder = Finder
				.create("from BamProjectPhase phase left join fetch phase.stage stage left join fetch stage.project project");
		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		finder.append("and stage.fdIndex=:stageIndex")
				.setParam("stageIndex", 6);// 阶段
		finder.append("and phase.fdIndex=:phaseIndex")
				.setParam("phaseIndex", 1);// 步骤
		finder.append("and phase.newteachId=:nid").setParam("nid", userId);// 对应新教师
		BamProjectPhase bamProjectPhase = findUnique(finder);

		endTime = bamProjectPhase.getEndTime().toString().substring(0, 10);

		return endTime;
	}

	/**
	 * 获取最终得分
	 * 
	 * @param userId
	 * @param flowId
	 * 
	 * @return
	 */
	public String getSuccessScore(String userId, String flowId) {
		double score = 0;
		double sumIndexToScore = 0;// 作业打分总分
		double avgGradeScore = 0;// 批课平均分
		double operationWeight = 0;// 作业权重
		double appofClossWeight = 0;// 批课权重
		DecimalFormat df = new DecimalFormat("#.##");

		// 获取作业步骤
		Finder finder = Finder
				.create("from BamProjectPhase phase left join fetch phase.stage stage left join fetch stage.project project");
		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		finder.append("and stage.fdIndex=:stageIndex")
				.setParam("stageIndex", 2);// 阶段
		finder.append("and phase.fdIndex=:phaseIndex")
				.setParam("phaseIndex", 1);// 步骤
		finder.append("and phase.newteachId=:nid").setParam("nid", userId);// 对应新教师
		BamProjectPhase bamProjectPhase = findUnique(finder);

		// 获取权重指标
		ProTemplate proTemplate = bamProjectPhase.getStage().getTemplateItem()
				.getTemplate();
		operationWeight = proTemplate.getFdOperationWeight().doubleValue() / 100;
		appofClossWeight = proTemplate.getFdAppofClass().doubleValue() / 100;

		// 获取作业平均分
		List<BamOperation> bamOperationList = bamProjectPhase.getBamPackage()
				.getBamOperation();
		if (bamOperationList != null && bamOperationList.size() > 0) {
			for (BamOperation bamOperation : bamOperationList) {
				List<BamIndex> bamIndexList = bamOperation.getBamIndexs();
				if (bamIndexList != null && bamIndexList.size() > 0) {
					for (BamIndex bamIndex : bamIndexList) {
						sumIndexToScore += bamIndex.getFdToValue();
					}
				}
			}
		}

		// 获取批课步骤
		finder = Finder
				.create("from BamProjectPhase phase left join fetch phase.stage stage left join fetch stage.project project");
		finder.append("where project.status=:status").setParam("status",
				BamProject.IS_OPEN);// 流程开启
		finder.append("and project.fdId=:fdId").setParam("fdId", flowId);// 对应流程
		finder.append("and stage.fdIndex=:stageIndex")
				.setParam("stageIndex", 5);// 阶段
		finder.append("and phase.fdIndex=:phaseIndex")
				.setParam("phaseIndex", 1);// 步骤
		finder.append("and phase.newteachId=:nid").setParam("nid", userId);// 对应新教师
		BamProjectPhase bamProjectPhase2 = findUnique(finder);

		// 获取批课平均分
		List<Grade> gtadeList = bamProjectPhase2.getGrades();
		if (gtadeList != null && gtadeList.size() > 0) {
			Grade grade = gtadeList.get(0);
			avgGradeScore += grade.getValue().doubleValue();// 获取批课平均分
		}

		/*
		 * S (总评分) = H (基础学术作业) * 0.3 + [ D (批课) * 20 ] * 0.7 (0 < H < 100, 0 <
		 * D < 100) 举例，S (总评分) = 79 * 0.3 + 4.3 * 20 * 0.7 = 23.7 + 60.2 = 83.9
		 */
		score = sumIndexToScore * operationWeight;
		score += avgGradeScore * 20 * appofClossWeight;

		return df.format(score);
	}
}
