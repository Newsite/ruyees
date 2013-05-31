package cn.xdf.me.otp.action.ajax;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xdf.me.otp.service.RegisterService;

/**
 * 注册验证
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:33:50
 */
@Controller
@RequestMapping(value = "/ajax/register")
@Scope("request")
public class RegisterAjaxController {

	@Autowired
	private RegisterService registerService;

	@RequestMapping(value = "checkIdentityCard")
	@ResponseBody
	public int checkIdentityCard(HttpServletRequest request) {
		String str = request.getParameter("str");

		int count = registerService.checkIdentityCard(str);

		return count;
	}

}
