package cn.xdf.me.otp.service.template;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.model.template.TemplateItem;
import cn.xdf.me.otp.service.BaseService;

/**
 * 
 * 流程阶段
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:39:06
 */
@Service
@Transactional(readOnly = true)
public class TemplateItemService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<TemplateItem> getEntityClass() {
		return TemplateItem.class;
	}

}
