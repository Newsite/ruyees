package cn.xdf.me.otp.action.base;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.model.base.AttMain;
import cn.xdf.me.otp.model.base.ContentMovie;
import cn.xdf.me.otp.service.CommentService;
import cn.xdf.me.otp.service.base.AttMainService;
import cn.xdf.me.otp.service.base.ContentMovieService;
import cn.xdf.me.otp.service.template.TemplateContentService;

/**
 * 文档视频
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:34:47
 */
@Controller
@RequestMapping(value = "/group/contentMovie")
@Scope("request")
public class ContentMovieController {

	@Autowired
	private ContentMovieService contentMovieService;
	@Autowired
	private TemplateContentService templateContentService;
	@Autowired
	private AttMainService attMainService;

	@Autowired
	private CommentService commentService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "contentMovie");
		Integer pn = 1;
		if (StringUtils.isNotBlank(pageNo)) {
			pn = Integer.parseInt(pageNo);
		}
		String fdType = request.getParameter("fdType");
		String fdName = request.getParameter("fdName");
		Finder finder = Finder
				.create("select c from ContentMovie c where 1=1 ");

		if (StringUtils.isNotBlank(fdType)) {
			finder.append("and c.fileType=:fdType");
			finder.setParam("fdType", Integer.parseInt(fdType));
			model.addAttribute("fdType", fdType);
		}
		if (StringUtils.isNotBlank(fdName)) {
			finder.append("and c.fdName like :fdName");
			finder.setParam("fdName", '%' + fdName + '%');
			model.addAttribute("fdName", fdName);
		}
		// finder.append("order by fdCreateTime DESC");
		finder.append("order by fdName asc");
		Pagination page = contentMovieService.getPage(finder, pn);
		model.addAttribute("page", page);
		// 关联删除提示
		String flag = request.getParameter("flag");
		model.addAttribute("flag", flag);
		return "/base/contentMovie/list";
	}

	@RequestMapping(value = "add")
	public String add(Model model, HttpServletRequest request) {
		return "/base/contentMovie/add";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(ContentMovie contentMovie,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		contentMovieService.saveContentMovie(contentMovie);
		return "redirect:/group/contentMovie/list";
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		dataBinder.setAutoGrowCollectionLimit(1000);
	}

	@RequestMapping(value = "delete")
	public String deleteById(RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		String fdId = request.getParameter("fdId");
		String fdType = request.getParameter("fdType");
		redirectAttributes.addAttribute("fdType", fdType);
		if (!isDel(redirectAttributes, fdId)) {
			return "redirect:/group/contentMovie/list";
		} else {
			// 删除附件
			ContentMovie contentMovie = contentMovieService.load(fdId);
			if (contentMovie.getAttMains() != null) {
				for (AttMain attMain : contentMovie.getAttMains()) {
					attMain.remove();
				}
			}
			contentMovieService.delete(fdId);
			return "redirect:/group/contentMovie/list";
		}
	}

	private boolean isDel(RedirectAttributes redirectAttributes, String... id) {
		Boolean flag = false;
		Finder finder = Finder
				.create("from TemplateContent c join c.contentMovies s where s.fdId in(:fdid)");
		finder.setParamList("fdid", id);
		flag = templateContentService.hasValue(finder);

		if (flag) {
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
		if (!isDel(redirectAttributes, ids)) {
			return "redirect:/group/contentMovie/list";
		}
		// 删除附件
		for (String id : ids) {
			ContentMovie contentMovie = contentMovieService.load(id);
			if (contentMovie.getAttMains() != null) {
				for (AttMain attMain : contentMovie.getAttMains()) {
					attMain.remove();
				}
				// contentMovie.getAttMain().remove();
			}
		}
		contentMovieService.delete(ids);
		return "redirect:/group/contentMovie/list";
	}

	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String id,
			HttpServletRequest request) {
		ContentMovie bean = contentMovieService.get(id);
		if (bean != null && bean.getAttMains() != null
				&& bean.getAttMains().size() > 100) {
			model.addAttribute("isView", 1);
		}
		model.addAttribute("bean", bean);
		return "/base/contentMovie/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String id) {
		model.addAttribute("bean", contentMovieService.get(id));
		return "/base/contentMovie/view";
	}

	@RequestMapping(value = "delAtt/{id}/{attId}")
	public String delAtt(HttpServletRequest request,
			@PathVariable("id") String id, @PathVariable("attId") String attId) {
		// 删除附件
		AttMain att = attMainService.get(AttMain.class, attId);
		att.remove();
		attMainService.delete(AttMain.class, attId);
		return "redirect:/group/contentMovie/edit/" + id;
	}

	@RequestMapping(value = "delAllAtt/{id}")
	public String delAllAtt(HttpServletRequest request,
			@PathVariable("id") String id) {
		List<AttMain> attMains = attMainService.findByProperty(AttMain.class,
				"contentMovie.fdId", id);
		for (AttMain attMain : attMains) {
			attMain.remove();
			attMainService.delete(AttMain.class, attMain.getFdId());
		}
		return "redirect:/group/contentMovie/edit/" + id;
	}

	@RequestMapping(value = "comment/{id}")
	public String comment(@PathVariable("id") String id, Model model,
			String pageNo) {
		int pn = NumberUtils.toInt(pageNo, 0);
		Finder finder = Finder
				.create("from Comment where conentMovieId=:cmid order by createdtime desc");
		finder.setParam("cmid", id);
		Pagination page = commentService.getPage(finder, pn);
		model.addAttribute("page", page);
		model.addAttribute("cwId", id);
		return "/base/contentMovie/comment";
	}

	@RequestMapping(value = "comment/delete/{fdId}")
	public String commentDelete(@PathVariable("fdId") String fdId,
			HttpServletRequest request) {
		String cwId = request.getParameter("cwid");
		commentService.delete(fdId);
		// return "redirect:/group/contentMovie/list";
		return "redirect:/group/contentMovie/comment/" + cwId;
	}
}
