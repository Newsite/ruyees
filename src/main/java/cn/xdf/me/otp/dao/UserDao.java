package cn.xdf.me.otp.dao;

import cn.xdf.me.otp.model.sys.SysOrgPerson;

public interface UserDao {

	SysOrgPerson findByLoginName(String loginName);

	SysOrgPerson save(SysOrgPerson entity);

}
