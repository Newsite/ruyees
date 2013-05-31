package cn.xdf.me.otp.action.trainee;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.xdf.me.otp.common.utils.array.ArrayUtils;
import cn.xdf.me.otp.common.utils.array.SortType;
import cn.xdf.me.otp.model.base.EvalItem;
import cn.xdf.me.otp.model.flow.BamProject;
import cn.xdf.me.otp.model.flow.BamProjectMember;
import cn.xdf.me.otp.model.flow.BamProjectPhase;
import cn.xdf.me.otp.model.flow.Grade;
import cn.xdf.me.otp.model.flow.GradeItem;
import cn.xdf.me.otp.model.flow.Tickling;
import cn.xdf.me.otp.model.sys.SysOrgPerson;
import cn.xdf.me.otp.model.template.ProTemplate;
import cn.xdf.me.otp.service.AccountService;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.base.GradeItemService;
import cn.xdf.me.otp.service.base.GradeService;
import cn.xdf.me.otp.service.base.TicklingService;
import cn.xdf.me.otp.service.flow.TraineeService;

/**
 * 新教师备课首页 详细说明请查看:
 * 
 * @see cn.xdf.me.otp.service.flow.TraineeService <p/>
 * @author Zaric
 * @date  2013-6-1 上午12:38:47
 */
@Controller
@RequestMapping(value = "/trainee")
@Scope("request")
public class TraineeController {

	@Autowired
	private TraineeService traineeService;

	@Autowired
	private GradeService gradeService;
	
	@Autowired
	private GradeItemService gradeItemService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TicklingService ticklingService;

