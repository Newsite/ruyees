package ruyees.otp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ruyees.otp.model.sys.SysOrgPerson;
import ruyees.otp.service.AccountService;
import ruyees.otp.sso.IGetUserFromTokenService;
import ruyees.otp.sso.e2.constant.SSOToE2Constant;
import ruyees.otp.sso.e2.util.CookieHelper;
import ruyees.otp.sso.e2.vo.UserModel;
import ruyees.otp.sso.model.TrustedSsoAuthenticationToken;
import ruyees.otp.utils.MD5Util;
import ruyees.otp.utils.ShiroUtils;

/**
 * 
 * @author Zaric
 * @date  2013-6-1 上午12:31:18
 */
@RequestMapping("/login")
@Controller
@Scope("request")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public String init(HttpServletResponse response) {

		if (ShiroUtils.hasSecurity()) {
			return "redirect:/";
		}
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(
			@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,
			@RequestParam(FormAuthenticationFilter.DEFAULT_PASSWORD_PARAM) String passWord,
			HttpServletResponse response, HttpServletRequest request,
			Model model) {
		TrustedSsoAuthenticationToken token = null;
		if (!(ShiroUtils.checkUserName(userName))) {
			token = createToken(request, response);
		} else {
			token = doDbLogin(userName, passWord);
		}
		if (token == null) {
			/*
			 * String msg =
			 * "createToken method implementation returned null. A valid non-null AuthenticationToken "
			 * + "must be created in order to execute a login attempt."; throw
			 * new IllegalStateException(msg);
			 */
			return "login";
		}
		try {
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			return "redirect:/";
		} catch (AuthenticationException e) {
			return "login";
		}
		/*
		 * model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,
		 * userName);
		 */
		// return "login";
	}

	private TrustedSsoAuthenticationToken doDbLogin(String username,
			String password) {
		try {
			SysOrgPerson user = accountService.findUserByLoginName(username);
			if (user == null)
				return null;
			if (!user.getPassword().equals(MD5Util.getMD5String(password))) {
				return null;
			}
			return new TrustedSsoAuthenticationToken(username);
		} catch (Exception ex) {
			throw new AuthenticationException(ex);
		}
	}

	protected TrustedSsoAuthenticationToken createToken(HttpServletRequest req,
			HttpServletResponse res) {
		TrustedSsoAuthenticationToken token = null;
		String cookieValue = CookieHelper.getCookie(req,
				ssoToE2Constant.getCookieName());
		UserModel userModel = null;
		try {
			userModel = ssoGetUserFromTokenService.getCurrentUser(req, res,
					cookieValue);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (userModel == null) {
			// return null;
			token = new TrustedSsoAuthenticationToken("");
		} else {
			token = new TrustedSsoAuthenticationToken(userModel.getLoginName());
		}
		return token;
	}

	@Autowired
	private IGetUserFromTokenService ssoGetUserFromTokenService;

	@Autowired
	private SSOToE2Constant ssoToE2Constant;

	@Autowired
	private AccountService accountService;
}
