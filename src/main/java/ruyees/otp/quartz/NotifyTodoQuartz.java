package ruyees.otp.quartz;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import ruyees.otp.service.notify.NotifyTodoService;

/**
 * 代办消息
 * 
 * 定时发送
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:24:25
 */
public class NotifyTodoQuartz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private NotifyTodoService notifyTodoService;

	public void notifyTodo() {
		System.out.println("-------send Mail---------");
		// notifyTodoService.excuteNotSend();
	}

}
