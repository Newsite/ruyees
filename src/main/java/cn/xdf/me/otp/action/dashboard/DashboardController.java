package cn.xdf.me.otp.action.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.xdf.me.otp.model.sys.UserRole;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.UserRoleService;
import cn.xdf.me.otp.service.notify.NotifyService;
import cn.xdf.me.otp.utils.ShiroUtils;

@RequestMapping("/dashboard")
@Controller
@Scope("request")
public class DashboardController {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private NotifyService notifyService;

	@RequestMapping(method = RequestMethod.GET)
	public String init(Model model) {
		ShiroUser user = ShiroUtils.getUser();
		List<UserRole> role = userRoleService.findUserRoles(user.getId());
		if (role == null) {
			return "/dashboard/no-role";
		} else {
			int count = notifyService.getUnreadNotifyCount(user.getId());
			model.addAttribute("count", count);
			return "/dashboard/dashboard";
			/*
			 * RoleEnum roleEnum = ShiroUtils.getUserHightRole(role); if
			 * (roleEnum == RoleEnum.default_role) { return
			 * "/dashboard/no-role"; } ////取未读消息个数 return
			 * ShiroUtils.getUserHightRole(role).getUrl();
			 */
			/*
			 * if (role.size() == 1) { RoleEnum roleEnum =
			 * ShiroUtils.getUserHightRole(role); if (roleEnum ==
			 * RoleEnum.default_role) { return "/dashboard/no-role"; }
			 * ////取未读消息个数 int count=
			 * notifyService.getUnreadNotifyCount(user.getId());
			 * model.addAttribute("count", count); return
			 * ShiroUtils.getUserHightRole(role).getUrl(); } else { return
			 * "/dashboard/dashboard"; }
			 */
		}
	}
}
