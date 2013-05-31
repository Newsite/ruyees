package cn.xdf.me.otp.service.base;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import cn.xdf.me.otp.action.view.model.VReportFactor;
import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Finder.SearchType;
import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.common.utils.SqlUtils;
import cn.xdf.me.otp.common.utils.TimeUtils;
import cn.xdf.me.otp.model.flow.BamProject;
import cn.xdf.me.otp.model.flow.BamProjectMember;
import cn.xdf.me.otp.model.sys.RoleEnum;
import cn.xdf.me.otp.model.sys.SysOrgElement;
import cn.xdf.me.otp.model.sys.SysOrgPerson;
import cn.xdf.me.otp.service.AccountService;
import cn.xdf.me.otp.service.SimpleService;
import cn.xdf.me.otp.service.SysOrgElementService;
import cn.xdf.me.otp.service.UserDictionService;
import cn.xdf.me.otp.service.UserRoleService;
import cn.xdf.me.otp.service.flow.BamProjectService;
import cn.xdf.me.otp.utils.ShiroUtils;

@Service
@Transactional(readOnly = true)
public class ReportFormsService extends SimpleService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private SysOrgElementService sysOrgElementService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private BamProjectService bamProjectService;

	@Autowired
	private UserDictionService userDictionService;

	/**
	 * 指导教师授课情况
	 * 
	 * @param factor
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findTeaching(VReportFactor factor) {
		List<Map> list = null;

		Map<String, Object> map = getQueryParams(factor);
		if (!ShiroUtils.isAdmin() && ShiroUtils.hasRole(RoleEnum.group)) {
			map.put("proids", SqlUtils.parseSqlRoundIn(userDictionService
					.getDictionIdByUser(ShiroUtils.getUser().getId())));
		}
		/*
		 * for (Map.Entry<String, Object> entry : map.entrySet()) {
		 * System.out.println(entry.getKey() + ":" + entry.getValue() + "\t"); }
		 */
		list = getBaseDao().findByNamedQuery("report-teaching", map, Map.class);

		return list;
	}

	/**
	 * 获取转换查询参数
	 * 
	 * @param factor
	 * @return
	 */
	public Map<String, Object> getQueryParams(VReportFactor factor) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("STARTTIME", factor.getStartDate());
		map.put("ENDTIME", factor.getEndDate());
		if (StringUtils.isNotEmpty(factor.getGuidIds())) {
			map.put("GUIDID", getTeachersParams(factor.getGuidIds()));
		} else if (StringUtils.isNotEmpty(factor.getSchIds())) {
			String orgs = getOrgsForQueryParam(factor.getSchIds());
			if (StringUtils.isNotEmpty(orgs)) {
				map.put("DEPATID", orgs);
			}
		}

		return map;
	}

	/**
	 * 获取机构下所有部门，返回用户sql查询的参数
	 * 
	 * @param orgId
	 * @return
	 */
	public String getOrgsForQueryParam(String orgIds) {
		StringBuilder sb = new StringBuilder();

		String orgIdsStr = getChildOrgIds(orgIds);
		String[] ids = orgIdsStr.split(",");

		if (ids != null && ids.length > 0) {
			int size = ids.length;
			int s = 1;
			sb.append("(");
			for (int i = 0; i < size; i++) {
				sb.append("'");
				sb.append(ids[i]);
				sb.append("'");
				if ((i + 2) / 1000 == s) {
					sb.append(") OR SOP.DEPATID IN (");
					s++;
				} else if (i < (size - 1)) {
					sb.append(",");
				}
			}
			sb.append(")");
		}

		return sb.toString();
	}

	/**
	 * 获取机构的下属机构
	 * 
	 * @param orgIds
	 * @return
	 */
	public String getChildOrgIds(String orgIds) {
		StringBuilder sb = new StringBuilder();

		String[] ids = orgIds.split(",");

		if (ids != null && ids.length > 0) {
			int size = ids.length;
			for (int i = 0; i < size; i++) {
				sb.append(getChildOrgIdsById(ids[i]));
				if (i < (size - 1)) {
					sb.append(",");
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 根据一个组织机构ID获取所有下属机构的ID
	 * 
	 * @param orgId
	 * @return
	 */
	public String getChildOrgIdsById(String orgId) {
		StringBuilder sb = new StringBuilder();

		List<SysOrgElement> sysOrgElementList = sysOrgElementService
				.findAllChildrenById(orgId);
		int size = sysOrgElementList.size();
		if (sysOrgElementList != null && sysOrgElementList.size() > 0) {
			for (int i = 0; i < size; i++) {
				SysOrgElement sysOrgElement = sysOrgElementList.get(i);
				sb.append(sysOrgElement.getFdId());
				if (i < (size - 1)) {
					sb.append(",");
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 获取机构下所有教师，返回用户sql查询的参数
	 * 
	 * @param orgId
	 * @return
	 */
	public String getTeachersForQueryParam(String orgId) {
		StringBuilder sb = new StringBuilder();

		List<SysOrgPerson> sysOrgPersonList = accountService
				.findUserByLinkLoginAndRealNameAndOrg("", orgId);
		if (sysOrgPersonList != null && sysOrgPersonList.size() > 0) {
			int size = sysOrgPersonList.size();
			int s = 1;
			sb.append("(");
			for (int i = 0; i < size; i++) {
				SysOrgPerson sysOrgPerson = sysOrgPersonList.get(i);

				sb.append("'");
				sb.append(sysOrgPerson.getFdId());
				sb.append("'");
				if ((i + 2) / 1000 == s) {
					sb.append(") OR BPM.GUIDID IN (");
					s++;
				} else if (i < (size - 1)) {
					sb.append(",");
				}
			}
			sb.append(")");
		}

		return sb.toString();
	}

	/**
	 * 将获取到的教师ID转换成查询参数
	 * 
	 * @param teacherIds
	 * @return
	 */
	public String getTeachersParams(String teacherIds) {
		StringBuilder sb = new StringBuilder();

		String[] ids = teacherIds.split(",");

		if (ids != null && ids.length > 0) {
			int size = ids.length;
			sb.append("(");
			for (int i = 0; i < size; i++) {
				sb.append("'");
				sb.append(ids[i]);
				sb.append("'");
				if (i < (size - 1)) {
					sb.append(",");
				}
			}
			sb.append(")");
		}

		return sb.toString();
	}

	/**
	 * 备课动态
	 * 
	 * @param factor
	 * @return
	 */
	/*
	 * public List<?> findPrepare(VReportFactor factor) { List<?> list = new
	 * ArrayList();
	 * 
	 * return list; }
	 *//**
	 * 新教师闯关情况
	 * 
	 * @param factor
	 * @return
	 */
	/*
	 * public List<?> findNewTeacher(VReportFactor factor) { List<?> list = new
	 * ArrayList();
	 * 
	 * return list; }
	 */

	public Finder listFinder(String schIds, String guidIds, String startDate,
			String endDate) throws Exception {

		List<String> schidList = new ArrayList<String>();
		if (schIds != null) {
			String[] arrSchids = schIds.split(",");
			for (String schid : arrSchids) {
				if (StringUtils.isNotBlank(schid)) {
					schidList.add(schid);
				}
			}
		}

		List<String> guididList = new ArrayList<String>();
		if (guidIds != null) {
			String[] arrGuidids = guidIds.split(",");
			for (String guid : arrGuidids) {
				if (StringUtils.isNotBlank(guid)) {
					guididList.add(guid);
				}
			}
		}

		Timestamp beginTime = null;
		Timestamp endTime = null;
		if (StringUtils.isNotBlank(startDate)) {
			String begin = startDate + " 00:00:00";
			beginTime = Timestamp.valueOf(begin);
		} else {
			startDate = TimeUtils.covertDateStr(
					DateUtils.addDays(new Date(), -20), "yyyy-MM-dd");
			String begin = startDate + " 00:00:00";
			beginTime = Timestamp.valueOf(begin);
		}
		if (StringUtils.isNotBlank(endDate)) {
			String end = endDate + " 23:59:59";
			endTime = Timestamp.valueOf(end);
		} else {
			endDate = TimeUtils.covertDateStr(DateUtils.addDays(new Date(), 1),
					"yyyy-MM-dd");
			String end = endDate + " 23:59:59";
			endTime = Timestamp.valueOf(end);
		}

		Finder finder = Finder
				.create("from BamProjectMember b left join fetch b.project project where project.status=:status");
		finder.setParam("status", BamProject.IS_OPEN);

		if (!ShiroUtils.isAdmin() && ShiroUtils.hasRole(RoleEnum.group)) {
			finder.search("project.template.program.fdId", userDictionService
					.getDictionIdByUser(ShiroUtils.getUser().id), SearchType.IN);
		}
		if (schidList.size() > 0) {
			finder.search("project.schId", schidList, SearchType.IN);
		}

		if (StringUtils.isNotBlank(startDate)) {
			finder.search("project.createTime", beginTime, SearchType.GE);
		}
		if (StringUtils.isNotBlank(endDate)) {
			finder.search("project.createTime", endTime, SearchType.LE);
		}

		if (guididList.size() > 0) {
			finder.search("b.guidId", guididList, SearchType.IN);
		}

		finder.append("order by project.name ASC");

		return finder;
	}

	/**
	 * 集团根据筛选条件来选择新教师
	 * 
	 * @param factor
	 * @return
	 */
	public Pagination getConditionPage(Model model, String pageNo,
			HttpServletRequest request) throws Exception {

		// 处理数据
		String orgNames = request.getParameter("orgNames");
		String guidNames = request.getParameter("guidNames");
		String schIds = request.getParameter("schIds");
		String guidIds = request.getParameter("guidIds");
		String startDate = request.getParameter("startDate"); // yyyy-mm-dd
		// hh:mm:ss
		String endDate = request.getParameter("endDate");

		Finder finder = listFinder(schIds, guidIds, startDate, endDate);

		Pagination page = getPage(finder, NumberUtils.createInteger(pageNo));
		for (Object obj : page.getList()) {
			BamProjectMember member = (BamProjectMember) obj;
			member.setNewTeach(accountService.findById(member.getNewteachId()));
			member.setGuid(accountService.findById(member.getGuidId()));
		}
		model.addAttribute("schIds", schIds);
		model.addAttribute("orgNames", orgNames);
		model.addAttribute("guidIds", guidIds);
		model.addAttribute("guidNames", guidNames);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		return page;
	}

	@SuppressWarnings("unchecked")
	public List<BamProjectMember> getConditionList(HttpServletRequest request)
			throws Exception {
		String schIds = request.getParameter("schIds");
		String guidIds = request.getParameter("guidIds");
		String startDate = request.getParameter("startDate"); // yyyy-mm-dd
		// hh:mm:ss
		String endDate = request.getParameter("endDate");

		Finder finder = listFinder(schIds, guidIds, startDate, endDate);
		List<BamProjectMember> lists = find(finder);
		for (Object obj : lists) {
			BamProjectMember member = (BamProjectMember) obj;
			member.setNewTeach(accountService.findById(member.getNewteachId()));
			member.setGuid(accountService.findById(member.getGuidId()));
		}
		return lists;
	}

	private Finder listFinder(Model model, HttpServletRequest request,
			String orgId) {
		String guidNames = request.getParameter("guidNames");
		model.addAttribute("guidNames", guidNames);
		String guidIds = request.getParameter("guidIds");
		model.addAttribute("guidIds", guidIds);
		List<String> guididList = new ArrayList<String>();
		if (guidIds != null) {
			String[] arrGuidids = guidIds.split(",");
			for (String guid : arrGuidids) {
				if (StringUtils.isNotBlank(guid)) {
					guididList.add(guid);
				}
			}
		}
		String startDate = request.getParameter("startDate"); // yyyy-mm-dd
																// hh:mm:ss
		String endDate = request.getParameter("endDate");
		if (StringUtils.isBlank(startDate)) {
			model.addAttribute("startDate", TimeUtils.covertDateStr(
					DateUtils.addDays(new Date(), -20), "yyyy-MM-dd"));
		} else {
			model.addAttribute("startDate", startDate);
		}
		model.addAttribute("endDate", endDate);
		Timestamp beginTime = null;
		Timestamp endTime = null;
		if (StringUtils.isNotBlank(startDate)) {
			String begin = startDate + " 00:00:00";
			beginTime = Timestamp.valueOf(begin);
		}
		if (StringUtils.isNotBlank(endDate)) {
			String end = endDate + " 00:00:00";
			endTime = Timestamp.valueOf(end);
		}

		Finder finder = Finder
				.create("from BamProjectMember b left join fetch b.project project where project.status=:status");
		finder.setParam("status", BamProject.IS_OPEN);
		if (StringUtils.isNotBlank(orgId)) {
			finder.search("project.schId", orgId, SearchType.EQ);
		}

		if (StringUtils.isNotBlank(startDate)) {
			finder.search("project.createTime", beginTime, SearchType.GE);
		}
		if (StringUtils.isNotBlank(endDate)) {
			finder.search("project.createTime", endTime, SearchType.LE);
		}

		if (guididList.size() > 0) {
			finder.search("b.guidId", guididList, SearchType.IN);
		}
		finder.append("order by project.name ASC");
		return finder;
	}

	/**
	 * 学校根据筛选条件来选择新教师
	 * 
	 * @param factor
	 * @return
	 */
	public Pagination getCampasConditionPage(Model model, String pageNo,
			HttpServletRequest request, String orgId) throws Exception {
		// 处理数据
		Finder finder = listFinder(model, request, orgId);
		Pagination page = getPage(finder, NumberUtils.createInteger(pageNo));
		for (Object obj : page.getList()) {
			BamProjectMember member = (BamProjectMember) obj;
			member.setNewTeach(accountService.findById(member.getNewteachId()));
			member.setGuid(accountService.findById(member.getGuidId()));
		}

		return page;
	}

	@SuppressWarnings("unchecked")
	public List<BamProjectMember> getCampasConditionList(Model model,
			HttpServletRequest request, String orgId) {
		Finder finder = listFinder(model, request, orgId);
		List<BamProjectMember> lists = find(finder);
		for (Object obj : lists) {
			BamProjectMember member = (BamProjectMember) obj;
			member.setNewTeach(accountService.findById(member.getNewteachId()));
			member.setGuid(accountService.findById(member.getGuidId()));
		}
		return lists;
	}

	/**
	 * 学校根据筛选条件来选择新教师
	 * 
	 * @param factor
	 * @return
	 */
	public Pagination getPageByCampus(String pageNo, HttpServletRequest request)
			throws Exception {
		// 处理数据
		String schIds = request.getParameter("schIds");
		List<String> schidList = new ArrayList<String>();
		if (schIds != null) {
			String[] arrSchids = schIds.split(",");
			for (String schid : arrSchids) {
				if (StringUtils.isNotBlank(schid)) {
					schidList.add(schid);
				}
			}
		}
		String guidIds = request.getParameter("guidIds");
		List<String> guididList = new ArrayList<String>();
		if (guidIds != null) {
			String[] arrGuidids = guidIds.split(",");
			for (String guid : arrGuidids) {
				if (StringUtils.isNotBlank(guid)) {
					guididList.add(guid);
				}
			}
		}
		String startDate = request.getParameter("startDate"); // yyyy-mm-dd
																// hh:mm:ss
		String endDate = request.getParameter("endDate");
		Timestamp beginTime = null;
		Timestamp endTime = null;
		if (StringUtils.isNotBlank(startDate)) {
			String begin = startDate + " 00:00:00";
			beginTime = Timestamp.valueOf(begin);
		}
		if (StringUtils.isNotBlank(endDate)) {
			String end = endDate + " 00:00:00";
			endTime = Timestamp.valueOf(end);
		}

		Finder finder = Finder
				.create("from BamProjectMember b left join fetch b.project project where project.status=:status");
		finder.setParam("status", BamProject.IS_OPEN);
		if (schidList.size() > 0) {
			finder.search("project.schId", schidList, SearchType.IN);
		}

		if (StringUtils.isNotBlank(startDate)) {
			finder.search("project.createTime", beginTime, SearchType.GE);
		}
		if (StringUtils.isNotBlank(endDate)) {
			finder.search("project.createTime", endTime, SearchType.LE);
		}

		if (guididList.size() > 0) {
			finder.search("b.guidId", guididList, SearchType.IN);
		}

		Pagination page = getPage(finder, NumberUtils.createInteger(pageNo));
		for (Object obj : page.getList()) {
			BamProjectMember member = (BamProjectMember) obj;
			member.setNewTeach(accountService.findById(member.getNewteachId()));
			member.setGuid(accountService.findById(member.getGuidId()));
		}
		return page;
	}

}
