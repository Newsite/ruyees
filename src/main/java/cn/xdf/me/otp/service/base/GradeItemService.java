package cn.xdf.me.otp.service.base;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.hibernate4.Updater.UpdateMode;
import cn.xdf.me.otp.common.hibernate4.Value;
import cn.xdf.me.otp.model.flow.BamProjectPhase;
import cn.xdf.me.otp.model.flow.GradeItem;
import cn.xdf.me.otp.service.AccountService;
import cn.xdf.me.otp.service.BaseService;

/**
 * 
 * 批课安排分项
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:27:49
 */
@Service
@Transactional(readOnly = true)
public class GradeItemService extends BaseService {

	@Autowired
	private AccountService accountService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<GradeItem> getEntityClass() {
		return GradeItem.class;
	}

	@Transactional(readOnly = false)
	public GradeItem updateGradeItem(GradeItem gradeItem) {
		Updater<GradeItem> updater = new Updater<GradeItem>(gradeItem);
		updater.setUpdateMode(UpdateMode.MIDDLE);

		// 格式分数
		DecimalFormat df = new DecimalFormat("#.#");
		Float value1 = gradeItem.getValue1() != null ? gradeItem.getValue1()
				: 0;
		Float value2 = gradeItem.getValue2() != null ? gradeItem.getValue2()
				: 0;
		Float value3 = gradeItem.getValue3() != null ? gradeItem.getValue3()
				: 0;
		Float value4 = gradeItem.getValue4() != null ? gradeItem.getValue4()
				: 0;
		Float value5 = gradeItem.getValue5() != null ? gradeItem.getValue5()
				: 0;
		Float value6 = gradeItem.getValue6() != null ? gradeItem.getValue6()
				: 0;
		Float value7 = gradeItem.getValue7() != null ? gradeItem.getValue7()
				: 0;
		Float value8 = gradeItem.getValue8() != null ? gradeItem.getValue8()
				: 0;
		Float value = (value1 + value2 + value3 + value4 + value5 + value6
				+ value7 + value8) / 8;

		gradeItem.setValue1(Float.parseFloat(df.format(value1)));
		gradeItem.setValue2(Float.parseFloat(df.format(value2)));
		gradeItem.setValue3(Float.parseFloat(df.format(value3)));
		gradeItem.setValue4(Float.parseFloat(df.format(value4)));
		gradeItem.setValue5(Float.parseFloat(df.format(value5)));
		gradeItem.setValue6(Float.parseFloat(df.format(value6)));
		gradeItem.setValue7(Float.parseFloat(df.format(value7)));
		gradeItem.setValue8(Float.parseFloat(df.format(value8)));
		gradeItem.setValue(Float.parseFloat(df.format(value)));

		return updateByUpdater(updater);
	}

	/**
	 * 获取新教师的所有批课老师
	 * 
	 * @param content
	 * @param uId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public List<GradeItem> getGradeItemList(BamProjectPhase content, String uId) {
		Finder finder = Finder
				.create("select distinct g.grade,g.advierId from GradeItem g left join g.grade grade left join grade.phase phase where phase.fdId=:phaseId and phase.newteachId=:newteachId");
		finder.setParam("phaseId", content.getFdId());
		finder.setParam("newteachId", uId);
		List items = find(finder);

		List<GradeItem> itemList = new ArrayList<GradeItem>();
		Iterator it = items.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
			GradeItem g = findByCriteria(GradeItem.class,
					Value.eq("grade", obj[0]), Value.eq("advierId", obj[1]))
					.get(0);
			g.setAdvier(accountService.findById(obj[1].toString()));
			itemList.add(g);
		}
		return itemList;
	}

}
