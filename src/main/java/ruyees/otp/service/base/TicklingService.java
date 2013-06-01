package ruyees.otp.service.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.model.flow.BamProjectPhase;
import ruyees.otp.model.flow.Grade;
import ruyees.otp.model.flow.GradeItem;
import ruyees.otp.model.flow.Tickling;
import ruyees.otp.service.AccountService;
import ruyees.otp.service.BaseService;

/**
 * 
 * 新教师给指导教师打分
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:28:50
 */
@Service
@Transactional(readOnly = true)
public class TicklingService extends BaseService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private GradeService gradeService;

	@Autowired
	private GradeItemService gradeItemService;

	/**
	 * 处理新老师给批课老师评价
	 * 
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = false)
	public String getTicklingList(HttpServletRequest request) {
		Boolean isSucc = false;
		List<Tickling> ticks = new ArrayList<Tickling>();
		String[] tickIds = request.getParameterValues("tickId");

		for (String tickId : tickIds) {
			// Tickling tick = get(Tickling.class, tickId);
			Tickling tick = new Tickling();
			tick.setFdId(tickId);

			String appraise = request.getParameter("appraise" + tickId);
			String value1 = request.getParameter("value1" + tickId);
			String value2 = request.getParameter("value2" + tickId);
			String value3 = request.getParameter("value3" + tickId);
			String value4 = request.getParameter("value4" + tickId);
			String value5 = request.getParameter("value5" + tickId);
			String totalvalue = request.getParameter("totalvalue" + tickId);

			if (StringUtils.isBlank(value1)) {
				value1 = "0";
			}
			if (StringUtils.isBlank(value2)) {
				value2 = "0";
			}
			if (StringUtils.isBlank(value3)) {
				value3 = "0";
			}
			if (StringUtils.isBlank(value4)) {
				value4 = "0";
			}
			if (StringUtils.isBlank(value5)) {
				value5 = "0";
			}
			if (StringUtils.isBlank(totalvalue)) {
				totalvalue = "0";
			}

			tick.setAppraise(appraise);
			tick.setValue1(Float.parseFloat(value1));
			tick.setValue2(Float.parseFloat(value2));
			tick.setValue3(Float.parseFloat(value3));
			tick.setValue4(Float.parseFloat(value4));
			tick.setValue5(Float.parseFloat(value5));
			tick.setTotalvalue(Float.parseFloat(totalvalue));

			GradeItem item = gradeItemService.get(tickId);
			tick.setGuidId(item.getAdvierId());
			tick.setNewTeachId(item.getUid());
			tick.setGrade(item.getGrade());
			/*
			 * Updater<Tickling> updater = new Updater<Tickling>(tick); Tickling
			 * tickx = updateByUpdater(updater);
			 */
			Tickling tickx = save(tick);
			ticks.add(tickx);
		}

		// 新教师给批课老师打完分之后更新步骤状态
		String gradeId = request.getParameter("gradeId");
		Grade g = new Grade();
		g.setFdId(gradeId);
		gradeService.updateGradeThrough(g);

		int length = tickIds.length;
		isSucc = length == ticks.size();
		return isSucc.toString();
	}

	/**
	 * 获取新教师的所有批课老师
	 * 
	 * @param content
	 * @param uId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<Tickling> getTicklingList(BamProjectPhase content, String uId) {
		Finder finder = Finder
				.create("select distinct t.grade,t.guidId from Tickling t left join t.grade grade left join grade.phase phase where phase.fdId=:phaseId and phase.newteachId=:newteachId");
		finder.setParam("phaseId", content.getFdId());
		finder.setParam("newteachId", uId);
		List ticks = find(finder);

		List<Tickling> tickList = new ArrayList<Tickling>();
		Iterator it = ticks.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			Tickling t = findByCriteria(Tickling.class,
					Value.eq("grade", obj[0]), Value.eq("guidId", obj[1])).get(
					0);
			t.setAdvier(accountService.findById(obj[1].toString()));
			tickList.add(t);
		}
		return tickList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<Tickling> getEntityClass() {
		return Tickling.class;
	}
}