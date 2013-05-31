package cn.xdf.me.otp.action.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.xdf.me.otp.action.view.model.VNotify;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.notify.NotifyService;

/**
 * 获取消息数量
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:33:19
 */
@Controller
@RequestMapping(value = "/ajax/notify")
@Scope("request")
public class NotifyAjaxController {

	@Autowired
	private NotifyService notifyService;

	@RequestMapping(value = "notifyCount")
	@ResponseBody
	public int unReadNotifyCount(HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		int count = notifyService.getUnreadNotifyCount(user.id);

		return count;
	}

	@RequestMapping(value = "getNotify/{fdId}")
	@ResponseBody
	public VNotify getNotify(@PathVariable("fdId") String fdId,
			HttpServletRequest request) {
		VNotify vNotify = notifyService.getVNotify(fdId);

		notifyService.setRead(fdId);// 设置已读

		return vNotify;
	}
}
