package cn.xdf.me.otp.action.base;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.model.base.ExamCategory;
import cn.xdf.me.otp.service.base.ExamCategoryService;
import cn.xdf.me.otp.service.template.TemplateContentService;

/**
 * 试卷
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:35:17
 */
@Controller
@RequestMapping(value = "/group/examCategory")
public class ExamCategoryController {

	@Autowired
	private ExamCategoryService examCategoryService;
	@Autowired
	private TemplateContentService templateContentService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "examCategory");
		Integer pn = 1;
		if (StringUtils.isNotBlank(pageNo)) {
			pn = Integer.parseInt(pageNo);
		}
		// query
		String fdName = request.getParameter("fdName");
		Finder finder = Finder.create("from ExamCategory e");
		if (StringUtils.isNotBlank(fdName)) {
			finder.append("where e.fdName like :fdName");
			finder.setParam("fdName", fdName + '%');
			model.addAttribute("fdName", fdName);
		}
		finder.append("order by e.fdName ASC");

		Pagination page = examCategoryService.getPage(finder, pn);
		model.addAttribute("page", page);

		// 关联删除提示
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		return "/base/examCategory/list";
	}

	@RequestMapping(value = "add")
	public String add(Model model) {
		return "/base/examCategory/edit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(ExamCategory examCategory,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		examCategoryService.saveExamCategory(examCategory);
		return "redirect:/group/examCategory/list";
	}

	@RequestMapping(value = "delete/{id}")
	public String deleteById(RedirectAttributes redirectAttributes,
			@PathVariable("id") String id) {
		if (!isDel(redirectAttributes, id)) {
			return "redirect:/group/examCategory/list";
		} else {
			examCategoryService.delete(id);
			return "redirect:/group/examCategory/list";
		}
	}

	public boolean isDel(RedirectAttributes redirectAttributes, String id) {
		Boolean flag = false;
		Finder finder = Finder
				.create("from TemplateContent c join c.examCategories s where s.fdId=:fdid");
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
		examCategoryService.delete(ids);
		return "redirect:/group/examCategory/list";
	}

	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		ExamCategory examCategory = examCategoryService.get(id);
		model.addAttribute("bean", examCategory);
		return "/base/examCategory/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String id) {
		ExamCategory examCategory = examCategoryService.get(id);
		model.addAttribute("bean", examCategory);
		return "/base/examCategory/view";
	}
}
