package cn.xdf.me.otp.action.coach;import java.io.File;import java.io.IOException;import java.io.UnsupportedEncodingException;import java.util.ArrayList;import java.util.List;import javax.servlet.http.HttpServletRequest;import javax.servlet.http.HttpServletResponse;import org.apache.commons.lang3.math.NumberUtils;import org.apache.shiro.SecurityUtils;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Scope;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.ui.ModelMap;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestMethod;import org.springframework.web.servlet.mvc.support.RedirectAttributes;import cn.xdf.me.otp.common.hibernate4.Finder;import cn.xdf.me.otp.common.page.Pagination;import cn.xdf.me.otp.common.utils.Zipper;import cn.xdf.me.otp.common.utils.Zipper.FileEntry;import cn.xdf.me.otp.model.flow.BamCourseware;import cn.xdf.me.otp.model.flow.BamCoursewareItem;import cn.xdf.me.otp.model.sys.SysOrgPerson;import cn.xdf.me.otp.service.AccountService;import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;import cn.xdf.me.otp.service.flow.BamCoursewareService;/** * 指导教师审批课件 * @author Zaric * @date  2013-6-1 上午12:37:05 */@Controller@RequestMapping(value = "/coach/courseware")@Scope("request")public class BamCoursewareController {		private static final Logger log = LoggerFactory.getLogger(BamCoursewareController.class);	@Autowired	private BamCoursewareService bamCoursewareService;	@Autowired	private AccountService accountService;	@RequestMapping(value = "list/{index}")	public String list(Model model, String pageNo, HttpServletRequest request, @PathVariable("index") String index) {		model.addAttribute("active", index);		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();		String uId = user.id;		Finder finder = Finder.create("select b from BamCourseware b left join fetch b.phase phase left join fetch phase.stage stage where phase.guidId=:guidId and stage.fdIndex = :fdIndex and b.through in (:through)");		finder.setParam("fdIndex", Integer.parseInt(index));		finder.setParam("guidId", uId);		finder.setParamList("through", new Boolean[] { true, false }); // true:通过,false:驳回		finder.append("order by b.fdId");// b.through		Pagination page = bamCoursewareService.getPage(finder, NumberUtils.createInteger(pageNo));		for (Object obj : page.getList()) {			BamCourseware bamCourseware = (BamCourseware) obj;			SysOrgPerson person = accountService.findById(bamCourseware.getPhase().getNewteachId());			bamCourseware.getPhase().setNewTeach(person);		}		model.addAttribute("page", page);		return String.format("/base/courseware/%s/list", index);	}	@SuppressWarnings("unchecked")	@RequestMapping(value = "edit/{index}/{id}")	public String edit(Model model, @PathVariable("id") String coursewareId, @PathVariable("index") String index) {		Finder finder = Finder.create("select b from BamCoursewareItem b ");		finder.append("left join fetch b.bamCourseware c ");		finder.append("left join fetch c.phase phase ");		finder.append("left join fetch phase.stage stage ");		finder.append("where c.fdId=:fdId ");		finder.append("order by b.name asc");// order by b.status		finder.setParam("fdId", coursewareId);		List<BamCoursewareItem> bamCoursewareItemList = bamCoursewareService.find(finder);		for (BamCoursewareItem item : bamCoursewareItemList) {			SysOrgPerson person = accountService.findById(item.getBamCourseware().getPhase().getNewteachId());			item.getBamCourseware().getPhase().setNewTeach(person);		}		BamCourseware bamCourseware = bamCoursewareService.load(coursewareId);		SysOrgPerson person = accountService.findById(bamCourseware.getPhase().getNewteachId());		model.addAttribute("bamCourseware", bamCourseware);		model.addAttribute("person", person);		model.addAttribute("bamCoursewareItemList", bamCoursewareItemList);		return String.format("/base/courseware/%s/edit", index);	}	@SuppressWarnings("unchecked")	@RequestMapping(value = "/data/zipExp")	public String dataExp(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {		String coursewareId = request.getParameter("fdId");		Finder finder = Finder.create("select b from BamCoursewareItem b ");		finder.append("left join fetch b.bamCourseware c ");		finder.append("where c.fdId=:fdId ");		finder.setParam("fdId", coursewareId);		List<BamCoursewareItem> bamCoursewareItemList = bamCoursewareService.find(finder);		BamCourseware bamCourseware = bamCoursewareService.load(coursewareId);		SysOrgPerson person = accountService.findById(bamCourseware.getPhase().getNewteachId());		String agent = request.getHeader("USER-AGENT");		String temp="";		// 设置文件头，文件名称或编码格式		if (null != agent && -1 != agent.indexOf("MSIE")) {// IE			temp=java.net.URLEncoder.encode("确认课件", "UTF-8");		} else {			temp=new String("确认课件".getBytes("UTF-8"),"ISO8859-1");		}		String zipName = temp + "(" + person.getLoginName() + ")";		List<FileEntry> fileEntrys = new ArrayList<FileEntry>();		response.setContentType("application/x-download;charset=UTF-8");		response.addHeader("Content-disposition", "filename=" + zipName + ".zip");		for (BamCoursewareItem bamItem : bamCoursewareItemList) {			File file = new File(bamItem.getPath());			fileEntrys.add(new FileEntry(bamItem.getName(), "", file));		}		try {			// 模板一般都在windows下编辑，所以默认编码为GBK			Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");		} catch (IOException e) {			log.error("export db error!", e);		}		return null;	}	@RequestMapping(value = "view/{index}/{id}")	public String view(Model model, @PathVariable("id") String coursewareId, @PathVariable("index") String index) {		BamCourseware bamCourseware = bamCoursewareService.load(coursewareId);		SysOrgPerson person = accountService.findById(bamCourseware.getPhase().getNewteachId());		model.addAttribute("bean", bamCourseware);		model.addAttribute("person", person);		return String.format("/base/courseware/%s/view", index);	}	@RequestMapping(value = "save/{index}", method = RequestMethod.POST)	public String save(BamCourseware bamCourseware, RedirectAttributes redirectAttributes, HttpServletRequest request, @PathVariable("index") String index) {		String newTeachId = request.getParameter("newTeachId");		bamCoursewareService.setThrough(bamCourseware, newTeachId);		return String.format("redirect:/coach/courseware/edit/%s/%s", index, bamCourseware.getFdId());	}}