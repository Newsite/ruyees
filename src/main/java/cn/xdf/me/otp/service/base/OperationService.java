package cn.xdf.me.otp.service.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.common.hibernate4.Finder;
import cn.xdf.me.otp.common.hibernate4.Finder.SearchType;
import cn.xdf.me.otp.model.base.AttMain;
import cn.xdf.me.otp.model.base.Index;
import cn.xdf.me.otp.model.base.OperPackage;
import cn.xdf.me.otp.model.base.Operation;
import cn.xdf.me.otp.service.BaseService;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;

/**
 * 
 * 作业对应步骤Service
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:28:24
 */
@Service
@Transactional(readOnly = true)
public class OperationService extends BaseService {
	@Autowired
	private OperPackageService operPackageService;

	@SuppressWarnings("unchecked")
	@Override
	public Class<Operation> getEntityClass() {
		return Operation.class;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Boolean isHas(Operation operation, String method, String fdPackageId) {
		Boolean isHas = false;
		if ("add".equals(method)) {
			String fdName = operation.getFdName();
			Finder finder = Finder
					.create("from Operation o where o.operPackage.fdId=:packageId");
			finder.setParam("packageId", fdPackageId);
			finder.search("o.fdName", fdName, SearchType.EQ);
			List<Operation> operations = find(finder);
			isHas = operations.size() > 0;
		}
		return isHas;
	}

	@Transactional(readOnly = false)
	public Operation saveOperation(Operation operation, String fdPackageId) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String uId = user.id;
		operation.setFdCreatorId(uId);
		operation.setFdCreateTime(new Date());
		// 维护作业包关联
		operation.setOperPackage((OperPackage) operPackageService
				.get(fdPackageId));

		if (operation.getIndexs() != null) {
			for (Index index : operation.getIndexs()) {
				AttMain attMain = index.getAttMain();
				if (attMain != null
						&& StringUtils.isNotBlank(index.getFdFileName())) {
					index.getAttMain().setFileName(index.getFdFileName());
					index.getAttMain().setFileType(1);
					index.getAttMain().setStoreType(2);
					index.getAttMain().setModelName(Index.MODEL_NAME);
				} else {
					index.setAttMain(null);
				}
				index.setOperation(operation);
			}
		} else {
			operation.setIndexs(new ArrayList<Index>());
		}
		return super.save(operation);
	}
}
