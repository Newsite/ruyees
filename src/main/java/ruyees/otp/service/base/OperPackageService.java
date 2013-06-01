package ruyees.otp.service.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import ruyees.otp.common.hibernate4.Value;
import ruyees.otp.common.page.Pagination;
import ruyees.otp.model.base.OperPackage;
import ruyees.otp.service.BaseService;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;

@Service
@Transactional(readOnly = true)
public class OperPackageService extends BaseService {

	public Pagination getPageByDynamic(Map<String, Object> map, Integer pageNo) {
		return getBaseDao().findByNamedPage("index-page", map, Map.class,
				pageNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<OperPackage> getEntityClass() {
		return OperPackage.class;
	}

	@Transactional(readOnly = false)
	public OperPackage saveOperPackage(OperPackage operPackage) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		operPackage.setFdCreatorId(uId);
		operPackage.setFdCreateTime(new Date());
		return super.save(operPackage);
	}

	public boolean hasPackageByName(String name) {
		List<OperPackage> lists = findByCriteria(OperPackage.class,
				Value.eq("fdName", name));
		return !(CollectionUtils.isEmpty(lists));
	}
}
