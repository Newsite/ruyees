package ruyees.otp.action;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ruyees.otp.model.sys.SysOrgElement;
import ruyees.otp.service.SysOrgElementService;

/**
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:31:38
 */
@Controller
@RequestMapping(value = "/self_register")
public class SelfRegisterController {

	@Autowired
	private SysOrgElementService sysOrgElementService;

	@RequestMapping(method = RequestMethod.GET)
	public String init(Locale locale, Model model) {
		List<SysOrgElement> elements = sysOrgElementService.findAllOrgByType(1);
		model.addAttribute("elements", elements);
		return "/base/register2/add";
	}

}
