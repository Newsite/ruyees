package cn.xdf.me.otp.dao.interceptor;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.springframework.beans.factory.annotation.Autowired;

import cn.xdf.me.otp.model.BamProcess;
import cn.xdf.me.otp.service.flow.BamProjectPhaseService;

/**
 * 流程监听器
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:05:21
 */
public class ProceeListener implements PostUpdateEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BamProjectPhaseService bamProjectPhaseService;

	public void onPostUpdate(PostUpdateEvent event) {
		if (event.getEntity() instanceof BamProcess) {
			BamProcess process = (BamProcess) event.getEntity();
			if (!process.getThrough())
				return;
		}

	}

}
