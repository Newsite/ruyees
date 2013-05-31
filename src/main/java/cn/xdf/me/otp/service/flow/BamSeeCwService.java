package cn.xdf.me.otp.service.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.action.view.model.VContent;
import cn.xdf.me.otp.common.hibernate4.Updater;
import cn.xdf.me.otp.common.utils.ComUtils;
import cn.xdf.me.otp.model.flow.BamAttMain;
import cn.xdf.me.otp.model.flow.BamSeeCw;

@Service
@Transactional(readOnly = true)
public class BamSeeCwService extends BamBaseService {

	@Autowired
	private BamProjectPhaseService bamProjectPhaseService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<BamSeeCw> getEntityClass() {
		return BamSeeCw.class;
	}

	/**
	 * 设置视屏观看状态
	 * 
	 * @param key
	 * 
	 * @return -1：设置失败；0：设置已观看成功；1：设置已观看成功，并且当前步骤已经通过
	 */
	@Transactional(readOnly = false)
	public List<VContent> setContentMovieThrough(String key, String nextId) {
		List<VContent> docs = new ArrayList<VContent>();
		BamSeeCw bamseeCw = new BamSeeCw();
		bamseeCw.setFdId(key);
		bamseeCw.setThrough(true);
		bamseeCw.setFdTime(ComUtils.now());
		Updater<BamSeeCw> updater = new Updater<BamSeeCw>(bamseeCw);
		bamseeCw = updateByUpdater(updater);

		String through = "-1";
		if (bamseeCw != null) {
			boolean phaseThrough = bamseeCw.getPhase().getThrough();
			through = phaseThrough ? "1" : "0";
		} else {
			through = "-1";
		}
		// 获取描述
		String nextRemark = "";
		if (StringUtils.isBlank(nextId)) {
			nextRemark = bamseeCw.getFdDescribe();
		} else {
			BamSeeCw nextSee = get(nextId);
			nextRemark = nextSee.getFdDescribe();
		}

		VContent doc = null;
		if (bamseeCw != null) {
			for (BamAttMain bamAtt : bamseeCw.getBamAttMains()) {
				doc = new VContent(through, bamAtt.getFdIndex(),
						bamAtt.getFilePath(), nextRemark);
				docs.add(doc);
			}
		}
		return docs;
	}

}
