package cn.xdf.me.otp.model.notify.ntype;

import cn.xdf.me.otp.model.notify.NotifyExcute;
import cn.xdf.me.otp.model.notify.NotifyTodo;

/**
 * 短信
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:20:27
 */
public class NotifyMessageExcute implements NotifyExcute {

	public void excute(NotifyTodo todo) {
		System.out.println("----Messsage----");

	}

	public void excute(String toEmail, String fromEmail, String title,
			String content) {
		// TODO Auto-generated method stub
	}

}
