package cn.xdf.me.otp.service.base;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xdf.me.otp.model.base.Index;
import cn.xdf.me.otp.service.BaseService;

@Service
@Transactional(readOnly = true)
public class IndexService extends BaseService {

	@SuppressWarnings("unchecked")
	@Override
	public Class<Index> getEntityClass() {
		return Index.class;
	}

}