	/**
	 * 欢迎 选择流程
	 * 
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome(Model model) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		// 查询人员对应的流程信息
		/*
		 * List<BamProject> bamProjects = traineeService
		 * .findEnableUserBamProjectByUid(uId);
		 */
		// List<FlowModel> beans = traineeService.findEnableUserFlowModels(uId);
		List<BamProject> bamProjects = traineeService.findBamProjectByUid(uId, TraineeService.QUERY_TYPE_ALL);
		ArrayUtils.sortListByProperty(bamProjects, "orderName", SortType.HIGHT);
		SysOrgPerson person = accountService.findById(uId);
		model.addAttribute("person", person);
		model.addAttribute("beans", bamProjects);
		return "/trainee/welcome";
	}

	/**
	 * 选关 进入备课流程的总开关
	 * 
	 * @return
	 */
	@RequestMapping(value = "/to/{name}", method = RequestMethod.GET)
	public String home(Model model, @PathVariable("name") String name) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		BamProjectMember member = traineeService.findProjectMemberByUidAndFlowName(uId, name);
		model.addAttribute("statges", traineeService.findStageByUidAndFlowName(uId, name));
		model.addAttribute("member", member);
		model.addAttribute("name", name);
		return "/trainee/home";
	}

	@RequestMapping(value = "/main/{name}", method = RequestMethod.GET)
	public String main(Model model, @PathVariable("name") String name) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		BamProject bamProject = traineeService.findBamProjectByUidAndFlowName(uId, name);
		if (bamProject == null) {// 这里应该直接转到提示界面“你不能访问该页面。”
			return "/trainee/step/main";
		}
		ProTemplate template = bamProject.getTemplate();
		model.addAttribute("statges", traineeService.findStageByUidAndFlowName(uId, name));
		BamProjectMember member = traineeService.findProjectMemberByUidAndFlowName(uId, name);
		model.addAttribute("member", member);
		model.addAttribute("bean", template);
		model.addAttribute("name", name);
		return "/trainee/step/main";
	}

	/**
	 * 
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/main/{name}/{stageIndex}", method = RequestMethod.GET)
	public String mainStage(Model model, @PathVariable("name") String name, @PathVariable("stageIndex") String stageIndex) {
		int index = 1;
		if (StringUtils.isNotBlank(stageIndex)) {
			index = NumberUtils.toInt(stageIndex, 1);
		}
		model.addAttribute("stageIndex", index);
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		model.addAttribute("statges", traineeService.findStageByUidAndFlowName(uId, name));
		return main(model, name);
	}

	@RequestMapping(value = "/main/{name}/{stageIndex}/{phaseIndex}", method = RequestMethod.GET)
	public String mainStageToIndex(Model model, @PathVariable("name") String name, @PathVariable("stageIndex") String stageIndex, @PathVariable("phaseIndex") String phaseIndex) {
		if ("5".equals(stageIndex) && "1".equals(phaseIndex)) { // 若批课老师打完分就直接进入5.2
			phaseIndex = gradeService.getGradeStep(name, stageIndex, phaseIndex);
		}

		model.addAttribute("step", phaseIndex);
		return mainStage(model, name, stageIndex);
	}

	/**
	 * 点击选关后进入 阶段 步骤 进入关口
	 * 
	 * @param model
	 * @param name
	 *            流程名称
	 * @param stage
	 *            阶段
	 * @param step
	 *            步骤
	 * @return
	 */
	@RequestMapping(value = "/step/{name}/{stage}/{step}", method = RequestMethod.GET)
	public String step(Model model, @PathVariable("name") String name, @PathVariable("stage") String stage, @PathVariable("step") String step) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		// traineeService.findBamStep(uId,name,Integer.parseInt(stage),step);
		BamProjectPhase content = traineeService.findBamStep(uId, name, Integer.parseInt(stage), Integer.parseInt(step));
		if (!content.getEnable()) {
			return String.format("/trainee/step/error");
		}
		BamProjectMember member = traineeService.findProjectMemberByUidAndFlowName(uId, name);

		// 获取阶段步骤的通过状态数组
		int[][] throughStatus = traineeService.getStepThroughStatus(uId, name, Integer.parseInt(stage));
		model.addAttribute("throughStatus", throughStatus);

		// 处理第五关批课
		if (stage.equals("5")) {
			// 打分分项
			List<GradeItem> items = gradeService.getGradeItemList(content, uId);
			// 日历初始化
			String gradeList = gradeService.getGradeListJson(items, step);
			model.addAttribute("gradeList", gradeList);
			// 若批课老师打完分就直接进入5.2
			if (step.equals("1")) {
				step = gradeService.getGradeStep(name, stage, step);
			}
			// 导师打分及评分项
			if (step.equals("2")) {
				Grade grade = gradeService.getGrade(content, uId);
				List<Tickling> ticks = ticklingService.getTicklingList(content, uId);
				if(ticks.size()==0){
					List<GradeItem> gradeItems=gradeItemService.getGradeItemList(content, uId);
					model.addAttribute("flag", 0);
					model.addAttribute("gradeItems", gradeItems);
				}
				List<EvalItem> evals = gradeService.getEvalItemList(content);
				model.addAttribute("grade", grade);
				if(ticks.size()>0){
					model.addAttribute("flag", 1);
					model.addAttribute("ticks", ticks);
				}
				model.addAttribute("evals", evals);
			}
			model.addAttribute("items", items);
			model.addAttribute("itemSize", items.size());
		}
		model.addAttribute("member", member);
		model.addAttribute("name", name);
		model.addAttribute("user", user);
		model.addAttribute("content", content);
		return String.format("/trainee/step/%s/%s", stage, step);
	}

	/**
	 * 步骤中刷新内容
	 * 
	 * @param model
	 * @param name
	 *            流程Id
	 * @param stage
	 *            阶段
	 * @param step
	 *            步骤
	 * @param index
	 *            内容索引
	 * @return
	 */
	@RequestMapping(value = "/step/{name}/{stage}/{step}/{resourceid}", method = RequestMethod.GET)
	public String content(Model model, @PathVariable("name") String name, @PathVariable("stage") String stage, @PathVariable("step") String step, @PathVariable("resourceid") String resourceid) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		BamProjectPhase content = traineeService.findBamStep(uId, name, Integer.parseInt(stage), Integer.parseInt(step));
		BamProjectMember member = traineeService.findProjectMemberByUidAndFlowName(uId, name);
		int index = traineeService.getResourceIndex(resourceid, content, stage, step);

		// 获取阶段步骤的通过状态数组
		int[][] throughStatus = traineeService.getStepThroughStatus(uId, name, Integer.parseInt(stage));
		model.addAttribute("throughStatus", throughStatus);

		model.addAttribute("index", index);
		model.addAttribute("member", member);
		model.addAttribute("name", name);
		model.addAttribute("content", content);
		model.addAttribute("resourceid", resourceid);
		return String.format("/trainee/step/%s/%s", stage, step);
	}

	/**
	 * 成功
	 * 
	 * @param model
	 * @param name
	 *            流程Id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/success/{name}", method = RequestMethod.GET)
	public String success(Model model, @PathVariable("name") String name) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;

		HashMap successResult = traineeService.getSuccessResult(uId, name);
		model.addAttribute("successResult", successResult);
		model.addAttribute("model", traineeService.findBamProjectByUidAndFlowName(uId, name));
		model.addAttribute("name", name);
		return "/trainee/success";
	}
	
	
	/**
	 * 成功
	 * 
	 * @param model
	 * @param name
	 *            流程Id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/success/self/{name}", method = RequestMethod.GET)
	public String successToSelf(Model model, @PathVariable("name") String name) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;

		HashMap successResult = traineeService.getSuccessResult(uId, name);
		model.addAttribute("successResult", successResult);
		model.addAttribute("model", traineeService.findBamProjectByUidAndFlowName(uId, name));
		model.addAttribute("name", name);
		return "/trainee/successself";
	}


}
