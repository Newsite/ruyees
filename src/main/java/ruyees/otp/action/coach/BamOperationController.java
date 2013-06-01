package ruyees.otp.action.coach;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.model.flow.BamOperation;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;
import ruyees.otp.service.flow.BamOperationService;

/**
 * 指导教师审批作业步骤
 * @author Zaric
 * @date  2013-6-1 上午12:37:34
 */
@Controller
@RequestMapping(value = "/coach/operation")
@Scope("request")
public class BamOperationController {

	@Autowired
	private BamOperationService bamOperationService;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "list")
	public String list(Model model, String pageNo, HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		Finder finder = Finder.create("select b from BamOperation b left join fetch b.bamPackage pack left join fetch pack.phase phase where phase.guidId=:guidId and b.through in (:through)");
		
		finder.setParam("guidId", uId);
		finder.setParamList("through", new Boolean[]{true,false}); // true:通过,false:驳回
		finder.append("order by b.through,b.fdId");
		Pagination page = bamOperationService.getPage(finder, NumberUtils.createInteger(pageNo));

		for(Object obj : page.getList()){
			BamOperation operation = (BamOperation)obj;
			SysOrgPerson person = accountService.findById(operation.getBamPackage().getPhase().getNewteachId());
			operation.getBamPackage().getPhase().setNewTeach(person);
		}
		model.addAttribute("page", page);
		return "/base/operStep/list";
	}

	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String operationId) {
		BamOperation bamOperation=bamOperationService.load(operationId);
		SysOrgPerson person = accountService.findById(bamOperation.getBamPackage().getPhase().getNewteachId());
		model.addAttribute("person", person);
		model.addAttribute("bean", bamOperation);
		return "/base/operStep/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String operationId) {
		model.addAttribute("bean", bamOperationService.load(operationId));
		return "/base/operStep/view";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(BamOperation bamOperation, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		bamOperationService.setThrough(bamOperation);
		return "redirect:/coach/operation/list";
	}

}