package ruyees.otp.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author Zaric
 * @date  2013-6-1 上午12:31:13
 */
@Controller
@Scope("request")
public class IntroController {
	
	@RequestMapping(value = "/intro", method = RequestMethod.GET)
	public String init(HttpServletResponse response) {
		return "intro";
	}
}
