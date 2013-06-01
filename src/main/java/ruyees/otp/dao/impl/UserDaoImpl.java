package ruyees.otp.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import ruyees.otp.common.hibernate4.Finder;
import ruyees.otp.common.hibernate4.HibernateSimpleDao;
import ruyees.otp.dao.UserDao;
import ruyees.otp.model.sys.SysOrgPerson;

public class UserDaoImpl extends HibernateSimpleDao implements UserDao {

	@SuppressWarnings("rawtypes")
	public SysOrgPerson findByLoginName(String loginName) {
		Finder finder = Finder
				.create("from SysOrgPerson where loginName=:fdname or notifyEntity.fdEmail=:emailName");
		finder.setParam("fdname", loginName);
		finder.setParam("emailName", loginName);
		List lists = find(finder);
		if (!CollectionUtils.isEmpty(lists)) {
			return (SysOrgPerson) lists.get(0);
		}
		return null;
	}

	public SysOrgPerson save(SysOrgPerson entity) {
		Assert.notNull(entity);
		getSession().merge(entity);
		return entity;
	}

	protected Criteria createCriteria(Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(SysOrgPerson.class);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

}
