package ruyees.otp.service.base;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.model.base.AttMain;
import ruyees.otp.service.SimpleService;

/**
 * 
 * 附件存储接口
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:27:05
 */
@Service
@Transactional(readOnly = true)
public class AttMainService extends SimpleService {

	@Transactional(readOnly = false)
	public void save(AttMain attMain) {
		try {
			attMain = super.save(attMain);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
