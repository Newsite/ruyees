package cn.xdf.me.otp.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 人员登出
 * 
 * <pre>
 * Spring Bean的ID为：ssoLogoutService
 * </pre>
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:38:38
 */
public interface ILogoutService {

	/**
	 * e2 后台退出接口声明
	 * 
	 * @throws Exception
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
