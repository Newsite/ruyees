package cn.xdf.me.otp.service;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Map;import org.apache.commons.lang3.ObjectUtils;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import org.springframework.util.CollectionUtils;import cn.xdf.me.otp.common.hibernate4.Finder;import cn.xdf.me.otp.common.utils.security.PasswordEncoder;import cn.xdf.me.otp.dao.UserDao;import cn.xdf.me.otp.model.NotifyEntity;import cn.xdf.me.otp.model.sys.RoleEnum;import cn.xdf.me.otp.model.sys.SysOrgElement;import cn.xdf.me.otp.model.sys.SysOrgPerson;import cn.xdf.me.otp.model.sys.UserRole;@Service@Transactional(readOnly = true)public class AccountService extends BaseService {	private static final Logger log = LoggerFactory.getLogger(AccountService.class);	@Autowired	private PasswordEncoder passwordEncoder;	@Autowired	private UserRoleService userRoleService;	@Autowired	private SysOrgElementService sysOrgElementService;	/**	 * 根据登录名称和姓名模糊查询用户	 * 	 * @param key	 * @return	 */	@SuppressWarnings("rawtypes")	public List<SysOrgPerson> findUserByLinkLoginAndRealName(String key) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("key", key);		List<Map> values = findByNamedQuery("findUserByLinkLoginAndRealName",				map, Map.class);		List<SysOrgPerson> sysOrgPersons = new ArrayList<SysOrgPerson>();		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			sysOrgPersons.add(person);		}		return sysOrgPersons;	}	// findUserByLinkLoginOrRealNameAndRole	/**	 * 根据登录名或者真实姓名和角色查询数据	 * 	 * @param key	 * @return	 */	@SuppressWarnings("rawtypes")	public List<SysOrgPerson> findUserByLinkLoginOrRealNameAndRole(String key,			RoleEnum roleEnum) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("key", key);		map.put("role", roleEnum.toString());		List<Map> values = findByNamedQuery(				"findUserByLinkLoginOrRealNameAndRole", map, Map.class);		List<SysOrgPerson> sysOrgPersons = new ArrayList<SysOrgPerson>();		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			sysOrgPersons.add(person);		}		return sysOrgPersons;	}	/**	 * 根据登录名称和姓名模糊查询用户	 * 	 * @param key	 * @return	 */	@SuppressWarnings("rawtypes")	public List<SysOrgPerson> findUserByLinkLoginAndRealNameAndDetp(String key,			String detpId) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("key", key);		map.put("detpId", detpId);		List<Map> values = findByNamedQuery(				"findUserByLinkLoginAndRealNameAndDept", map, Map.class);		List<SysOrgPerson> sysOrgPersons = new ArrayList<SysOrgPerson>();		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			sysOrgPersons.add(person);		}		return sysOrgPersons;	}	/**	 * 根据登录名称和姓名模糊查询用户	 * 	 * @param key	 * @return	 */	@SuppressWarnings({ "unchecked" })	public List<SysOrgPerson> findUserByLinkLoginAndRealNameAndOrg(String key,			String orgId) {		List<SysOrgElement> elements = sysOrgElementService				.findAllChildrenById(orgId);		if (CollectionUtils.isEmpty(elements)) {			return new ArrayList<SysOrgPerson>();		}		List<String> deptIds = new ArrayList<String>();		for (SysOrgElement e : elements) {			deptIds.add(e.getFdId());		}		Finder finder = Finder				.create("from SysOrgPerson person left join fetch person.hbmParent parent");		finder.append("where (person.notifyEntity.realName like :realName or person.fdInitPassword like :name)  and parent.fdId in(:fids)");		finder.setParam("realName", '%' + key + '%');		finder.setParam("name", '%' + key + '%');		finder.setParamList("fids", deptIds.toArray());		return find(finder);	}	/**	 * 根据用户id获取用户	 * 	 * @param id	 * @return	 */	@SuppressWarnings("rawtypes")	public SysOrgPerson findById(String id) {		Map<String, Object> map = new HashMap<String, Object>();		map.put("id", id);		List<Map> values = findByNamedQuery("findUserById", map, Map.class);		SysOrgPerson person = null;		for (Map<String, Object> value : values) {			person = builderUserByMap(value);			return person;		}		return null;	}	private SysOrgPerson builderUserByMap(Map<String, Object> value) {		if (CollectionUtils.isEmpty(value)) {			return null;		}		SysOrgPerson person = new SysOrgPerson();		person.setFdId(value.get("FDID").toString());		person.setLoginName(ObjectUtils.toString(value.get("FD_LOGIN_NAME")				.toString()));		NotifyEntity notity = new NotifyEntity(ObjectUtils.toString(value				.get("FD_MOBILE_NO")), ObjectUtils.toString(value				.get("FD_EMAIL")), ObjectUtils.toString(value.get("REALNAME")));		person.setNotifyEntity(notity);		person.setDeptName(value.get("FD_NAME").toString());		if (value.get("FD_PHOTO_URL") != null) {			person.setFdPhotoUrl(value.get("FD_PHOTO_URL").toString());		}		if (value.get("FD_IS_EMP") != null) {			person.setFdIsEmp(value.get("FD_IS_EMP").toString());		}		return person;	}	@SuppressWarnings("unchecked")	public SysOrgPerson findUserByLoginName(String loginName) {		try {			SysOrgPerson user = userDao.findByLoginName(loginName);			if (user != null) {				// 查询用户角色信息				Finder finder = Finder						.create("SELECT r from  UserRole r where r.sysOrgPerson.fdId=:fdId");				finder.setParam("fdId", user.getFdId());				List<UserRole> userRoles = userRoleService.find(finder);				user.setUserRoles(userRoles);			}			return user;		} catch (Exception e) {			log.error(e.toString());			throw new RuntimeException(e);		}	}	@Transactional(readOnly = false)	public void registerUser(SysOrgPerson user) {		entryptPassword(user);		userDao.save(user);	}	/**	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash	 */	private void entryptPassword(SysOrgPerson user) {		user.setPassword(passwordEncoder.encodePassword(				user.getPlainPassword(), null));	}	private UserDao userDao;	@Autowired	public void setUserDao(UserDao userDao) {		this.userDao = userDao;	}	@SuppressWarnings("unchecked")	@Override	public Class<SysOrgPerson> getEntityClass() {		return SysOrgPerson.class;	}}