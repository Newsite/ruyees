package ruyees.otp.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.model.sys.SysOrgElement;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.model.sys.SysOrgPersonTemp;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.RegisterService;
import ruyees.otp.service.SysOrgElementService;
import ruyees.otp.service.flow.BamProjectService;
import ruyees.otp.utils.ShiroUtils;

/**
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:31:23
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private SysOrgElementService sysOrgElementService;

	@Autowired
	private BamProjectService bamProjectService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid SysOrgPerson user,
			RedirectAttributes redirectAttributes) {
		accountService.registerUser(user);
		redirectAttributes.addFlashAttribute("username", user.getLoginName());
		return "redirect:/login";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") String id) {
		registerService.deleteById(id);
		return "redirect:/register/list";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (accountService.findUserByLoginName(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}

	/*
	 * 管理员添加新教师
	 */
	@RequestMapping(value = "add")
	public String add(Model model) {
		// 管理员学校主管注册新教师
		String userId = ShiroUtils.getUser().getId();
		SysOrgPerson person = accountService.get(userId);
		SysOrgElement element = person.getHbmParent();
		String orgId = "";
		if (element != null) {
			if (element.getFdOrgType() == 1) {
				orgId = element.getFdId();
			} else {
				if (element.getHbmParent() != null)
					orgId = element.getHbmParentOrg().getFdId();
			}
		}
		model.addAttribute("orgId", orgId);

		return "/base/register/add";
	}

	/*
	 * 管理员编辑新教师
	 */
	@RequestMapping(value = "edit/{fdId}")
	public String edit(Model model, @PathVariable("fdId") String fdId) {
		SysOrgPersonTemp sysOrgPersonTemp = registerService.get(
				SysOrgPersonTemp.class, fdId);
		model.addAttribute("bean", sysOrgPersonTemp);
		SysOrgPerson sys = registerService.findUniqueByProperty(
				SysOrgPerson.class,
				Value.eq("loginName", sysOrgPersonTemp.getFdIdentityCard()));
		model.addAttribute("sys", sys);
		// 取登录机构
		String userId = ShiroUtils.getUser().getId();
		SysOrgPerson person = accountService.get(userId);
		SysOrgElement element = person.getHbmParent();
		String orgId = "";
		if (element != null) {
			if (element.getFdOrgType() == 1) {
				orgId = element.getFdId();
			} else {
				if (element.getHbmParent() != null)
					orgId = element.getHbmParentOrg().getFdId();
			}
		}
		model.addAttribute("orgId", orgId);

		return "/base/register/edit";
	}

	/*
	 * 管理员修改新教师保存
	 */
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(SysOrgPersonTemp syst, HttpServletRequest request) {
		String icoUrl = request.getParameter("fdIcoUrl");
		String sysId = request.getParameter("sysId");

		registerService.updateSop(syst, icoUrl, sysId);

		return "redirect:/register/list";
	}

	/*
	 * 管理员添加新教师保存
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(SysOrgPerson sysOrgPerson, HttpServletRequest request) {
		String icoUrl = request.getParameter("fdIcoUrl");

		registerService.register(sysOrgPerson, icoUrl);
		if (ShiroUtils.getUser() == null) {
			return "redirect:/login";
		}
		return "redirect:/register/list";
	}

	/*
	 * 查看本地新教师注册信息
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		if (ShiroUtils.getUser() == null) {
			return "redirect:/login";
		}
		String userId = ShiroUtils.getUser().getId();
		SysOrgPerson person = accountService.load(userId);
		SysOrgElement orgElement = null;

		String deptId = person.getDepatId();
		if (StringUtils.isNotBlank(deptId)) {// admin无deptId
			SysOrgElement element = sysOrgElementService.load(deptId);
			if (element.getFdOrgType() == 1) {
				orgElement = element;
			} else {
				orgElement = element.getHbmParentOrg();
			}
		}

		List<String> ids = new ArrayList<String>();
		if (!ShiroUtils.hasAnyRoles("admin,group")) {
			if (orgElement != null) {
				List<SysOrgElement> children = orgElement.getFdAllChildren();
				for (SysOrgElement e : children) {
					ids.add(e.getFdId());
				}
				ids.add(orgElement.getFdId());
			}
		}

		Finder finder = Finder.create("from SysOrgPersonTemp");
		if (ids != null && ids.size() > 0) {
			finder.append("where depatId in (:ids)").setParamList("ids",
					ids.toArray());
		}
		Pagination page = registerService.getPage(finder,
				NumberUtils.createInteger(pageNo));
		model.addAttribute("page", page);
		model.addAttribute("active", "register");
		return "/base/register/list";
	}

	/*
	 * 查看本地新教师注册信息
	 */
	@RequestMapping(value = "view/{fdId}")
	public String view(Model model, @PathVariable("fdId") String fdId) {
		SysOrgPersonTemp sysOrgPersonTemp = registerService.get(
				SysOrgPersonTemp.class, fdId);

		model.addAttribute("bean", sysOrgPersonTemp);

		if (sysOrgPersonTemp.getSenderId() != null
				&& !"".equals(sysOrgPersonTemp.getSenderId())) {
			SysOrgPerson sysOrgPerson = registerService.get(SysOrgPerson.class,
					sysOrgPersonTemp.getSenderId());
			model.addAttribute("sender", sysOrgPerson);
		}
		if (sysOrgPersonTemp.getRegisterId() != null
				&& !"".equals(sysOrgPersonTemp.getRegisterId())) {
			SysOrgPerson sysOrgPerson = registerService.get(SysOrgPerson.class,
					sysOrgPersonTemp.getRegisterId());
			model.addAttribute("register", sysOrgPerson);
		}

		return "/base/register/view";
	}

	/*
	 * 给新教师发送邮件
	 */
	@RequestMapping(value = "mail")
	public String mail(Model model) {
		return "/base/register/mail";
	}

	/*
	 * 发送注册邮件
	 */
	@RequestMapping(value = "sendMail", method = RequestMethod.POST)
	public String sendMail(SysOrgPersonTemp sysOrgPersonTemp,
			HttpServletRequest request) {

		registerService.sendMail(sysOrgPersonTemp);

		return "redirect:/register/list";
	}
}
