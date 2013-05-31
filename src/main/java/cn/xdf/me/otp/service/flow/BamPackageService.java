package cn.xdf.me.otp.service.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.hibernate4.Updater.UpdateMode;
import cn.xdf.me.otp.model.flow.BamIndex;
import cn.xdf.me.otp.model.flow.BamOperation;
import cn.xdf.me.otp.model.flow.BamPackage;
import cn.xdf.me.otp.service.notify.NotifyService;

/**
 * 
 * 作业包业务数据
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:30:21
 */
@Service
@Transactional(readOnly = true)
public class BamPackageService extends BamBaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<BamPackage> getEntityClass() {
		return BamPackage.class;
	}

	@Autowired
	private BamProjectPhaseService bamProjectPhaseService;

	@Autowired
	private NotifyService notifyService;

	/**
	 * 设置作业包通过(若其驳回，其下所有作业都驳回)
	 * 
	 * @param bamCourseware
	 * @return
	 */
	@Transactional(readOnly = false)
	public void setThrough(BamPackage bamPackage) {
		Updater<BamPackage> updater = new Updater<BamPackage>(bamPackage);
		updater.setUpdateMode(UpdateMode.MIN);
		updater.include("through");
		updater.include("fdComment");
		bamPackage = updateByUpdater(updater);
		// 若其驳回，其下所有作业都驳回,并发消息给新教师
		if (!bamPackage.getThrough()) {
			List<BamOperation> operList = bamPackage.getBamOperation();
			for (BamOperation bamOp : operList) {
				bamOp.setThrough(false);
				Updater<BamOperation> updater2 = new Updater<BamOperation>(
						bamOp);
				updater2.setUpdateMode(UpdateMode.MIN);
				updater2.include("through");
				updateByUpdater(updater2);

				List<BamIndex> indexList = bamOp.getBamIndexs();
				for (BamIndex index : indexList) {
					index.setStatus(3);// 驳回
					Updater<BamIndex> updater3 = new Updater<BamIndex>(index);
					updater3.setUpdateMode(UpdateMode.MIN);
					updater3.include("status");
					updateByUpdater(updater3);
				}
			}
			// 发消息
			notifyService.guidToDoNewTeach(bamPackage, 1);
		}
	}

}
