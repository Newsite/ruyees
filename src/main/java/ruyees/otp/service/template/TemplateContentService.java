package ruyees.otp.service.template;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ruyees.otp.model.template.TemplateContent;
import ruyees.otp.service.BaseService;

/**
 * 
 * 流程步骤
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:39:14
 */
@Service
@Transactional(readOnly = true)
public class TemplateContentService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<TemplateContent> getEntityClass() {
		return TemplateContent.class;
	}

}
