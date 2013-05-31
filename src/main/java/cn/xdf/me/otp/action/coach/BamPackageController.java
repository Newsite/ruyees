package cn.xdf.me.otp.action.coach;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.common.utils.Zipper;
import cn.xdf.me.otp.common.utils.Zipper.FileEntry;
import cn.xdf.me.otp.model.flow.BamIndex;
import cn.xdf.me.otp.model.flow.BamPackage;
import cn.xdf.me.otp.model.sys.SysOrgPerson;
import cn.xdf.me.otp.service.AccountService;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.flow.BamPackageService;

/**
 * 指导教师审批作业包
 * @author Zaric
 * @date  2013-6-1 上午12:37:42
 */
@Controller
@RequestMapping(value = "/coach/package")
@Scope("request")
public class BamPackageController {

	private static final Logger log = LoggerFactory.getLogger(BamPackageController.class);

	@Autowired
	private BamPackageService bamPackageService;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "list")
	public String list(Model model, String pageNo, HttpServletRequest request) {
		model.addAttribute("active", "package");
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		Finder finder = Finder.create("select b from BamPackage b left join fetch b.phase phase where phase.guidId=:guidId and b.through in (:through)");

		finder.setParam("guidId", uId);
		finder.setParamList("through", new Boolean[] { true, false }); // true:通过,false:驳回
		finder.append("order by b.through,b.fdId");
		Pagination page = bamPackageService.getPage(finder, NumberUtils.createInteger(pageNo));

		for (Object obj : page.getList()) {
			BamPackage bamPackage = (BamPackage) obj;
			SysOrgPerson person = accountService.findById(bamPackage.getPhase().getNewteachId());
			bamPackage.getPhase().setNewTeach(person);
		}
		model.addAttribute("page", page);
		return "/base/package/list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "edit/{id}")
	public String edit(Model model, @PathVariable("id") String packageId) {
		Finder finder = Finder.create("select b from BamIndex b ");
		finder.append("left join fetch b.bamOperation o ");
		finder.append("left join fetch o.bamPackage pack ");
		finder.append("left join fetch pack.phase phase ");
		finder.append("where pack.fdId=:fdId ");// and b.status in (:status)
		finder.append("order by o.fdOrder,b.fdOrder,b.fdIndexName");// order by
																	// o.fdOrder,b.fdOrder,b.fdIndexName,
		finder.setParam("fdId", packageId);

		double totalValue=0;
		List<BamIndex> bamIndexList = bamPackageService.find(finder);
		for (BamIndex index : bamIndexList) {
			SysOrgPerson person = accountService.findById(index.getBamOperation().getBamPackage().getPhase().getNewteachId());
			index.getBamOperation().getBamPackage().getPhase().setNewTeach(person);
			totalValue+=index.getFdToValue()==null?0:index.getFdToValue();
		}

		BamPackage bamPackage = bamPackageService.load(packageId);
		SysOrgPerson person = accountService.findById(bamPackage.getPhase().getNewteachId());

		DecimalFormat df = new DecimalFormat("#.##");
		String tValue=df.format(totalValue);
		
		model.addAttribute("tValue", tValue);
		model.addAttribute("person", person);
		model.addAttribute("bamPackage", bamPackage);
		model.addAttribute("bamIndexList", bamIndexList);

		return "/base/package/edit";
	}

	@RequestMapping(value = "view/{id}")
	public String view(Model model, @PathVariable("id") String packageId) {
		model.addAttribute("bean", bamPackageService.load(packageId));
		return "/base/package/view";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(BamPackage bamPackage, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		bamPackageService.setThrough(bamPackage);
		return String.format("redirect:/coach/package/edit/%s", bamPackage.getFdId());
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/data/o_export")
	public String exportSubmit(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String packageId = request.getParameter("fdId");

		Finder finder = Finder.create("select b from BamIndex b ");
		finder.append("left join fetch b.bamOperation o ");
		finder.append("left join fetch o.bamPackage pack ");
		finder.append("left join fetch pack.phase phase ");
		finder.append("where pack.fdId=:fdId ");// and b.status in (:status)
		finder.append("order by o.fdOrder,b.fdOrder,b.fdIndexName");
																	
		finder.setParam("fdId", packageId);

		List<BamIndex> bamIndexList = bamPackageService.find(finder);

		BamPackage bamPackage = bamPackageService.load(packageId);
		SysOrgPerson person = accountService.findById(bamPackage.getPhase().getNewteachId());
		
		String agent = request.getHeader("USER-AGENT");
		String temp="";
		// 设置文件头，文件名称或编码格式
		if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
			temp=java.net.URLEncoder.encode(bamPackage.getFdName(), "UTF-8");
		} else {
			temp=new String(bamPackage.getFdName().getBytes("UTF-8"),"ISO8859-1");
		}
		//String backName = bamPackage.getFdName() + "(" + person.getLoginName() + ")";
		String backName =  temp+ "(" + person.getLoginName() + ")";

		List<FileEntry> fileEntrys = new ArrayList<FileEntry>();
		response.setContentType("application/x-download;charset=UTF-8");
		response.addHeader("Content-disposition", "filename=" + backName + ".zip");

		for (BamIndex index : bamIndexList) {
			File file = new File(index.getBamAttMain().getFilePath());
			fileEntrys.add(new FileEntry(index.getFdIndexName(), "", file));
		}
		try {
			// 模板一般都在windows下编辑，所以默认编码为GBK
			Zipper.zip(response.getOutputStream(), fileEntrys, "GBK");
		} catch (IOException e) {
			log.error("export db error!", e);
		}
		return null;
	}

}