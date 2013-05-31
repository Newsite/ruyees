package cn.xdf.me.otp.action.notify;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.xdf.me.otp.common.page.Pagination;
import cn.xdf.me.otp.common.utils.ComUtils;
import cn.xdf.me.otp.model.notify.NotifyAcceptUser;
import cn.xdf.me.otp.model.notify.NotifyEnum;
import cn.xdf.me.otp.model.notify.NotifyTodo;
import cn.xdf.me.otp.model.notify.NotifyTypeEnum;
import cn.xdf.me.otp.service.AccountService;
import cn.xdf.me.otp.service.ShiroDbRealm.ShiroUser;
import cn.xdf.me.otp.service.notify.NotifyService;
import cn.xdf.me.otp.service.notify.NotifyTodoService;
import cn.xdf.me.otp.utils.ShiroUtils;

@Controller
@RequestMapping(value = "/notify")
public class NotifyController {
	@Autowired
	private NotifyService notifyService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private NotifyTodoService notifyTodoService;

	/*
	 * 获取消息列表
	 */
	@RequestMapping(value = "/list/{sendReceive}/{type}", method = RequestMethod.GET)
	public String list(Model model, String pageNo,
			@PathVariable("sendReceive") int sendReceive,
			@PathVariable("type") String type, HttpServletRequest request) {
		model.addAttribute("active", "notify");
		switch (sendReceive) {
		case 0:
			return sentList(model, pageNo, type, request);
		case 1:
			return receivedList(model, pageNo, type, request);
		}

		return "redirect:/login";
	}

	/*
	 * 获取发送消息列表
	 */
	@RequestMapping(value = "/sentList/{type}", method = RequestMethod.GET)
	public String sentList(Model model, String pageNo,
			@PathVariable("type") String type, HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		if (pageNo == null || "".equals(pageNo)) {
			pageNo = "1";
		}

		Pagination page = notifyService.findNotifyByUidOfSent(user.id,
				NumberUtils.createInteger(pageNo), type);
		model.addAttribute("page", page);
		model.addAttribute("notifyType", NotifyTypeEnum.values());

		return "/base/notify/list_sent";
	}

	/*
	 * 获取接收消息列表
	 */
	@RequestMapping(value = "/receivedList/{type}", method = RequestMethod.GET)
	public String receivedList(Model model, String pageNo,
			@PathVariable("type") String type, HttpServletRequest request) {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();

		if (pageNo == null || "".equals(pageNo)) {
			pageNo = "1";
		}
		
		Pagination page = notifyService.findNotifyByUidOfReceived(user.id,
				NumberUtils.createInteger(pageNo), type);
		model.addAttribute("page", page);
		model.addAttribute("notifyType", NotifyTypeEnum.values());

		return "/base/notify/list_received";
	}

	/**
	 * 新教师给导师发消息
	 * 
	 * @param report
	 * @param pageNo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "toAdd/{guidId}", method = RequestMethod.GET)
	public String toAdd(Model model, @PathVariable("guidId") String guidId, HttpServletRequest request) {
		model.addAttribute("guidId", guidId);
		return "/base/notify/to_edit";
	}

	@RequestMapping(value = "toSave", method = RequestMethod.POST)
	public String toSave(NotifyTodo notifyTodo, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		List<NotifyAcceptUser> accepList =notifyTodo.getAcceptUser();
		for(NotifyAcceptUser accept:accepList){
			accept.setSendTime(ComUtils.now());
			accept.setNotifyTodo(notifyTodo);
		}
		
		ShiroUser user = ShiroUtils.getUser();
		String uId = user.id;
		notifyTodo.setSendUser(accountService.findById(uId));
		notifyTodo.setAcceptUser(accepList);
		notifyTodo.setNotifyEnum(NotifyEnum.MESSAGE);
		notifyTodo.setNotifyTypeEnum(NotifyTypeEnum.TODO);
		notifyTodoService.save(notifyTodo);
		return "redirect:/notify/list/0/ALL";
	}
}
