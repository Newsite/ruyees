package ruyees.otp.service.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.model.base.AttMain;
import ruyees.otp.model.base.ContentMovie;
import ruyees.otp.service.BaseService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;

/**
 * 
 * 视频文档Service
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:27:18
 */
@Service
@Transactional(readOnly = true)
public class ContentMovieService extends BaseService {
	@Autowired
	private AttMainService attMainService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<ContentMovie> getEntityClass() {
		return ContentMovie.class;
	}

	@Transactional(readOnly = false)
	public ContentMovie saveContentMovie(ContentMovie contentMovie) {
		int fileType = contentMovie.getFileType();
		// 文档编辑删除
		if (fileType == 1) {
			List<AttMain> atts = attMainService.findByProperty(AttMain.class,
					"contentMovie.fdId", contentMovie.getFdId());
			if (CollectionUtils.isNotEmpty(atts)) {
				for (AttMain attMain : atts) {
					attMain.remove();
				}
			}
		}

		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;

		if (fileType == 1) {
			fileType = 3; // 图片类型为3
		}
		contentMovie.setFdCreatorId(uId);
		contentMovie.setFdCreateTime(new Date());

		List<AttMain> attMains = contentMovie.getAttMains();
		if (attMains != null) {
			for (AttMain attMain : attMains) {
				if (fileType == 2) {
					attMain.setFileName(contentMovie.getFdName());
					if (StringUtils.isBlank(attMain.getFileUrl())) {
						attMain.setFlag(0);
					}
					// attMain.setFlag(0);
					// attMain.setFileUrl(null);
				}
				// 多上传第一个路径后多了一个“，”
				String filePath = attMain.getFilePath();
				int index = filePath.indexOf(",");
				if (index >= 0) {
					filePath = filePath.substring(0, index);
				}
				attMain.setFileType(fileType);
				attMain.setStoreType(2); // 文档存储方式 1:数据库 2：磁盘
				attMain.setModelName(ContentMovie.MODEL_NAME);
				attMain.setFilePath(filePath);
				attMain.setContentMovie(contentMovie);// 保存外键
			}
		} else {
			contentMovie.setAttMains(new ArrayList<AttMain>());
		}
		return super.save(contentMovie);
	}

	/**
	 * 根据文档视频类型和名称查询数据是否重复
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public boolean hasValueByNameAndType(String type, String name) {
		List<ContentMovie> cms = findByCriteria(ContentMovie.class,
				Value.eq("fdName", name),
				Value.eq("fileType", NumberUtils.createInteger(type)));
		return (CollectionUtils.isNotEmpty(cms));
	}

	/**
	 * 根据文档视频主键类型名称查询数据是否重复
	 * 
	 * @param name
	 * @param type
	 * @return
	 */
	public boolean hasValueByIdAndNameAndType(String fdId, String type,
			String name) {
		List<ContentMovie> cms = findByCriteria(ContentMovie.class,
				Value.ne("fdId", fdId), Value.eq("fdName", name),
				Value.eq("fileType", NumberUtils.createInteger(type)));
		return (CollectionUtils.isNotEmpty(cms));
	}
}
