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
import cn.xdf.me.otp.model.base.Exam;
import cn.xdf.me.otp.model.base.ExamCategory;
import cn.xdf.me.otp.service.base.ExamCategoryService;
import cn.xdf.me.otp.service.base.ExamService;
import cn.xdf.me.otp.service.template.TemplateContentService;

@Controller
@RequestMapping(value = "/group/exam")
public class ExamController {

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamCategoryService examCategoryService;
	@Autowired
	private TemplateContentService templateContentService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {

		String fdCategoryId = request.getParameter("fdCategoryId");
		ExamCategory cate = (ExamCategory) examCategoryService
				.get(fdCategoryId);
		model.addAttribute("fdCategoryId", fdCategoryId);
		model.addAttribute("fdCategoryName", cate.getFdName());

		Finder finder = Finder
				.create("select e from Exam e left join e.examCategory c where 1=1");
		finder.search("c.fdId", fdCategoryId, SearchType.EQ);
		finder.append("order by e.fdOrder");

		Pagination page = examService.getPage(finder,
				NumberUtils.createInteger(pageNo));
		model.addAttribute("page", page);
		// 关联删除提示
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		return "/base/exam/list";
	}

	@RequestMapping(value = "add")
	public String add(Model model, HttpServletRequest request) {
		String fdCategoryId = request.getParameter("fdCategoryId");
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		model.addAttribute("fdCategoryId", fdCategoryId);
		model.addAttribute("method", "add");

		return "/base/exam/edit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(Exam exam, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		String fdCategoryId = request.getParameter("fdCategoryId");
		String method = request.getParameter("method");
		// 判断数据是否重复
		if (examService.isHas(exam, method, fdCategoryId)) {
			redirectAttributes.addAttribute("flag", "has");
			redirectAttributes.addAttribute("fdCategoryId", fdCategoryId);
			return "redirect:/group/exam/add";
		}

		examService.saveExam(exam, fdCategoryId);
		redirectAttributes.addAttribute("fdCategoryId", fdCategoryId);
		return "redirect:/group/exam/list";
	}

	@RequestMapping(value = "delete/{id}/{fdCategoryId}")
	public String deleteById(RedirectAttributes redirectAttributes,
			@PathVariable("id") String id,
			@PathVariable("fdCategoryId") String fdCategoryId) {
		redirectAttributes.addAttribute("fdCategoryId", fdCategoryId);
		examService.delete(id);
		return "redirect:/group/exam/list";
	}

	@RequestMapping(value = "delete")
	public String delete(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		redirectAttributes.addAttribute("fdCategoryId",
				request.getParameter("fdCategoryId"));
		String[] ids = request.getParameterValues("ids");
		examService.delete(ids);
		return "redirect:/group/exam/list";
	}

	@RequestMapping(value = "edit")
	public String edit(Model model, HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		Exam exam = examService.get(fdId);

		String fdCategoryId = request.getParameter("fdCategoryId");
		model.addAttribute("fdCategoryId", fdCategoryId);

		model.addAttribute("bean", exam);
		model.addAttribute("method", "edit");
		return "/base/exam/edit";
	}

	@RequestMapping(value = "view")
	public String view(Model model, HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		Exam exam = examService.get(fdId);

		String fdCategoryId = request.getParameter("fdCategoryId");
		model.addAttribute("fdCategoryId", fdCategoryId);
		model.addAttribute("bean", exam);
		return "/base/exam/view";
	}

}
