package cn.xdf.me.otp.service.base;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.model.base.AttMain;
import cn.xdf.me.otp.service.SimpleService;

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

	// @Autowired
	// private IAttMainWebService attMainWebService;

	@Transactional(readOnly = false)
	public void save(AttMain attMain) {
		try {
			attMain = super.save(attMain);

			/*
			 * if (attMain.getFileType() == 2 &&
			 * StringUtils.isNotBlank(attMain.getFilePath())) {
			 * com.landray.kmss.xdf.webservice.AttMain webattMain = new
			 * com.landray.kmss.xdf.webservice.AttMain();
			 * webattMain.setModelId(attMain.getFdId());
			 * webattMain.setModelName(attMain.getFileName());
			 * webattMain.getFilePaths().add(attMain.getFilePath()); ReturnMsg
			 * returnMsg = AttMainUtils.addAttMain(webattMain); if
			 * (!"000".equals(returnMsg.getCode())) { throw new
			 * RuntimeException("upload file error,error code is:" +
			 * returnMsg.getCode()); } }
			 */

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
