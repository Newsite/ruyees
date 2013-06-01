package ruyees.otp.action.ajax;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ruyees.otp.action.view.model.VGradeItem;
import ruyees.otp.common.hibernate4.Updater;
import ruyees.otp.common.hibernate4.Updater.UpdateMode;
import ruyees.otp.model.flow.GradeItem;
import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.base.GradeItemService;
import ruyees.otp.service.base.TicklingService;

/**
 * 新教师给批课老师打分
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:32:56
 */
@Controller
@RequestMapping(value = "/ajax/grade")
@Scope("request")
public class GradeAjaxController {

	@Autowired
	private TicklingService ticklingService;

	@Autowired
	private GradeItemService gradeItemService;

	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "pushGrade", method = RequestMethod.POST)
	@ResponseBody
	public String pushGrade(HttpServletRequest request) {
		return ticklingService.getTicklingList(request);

	}

	// addGrade

	@RequestMapping(value = "addGrade", method = RequestMethod.POST)
	@ResponseBody
	public String addGrade(HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String adverid = request.getParameter("teachers");
		String startDate = request.getParameter("startDate");
		String startTime = request.getParameter("startTime");
		String endDate = request.getParameter("endDate");
		String endTime = request.getParameter("endTime");
		String addr = request.getParameter("addr");
		String itemId = request.getParameter("itemId");
		String remark = request.getParameter("remark");
		GradeItem item = new GradeItem();
		item.setFdId(itemId);
		item.setAdvierId(adverid);
		item.setAddress(addr);
		item.setRemark(remark);
		try {
			String sdate = df.format(df.parse(startDate + " " + startTime
					+ ":00"));
			String edate = df.format(df.parse(endDate + " " + endTime + ":00"));
			item.setStartTime(Timestamp.valueOf(sdate));
			item.setEndTime(Timestamp.valueOf(edate));
		} catch (Exception ex) {
			ex.printStackTrace();
			return "false";
		}

		Updater<GradeItem> updater = new Updater<GradeItem>(item);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("advierId");
		updater.include("address");
		updater.include("remark");
		updater.include("startTime");
		updater.include("endTime");
		gradeItemService.updateByUpdater(updater);
		return "true";

	}

	// deleteGrade
	@RequestMapping(value = "deleteGrade", method = RequestMethod.POST)
	@ResponseBody
	public String deleteGrade(HttpServletRequest request) {
		String key = request.getParameter("key");
		gradeItemService.delete(GradeItem.class, key);
		return "true";
	}

	@RequestMapping(value = "loadGrade", method = RequestMethod.POST)
	@ResponseBody
	public VGradeItem loadGrade(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");

		String key = request.getParameter("key");
		GradeItem item = gradeItemService.get(key);
		SysOrgPerson newPerson = accountService.get(item.getUid());
		SysOrgPerson avderPerson = accountService.get(item.getAdvierId());
		item.setNewTeach(newPerson);
		item.setAdvier(avderPerson);
		VGradeItem vitem = new VGradeItem();
		vitem.setFdId(item.getFdId());
		try {
			vitem.setStartDate(sdf.format(item.getStartTime()));
			vitem.setStarTime(sdf2.format(item.getStartTime()));
			vitem.setEndDate(sdf.format(item.getEndTime()));
			vitem.setEndTime(sdf2.format(item.getEndTime()));
		} catch (Exception ex) {

		}
		vitem.setNewTeachName(newPerson.getRealName());
		vitem.setNewTeachId(newPerson.getFdId());
		vitem.setAdvierId(avderPerson.getFdId());
		vitem.setAdvierName(avderPerson.getRealName());
		vitem.setAddress(item.getAddress());
		vitem.setRemarke(item.getRemark());
		return vitem;
	}

}
