package cn.xdf.me.otp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.upload.FileModel;
import cn.xdf.me.otp.common.upload.FileRepository;
import cn.xdf.me.otp.model.sys.SysOrgElement;
import cn.xdf.me.otp.model.sys.SysOrgPerson;
import cn.xdf.me.otp.model.sys.SysOrgPersonTemp;
import cn.xdf.me.otp.service.AccountService;
import cn.xdf.me.otp.service.RegisterService;
import cn.xdf.me.otp.service.SysOrgElementService;
import cn.xdf.me.otp.utils.ShiroUtils;

/**
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:31:31
 */
@Controller
@RequestMapping(value = "/register2")
public class RegisterController2 {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SysOrgElementService sysOrgElementService;

	@RequestMapping(value = "add")
	public String registerForm(Model model) {
		List<SysOrgElement> elements = sysOrgElementService.findAllOrgByType(1);
		model.addAttribute("elements", elements);
		return "/base/register2/add";
	}

	/*
	 * 新教师保存
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(SysOrgPersonTemp sysOrgPersonTemp,
			HttpServletRequest request) {
		registerService.register2(sysOrgPersonTemp);
		if (ShiroUtils.getUser() == null) {
			return "redirect:/login";
		}
		return "redirect:/register/list";
	}

	/*
	 * 管理员编辑新教师
	 */
	@RequestMapping(value = "edit/{fdId}")
	public String edit(Model model, @PathVariable("fdId") String fdId) {
		SysOrgPersonTemp sysOrgPersonTemp = registerService.get(
				SysOrgPersonTemp.class, fdId);

		model.addAttribute("bean", sysOrgPersonTemp);
		List<SysOrgElement> elements = sysOrgElementService.findAllOrgByType(1);
		model.addAttribute("elements", elements);
		return "/base/register2/edit";
	}

	@RequestMapping(value = "edit")
	public String edit(Model model) {
		String uid = ShiroUtils.getUser().getId();
		SysOrgPerson person = accountService.load(uid);
		List<SysOrgPersonTemp> sysOrgPersonTemps = registerService
				.findByProperty(SysOrgPersonTemp.class, "fdIdentityCard",
						person.getLoginName());
		if (sysOrgPersonTemps != null && !sysOrgPersonTemps.isEmpty()) {
			SysOrgPersonTemp sysOrgPersonTemp = sysOrgPersonTemps.get(0);
			sysOrgPersonTemp.setFdIcoUrl(person.getFdPhotoUrl());
			model.addAttribute("bean", sysOrgPersonTemp);
		}

		List<SysOrgElement> elements = sysOrgElementService.findAllOrgByType(1);
		model.addAttribute("elements", elements);
		return "/base/register2/edit";
	}

	/*
	 * 邮件注册入口
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "register/{randomCode}")
	public String register(Model model,
			@PathVariable("randomCode") String randomCode) {
		Finder finder = Finder
				.create("from SysOrgPersonTemp sopt where sopt.isRegistered = '0' and sopt.randomCode = :randomCode");
		finder.setParam("randomCode", randomCode);
		List<SysOrgPersonTemp> sysOrgPersonTempList = registerService
				.find(finder);

		SysOrgPersonTemp sysOrgPersonTemp = new SysOrgPersonTemp();
		if (sysOrgPersonTempList != null && sysOrgPersonTempList.size() > 0) {
			sysOrgPersonTemp = sysOrgPersonTempList.get(0);
		} else {
			return "/login";
		}

		model.addAttribute("sopt", sysOrgPersonTemp);

		return "/base/register/register";
	}

	/**
	 * 上传文件
	 */
	@RequestMapping("o_upload")
	@ResponseBody
	public FileModel execute(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {

		String folder = request.getParameter("folder");
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());

		commonsMultipartResolver.setDefaultEncoding("utf-8");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("Filedata");
		FileModel fileModel = fileRepository.storeByExt(file, folder);
		return fileModel;
	}

	private FileRepository fileRepository;

	@Autowired
	public void setFileRepository(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}
}
