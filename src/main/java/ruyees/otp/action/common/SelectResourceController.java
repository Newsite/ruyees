package ruyees.otp.action.common;import java.util.HashMap;import java.util.Map;import javax.servlet.http.HttpServletRequest;import org.apache.commons.lang3.StringUtils;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import ruyees.otp.common.hibernate4.Finder;import ruyees.otp.common.page.Pagination;import ruyees.otp.model.template.Constant;import ruyees.otp.service.base.ContentMovieService;import ruyees.otp.service.base.ExamCategoryService;import ruyees.otp.service.base.OperPackageService;/** *  资源选择 * @author Zaric * @date  2013-6-1 上午12:38:13 */@Controllerpublic class SelectResourceController {	@Autowired	private ContentMovieService contMovieService;	@Autowired	private ExamCategoryService examCategoryService;	@Autowired	private OperPackageService operPackageService;	// get video page	@RequestMapping(value = "/common/{method}")	public String video(Model model, String pageNo, String itemindex,			String contentId, HttpServletRequest request,			@PathVariable("method") String method) {		Integer pn = 1;		if (StringUtils.isNotBlank(pageNo)) {			pn = Integer.parseInt(pageNo);		}		String fdName = request.getParameter("fdName");		Finder finder = Finder.create("from ContentMovie c");		//finder.append(" where c.attMain.fileType=:fileType");		finder.append(" where c.fileType=:fileType");		if ("video".equals(method)) {			finder.setParam("fileType", Constant.CONTNETMOVIE_TWO);			model.addAttribute("method", "video");		} else {			finder.setParam("fileType", Constant.CONTENTMOVIE_ONE);			model.addAttribute("method", "content");		}		if (StringUtils.isNotBlank(fdName)) {			finder.append("and c.fdName like :fdName");			finder.setParam("fdName", '%'+fdName + '%');			model.addAttribute("fdName", fdName);		}		finder.append("order by c.fdName ASC");		model.addAttribute("itemindex", itemindex);		model.addAttribute("contentId", contentId);		Pagination page = contMovieService.getPage(finder, pn, 6);		model.addAttribute("page", page);		if ("video".equals(method)) {			return "/common/video";		} else {			return "/common/content";		}	}	// get exam page	@RequestMapping(value = "/common/exam", method = RequestMethod.GET)	public String exam(Model model, String pageNo, String itemindex,			String contentId, HttpServletRequest request) {		Integer pn = 1;		if (StringUtils.isNotBlank(pageNo)) {			pn = Integer.parseInt(pageNo);		}		String fdName = request.getParameter("fdName");		Finder finder = Finder.create("from ExamCategory e");		if (StringUtils.isNotBlank(fdName)) {			finder.append("where e.fdName like :fdName");			finder.setParam("fdName", '%'+fdName + '%');			model.addAttribute("fdName", fdName);		}        finder.append("order by e.fdName ASC");		model.addAttribute("itemindex", itemindex);		model.addAttribute("contentId", contentId);		model.addAttribute("method", "exam");		Pagination page = examCategoryService.getPage(finder, pn, 6);		model.addAttribute("page", page);		return "/common/exam";	}	// get exam page	@RequestMapping(value = "/common/operation", method = RequestMethod.GET)	public String operation(Model model, String pageNo, String itemindex,			String contentId, HttpServletRequest request) {		Integer pn = 1;		if (StringUtils.isNotBlank(pageNo)) {			pn = Integer.parseInt(pageNo);		}		String fdName = request.getParameter("fdName");		model.addAttribute("fdName", fdName);		model.addAttribute("itemindex", itemindex);		model.addAttribute("contentId", contentId);		model.addAttribute("cid", request.getParameter("cid"));		model.addAttribute("method", "operation");		Map<String, Object> map = new HashMap<String, Object>();		map.put("fdValue", 100);		if (StringUtils.isNotBlank(fdName))			map.put("fdName", '%'+fdName + '%');		Pagination page = operPackageService.getPageByDynamic(map, pn);		model.addAttribute("page", page);		return "/common/operation";	}}