package ruyees.otp.action.ajax;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jodd.servlet.URLDecoder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ruyees.otp.action.view.model.VDept;
import ruyees.otp.service.SysOrgElementService;

/**
 * 获取学校
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:33:36
 */
@Controller
@RequestMapping(value = "/ajax/org")
@Scope("request")
public class OrgAjaxController {

	@Autowired
	private SysOrgElementService sysOrgElementService;

	/**
	 * 根据名称查询部门
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findByName")
	@ResponseBody
	public List<VDept> findByName(HttpServletRequest request) {
		String key = request.getParameter("q");
		List<VDept> depts = new ArrayList<VDept>();
		if (StringUtils.isBlank(key))
			return depts;
		key = URLDecoder.decode(key, "utf-8");
		// findOrgByEelementName
		depts = sysOrgElementService.findOrgByEelementName(key, null);
		return depts;
	}

	/**
	 * 查询机构信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findByOrgName")
	@ResponseBody
	public List<VDept> findByOrgName(HttpServletRequest request) {
		String key = request.getParameter("q");
		List<VDept> depts = new ArrayList<VDept>();
		if (StringUtils.isBlank(key))
			return depts;
		key = URLDecoder.decode(key, "utf-8");
		depts = sysOrgElementService.findOrgByEelementName(key, 1);
		return depts;
	}

	/**
	 * 查询多机构信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findByOrgNames")
	@ResponseBody
	public List<VDept> findByOrgNames(HttpServletRequest request) {
		List<VDept> depts = new ArrayList<VDept>();
		String keys = request.getParameter("q");
		if (StringUtils.isBlank(keys))
			return depts;
		String[] arrs = keys.split(",");
		for (String key : arrs) {
			key = URLDecoder.decode(key, "utf-8").replaceAll(" ", "");
			List<VDept> de = sysOrgElementService.findOrgByEelementName(key, 1);
			depts.addAll(de);
		}
		return depts;
	}

	/**
	 * 查询某一机构下所匹配部门
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findByOrgIdAndDeptName")
	@ResponseBody
	public List<VDept> findByOrgIdAndDeptName(HttpServletRequest request) {
		List<VDept> depts = new ArrayList<VDept>();
		String key = request.getParameter("q");
		String orgId = request.getParameter("orgId");
		if (StringUtils.isBlank(key))
			return depts;
		key = URLDecoder.decode(key, "utf-8");
		if (StringUtils.isBlank(orgId)) {
			depts = sysOrgElementService.findOrgByEelementName(key, null);
		} else {
			List<VDept> de = sysOrgElementService.findDeptByOrgIdAndName(key,
					orgId);
			depts.addAll(de);
		}
		return depts;
	}

}
