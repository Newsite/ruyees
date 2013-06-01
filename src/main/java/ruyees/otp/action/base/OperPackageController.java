package ruyees.otp.action.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.model.base.OperPackage;
import ruyees.otp.service.base.OperPackageService;
import ruyees.otp.service.template.TemplateContentService;

/**
 * 作业包
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:36:15
 */
@Controller
@RequestMapping(value = "/group/operPackage")
public class OperPackageController {

	@Autowired
	private OperPackageService operPackageService;
	@Autowired
	private TemplateContentService templateContentService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "operPackage");
		Integer pn = 1;
		if (StringUtils.isNotBlank(pageNo)) {
			pn = Integer.parseInt(pageNo);
		}
		// query
		String fdName = request.getParameter("fdName");
		Finder finder = Finder.create("from OperPackage o");
		if (StringUtils.isNotBlank(fdName)) {
			finder.append("where o.fdName like :fdName");
			finder.setParam("fdName", fdName + '%');
			model.addAttribute("fdName", fdName);
		}
		finder.append("order by o.fdName");
		Pagination page = operPackageService.getPage(finder, pn);
		model.addAttribute("page", page);
		// 关联删除提示
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		return "/base/operPackage/list";
	}

	@RequestMapping(value = "add")
	public String add(Model model) {
		return "/base/operPackage/edit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(OperPackage operPackage,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		operPackageService.saveOperPackage(operPackage);
		return "redirect:/group/operPackage/list";
	}

	@RequestMapping(value = "delete/{id}")
	public String deleteById(RedirectAttributes redirectAttributes,
			@PathVariable("id") String id) {
		if (!isDel(redirectAttributes, id)) {
			return "redirect:/group/operPackage/list";
		} else {
			operPackageService.delete(id);
			return "redirect:/group/operPackage/list";
		}
	}

	public boolean isDel(RedirectAttributes redirectAttributes, String id) {
		Boolean flag = false;
		Finder finder = Finder
				.create("from TemplateContent c join c.operPackage s where s.fdId=:fdid");
		finder.setParam("fdid", id);
		flag = templateContentService.hasValue(finder);
		if (flag) {
			redirectAttributes.addAttribute("flag", "no");
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "delete")
	public String delete(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		operPackageService.delete(ids);
		return "redirect:/group/operPackage/list";
	}

	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		OperPackage operPackage = operPackageService.get(id);
		model.addAttribute("bean", operPackage);
		return "/base/operPackage/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String id) {
		OperPackage operPackage = operPackageService.get(id);
		model.addAttribute("bean", operPackage);
		return "/base/operPackage/view";
	}

}
