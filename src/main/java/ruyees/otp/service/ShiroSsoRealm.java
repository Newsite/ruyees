package ruyees.otp.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.ShiroDbRealm.ShiroUser;
import ruyees.otp.sso.model.TrustedSsoAuthenticationToken;

public class ShiroSsoRealm extends AuthorizingRealm {

	public ShiroSsoRealm() {
		// 设置无需凭证，因为从sso认证后才会有用户名
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
		// 设置token为我们自定义的
		setAuthenticationTokenClass(TrustedSsoAuthenticationToken.class);
	}

	protected AccountService accountService;

	// private ILoginService ssoLoginService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {

		TrustedSsoAuthenticationToken token = (TrustedSsoAuthenticationToken) authcToken;
		System.out.println("-------------authcToken==" + token.getUsername());
		if (StringUtils.isBlank(token.getUsername())) {
			return null;
		}
		SysOrgPerson user = accountService.findUserByLoginName(token
				.getUsername());
		if (user != null) {
			return new SimpleAuthenticationInfo(new ShiroUser(user.getFdId(),
					user.getLoginName(), user.getRealName()),
					token.getCredentials(), getName());
			/*
			 * return new SimpleAuthenticationInfo(new ShiroUser(user.getFdId(),
			 * user.getLoginName(), user.getRealName()), user.getPassword(),
			 * null, getName());
			 */
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SysOrgPerson user = accountService
				.findUserByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return info;
	}

	/**
	 * 清空用户关联权限认证，待下次使用时重新加载。
	 * 
	 * @param principal
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清空所有关联认证
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	/**
	 * 设定Password校验的Md5算法
	 */
	/*
	 * @PostConstruct public void initCredentialsMatcher() {
	 * Md5CredentialsMatcher matcher = new Md5CredentialsMatcher();
	 * setCredentialsMatcher(matcher); }
	 */

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/*
	 * @Autowired public void setSsoLoginService(ILoginService ssoLoginService)
	 * { this.ssoLoginService = ssoLoginService; }
	 */
}