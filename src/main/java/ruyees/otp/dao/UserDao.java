package ruyees.otp.dao;

import ruyees.otp.model.sys.SysOrgPerson;

public interface UserDao {

	SysOrgPerson findByLoginName(String loginName);

	SysOrgPerson save(SysOrgPerson entity);

}
