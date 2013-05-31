package cn.xdf.me.otp.service.base;

import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.xdf.me.otp.common.hibernate4.Value;
import cn.xdf.me.otp.model.base.ExamCategory;
import cn.xdf.me.otp.service.BaseService;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;

@Service
@Transactional(readOnly = true)
public class ExamCategoryService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<ExamCategory> getEntityClass() {
		return ExamCategory.class;
	}

	@Transactional(readOnly = false)
	public ExamCategory saveExamCategory(ExamCategory examCategory) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		examCategory.setFdCreatorId(uId);
		examCategory.setFdCreateTime(new Date());
		return save(examCategory);
	}

	public boolean hasValueByName(String name) {
		List<ExamCategory> list = findByCriteria(ExamCategory.class,
				Value.eq("fdName", name));
		return !(CollectionUtils.isEmpty(list));
	}
}
