package cn.xdf.me.otp.action.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Finder.SearchType;
import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.model.base.OperPackage;
import cn.xdf.me.otp.model.base.Operation;
import cn.xdf.me.otp.service.base.OperPackageService;
import cn.xdf.me.otp.service.base.OperationService;
import cn.xdf.me.otp.service.template.TemplateContentService;


/**
 * 作业步骤
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:36:01
 */
@Controller
@RequestMapping(value = "/group/operation")
public class OperationController {

	@Autowired
	private OperationService operationService;
	@Autowired
	private OperPackageService operPackageService;
	@Autowired
	private TemplateContentService templateContentService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		String fdPackageId = request.getParameter("fdPackageId");

		Finder finder = Finder.create("from Operation o where 1=1");
		finder.search("o.operPackage.fdId", fdPackageId, SearchType.EQ);
		finder.append("order by o.fdOrder");

		OperPackage pack = operPackageService.get(fdPackageId);
		model.addAttribute("fdPackageId", fdPackageId);
		model.addAttribute("packageName", pack.getFdName());

		Pagination page = operationService.getPage(finder,
				NumberUtils.createInteger(pageNo));
		model.addAttribute("page", page);
		// 关联删除提示
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		return "/base/operation/list";
	}

	@RequestMapping(value = "add")
	public String add(Model model, HttpServletRequest request) {
		String fdPackageId = request.getParameter("fdPackageId");
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		model.addAttribute("fdPackageId", fdPackageId);
		model.addAttribute("method", "add");

		return "/base/operation/edit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Operation operation, Model model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		String fdPackageId = request.getParameter("fdPackageId");
		String method = request.getParameter("method");
		// 判断数据是否重复
		if (operationService.isHas(operation, method, fdPackageId)) {
			redirectAttributes.addAttribute("flag", "has");
			redirectAttributes.addAttribute("fdPackageId", fdPackageId);
			return "redirect:/group/operation/add";
		}

		operationService.saveOperation(operation, fdPackageId);
		return String.format("redirect:/group/operation/list?fdPackageId=%s",
				fdPackageId);
	}

	@RequestMapping(value = "delete/{id}/{fdPackageId}")
	public String deleteById(RedirectAttributes redirectAttributes,
			@PathVariable("id") String id,
			@PathVariable("fdPackageId") String fdPackageId) {
		redirectAttributes.addAttribute("fdPackageId", fdPackageId);
		operationService.delete(id);
		return "redirect:/group/operation/list";
	}

	@RequestMapping(value = "delete")
	public String delete(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		redirectAttributes.addAttribute("fdPackageId",
				request.getParameter("fdPackageId"));
		String[] ids = request.getParameterValues("ids");
		operationService.delete(ids);
		return "redirect:/group/operation/list";
	}

	@RequestMapping(value = "edit")
	public String edit(Model model, HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		Operation operation = operationService.get(fdId);

		String fdPackageId = request.getParameter("fdPackageId");
		model.addAttribute("fdPackageId", fdPackageId);

		model.addAttribute("bean", operation);
		model.addAttribute("method", "edit");
		return "/base/operation/edit";
	}

	@RequestMapping(value = "view")
	public String view(Model model, HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		Operation operation = operationService.get(fdId);

		String fdPackageId = request.getParameter("fdPackageId");
		model.addAttribute("fdPackageId", fdPackageId);
		model.addAttribute("bean", operation);
		return "/base/operation/view";
	}
}
