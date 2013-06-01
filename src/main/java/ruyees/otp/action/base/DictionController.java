package ruyees.otp.action.base;

import java.util.List;

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
import ruyees.otp.common.web.springmvc.annotation.FormBean;
import ruyees.otp.model.base.Diction;
import ruyees.otp.model.template.Constant;
import ruyees.otp.service.base.DictionService;
import ruyees.otp.service.template.ProTemplateService;


/**
 * 字典
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:35:04
 */
@Controller
@RequestMapping(value = "/group/diction")
public class DictionController {

	@Autowired
	private DictionService dictionService;
	@Autowired
	private ProTemplateService proTemplateService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "diction");
		Integer pn = 1;
		if (StringUtils.isNotBlank(pageNo)) {
			pn = Integer.parseInt(pageNo);
		}
		String fdType = request.getParameter("fdType");
		Finder finder = Finder.create("from Diction p");

		if (StringUtils.isNotBlank(fdType)) {
			finder.append("where p.fdType=:fdType");
			finder.setParam("fdType", Integer.parseInt(fdType));
		}
		model.addAttribute("fdType", fdType);
		finder.append("order by p.fdType ASC,p.fdSeqNum ASC");
		Pagination page = dictionService.getPage(finder, pn);
		model.addAttribute("page", page);
		// 关联删除提示
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);

		return "/base/diction/list";
	}

	@RequestMapping(value = "add")
	public String add(Model model) {
		return "/base/diction/edit";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@FormBean("bean") Diction diction,
			RedirectAttributes redirectAttributes) {
		dictionService.saveDiction(diction);
		return "redirect:/group/diction/list";
	}

	@RequestMapping(value = "delete")
	public String deleteById(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		String fdType = request.getParameter("fdType");

		redirectAttributes.addAttribute("fdType", fdType);
		if (!isDel(redirectAttributes, fdId)) {
			return "redirect:/group/diction/list";
		} else {
			dictionService.delete(fdId);
			return "redirect:/group/diction/list";
		}
	}

	@SuppressWarnings("unchecked")
	public boolean isDel(RedirectAttributes redirectAttributes, String id) {
		Diction diction = dictionService.load(id);
		Integer fdType = diction.getFdType();
		Finder finder = Finder.create("from ProTemplate p where 1=1");
		if (fdType == Constant.DICTION_PROGRAM) {
			finder.append("and p.program.fdId=:fdId");
		} else if (fdType == Constant.DICTION_COURSE) {
			finder.append("and p.course.fdId=:fdId");
		} else if (fdType == Constant.DICTION_ITEM) {
			finder.append("and p.item.fdId=:fdId");
		} else {
			finder.append("and p.stage.fdId=:fdId");
		}
		finder.setParam("fdId", id);
		List<Diction> lists = proTemplateService.find(finder);

		if (lists.size() > 0) {
			redirectAttributes.addAttribute("flag", "no");
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "delAll")
	public String delete(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		dictionService.delete(ids);
		return "redirect:/group/diction/list";
	}

	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String id) {
		model.addAttribute("bean", dictionService.load(id));
		return "/base/diction/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String id) {
		model.addAttribute("bean", dictionService.load(id));
		return "/base/diction/view";
	}
}
