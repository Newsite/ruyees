package ruyees.otp.action.base;import java.util.List;import javax.servlet.http.HttpServletRequest;import org.apache.commons.lang3.math.NumberUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.servlet.mvc.support.RedirectAttributes;import ruyees.otp.common.hibernate4.Finder;import ruyees.otp.common.page.Pagination;import ruyees.otp.common.utils.ComUtils;import ruyees.otp.model.flow.BamProject;import ruyees.otp.model.flow.BamProjectMember;import ruyees.otp.model.flow.BamProjectPhase;import ruyees.otp.model.flow.Grade;import ruyees.otp.model.flow.GradeItem;import ruyees.otp.model.flow.Tickling;import ruyees.otp.model.notify.NotifyAcceptUser;import ruyees.otp.model.notify.NotifyEnum;import ruyees.otp.model.notify.NotifyTodo;import ruyees.otp.model.notify.NotifyTypeEnum;import ruyees.otp.service.AccountService;import ruyees.otp.service.ShiroDbRealm.ShiroUser;import ruyees.otp.service.base.ReportFormsService;import ruyees.otp.service.notify.NotifyTodoService;import ruyees.otp.utils.ShiroUtils;/** * 统计报表 *  * @author Zaric * @date 2013-6-1 上午12:35:48 */@Controller@RequestMapping(value = "/coach/progress")public class NewProgressController {	@Autowired	private ReportFormsService reportFormsService;	@Autowired	private AccountService accountService;	@Autowired	private NotifyTodoService notifyTodoService;	/**	 * 进入查询页面	 * 	 * @param model	 * @param pageNo	 * @param param	 * @param request	 * @return	 */	@RequestMapping(value = "list", method = RequestMethod.GET)	public String list(Model model, String pageNo, HttpServletRequest request)			throws Exception {		model.addAttribute("active", "coachprogress");		ShiroUser user = ShiroUtils.getUser();		String uId = user.id;		Finder finder = Finder				.create("from BamProjectMember b left join fetch b.project project where project.status=:status");		finder.setParam("status", BamProject.IS_OPEN);		finder.append("and b.guidId=:guidId").setParam("guidId", uId);		Pagination page = reportFormsService.getPage(finder,				NumberUtils.createInteger(pageNo));		for (Object obj : page.getList()) {			BamProjectMember member = (BamProjectMember) obj;			member.setNewTeach(accountService.findById(member.getNewteachId()));		}		model.addAttribute("page", page);		return "/base/progress/list";	}	/**	 * 查询每个人的各阶段情况	 * 	 * @param report	 * @param pageNo	 * @param request	 * @return	 */	@RequestMapping(value = "stageList/{projectId}/{newTeachId}", method = RequestMethod.GET)	public String stageList(Model model,			@PathVariable("projectId") String projectId,			@PathVariable("newTeachId") String newTeachId, String pageNo,			HttpServletRequest request) {		Finder finder = Finder				.create("from BamStage b where b.project.fdId=:projectId and b.newteachId=:newTeachId");		finder.setParam("projectId", projectId);		finder.setParam("newTeachId", newTeachId);		Pagination page = reportFormsService.getPage(finder,				NumberUtils.createInteger(pageNo));		model.addAttribute("page", page);		return "/base/progress/stage_list";	}	/**	 * 查询每个人的各阶段的各种内容	 * 	 * @param report	 * @param pageNo	 * @param request	 * @return	 */	@RequestMapping(value = "contentList/{projectId}/{stageId}/{newTeachId}/{stageNo}", method = RequestMethod.GET)	public String contentList(Model model,			@PathVariable("projectId") String projectId,			@PathVariable("stageId") String stageId,			@PathVariable("newTeachId") String newTeachId,			@PathVariable("stageNo") String stageNo, String pageNo,			HttpServletRequest request) {		Finder finder = Finder				.create("from BamProjectPhase b where b.stage.fdId=:stageId and b.newteachId=:newteachId");		finder.setParam("stageId", stageId);		finder.setParam("newteachId", newTeachId);		Pagination page = reportFormsService.getPage(finder,				NumberUtils.createInteger(pageNo));		model.addAttribute("page", page);		if ("5".equals(stageNo)) {			BamProjectPhase phase = (BamProjectPhase) page.getList().get(0);			List<Grade> grades = phase.getGrades();			if (grades != null && grades.size() > 0) {				Grade grade = grades.get(0);				for (GradeItem item : grade.getGradeItems()) {					item.setNewTeach(accountService.findById(item.getUid()));					item.setAdvier(accountService.findById(item.getAdvierId()));				}				for (Tickling tick : grade.getTicklings()) {					tick.setAdvier(accountService.findById(grade.getGuidId()));				}			}		}		model.addAttribute("stageNo", stageNo);		model.addAttribute("projectId", projectId);		model.addAttribute("newTeachId", newTeachId);		return "/base/progress/content_list";	}	/**	 * 查询每个人的各阶段情况	 * 	 * @param report	 * @param pageNo	 * @param request	 * @return	 */	@RequestMapping(value = "toAdd/{newTeachId}", method = RequestMethod.GET)	public String toAdd(Model model,			@PathVariable("newTeachId") String newTeachId,			HttpServletRequest request) {		ShiroUser user = ShiroUtils.getUser();		String uId = user.id;		model.addAttribute("guidId", uId);		model.addAttribute("newTeachId", newTeachId);		return "/base/progress/to_edit";	}	@RequestMapping(value = "toSave", method = RequestMethod.POST)	public String toSave(NotifyTodo notifyTodo,			RedirectAttributes redirectAttributes, HttpServletRequest request) {		List<NotifyAcceptUser> accepList = notifyTodo.getAcceptUser();		for (NotifyAcceptUser accept : accepList) {			accept.setSendTime(ComUtils.now());			accept.setNotifyTodo(notifyTodo);		}		ShiroUser user = ShiroUtils.getUser();		String uId = user.id;		notifyTodo.setSendUser(accountService.findById(uId));		notifyTodo.setAcceptUser(accepList);		notifyTodo.setNotifyEnum(NotifyEnum.MESSAGE);		notifyTodo.setNotifyTypeEnum(NotifyTypeEnum.TODO);		notifyTodoService.save(notifyTodo);		return "redirect:/coach/progress/list";	}}