package cn.xdf.me.otp.model.notify;

/**
 * 代办消息执行类
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:19:37
 */
public interface NotifyExcute {

	/**
	 * 执行方法
	 */
	public void excute(NotifyTodo todo);

	/**
	 * 执行发送邮件
	 * 
	 * @param toEmail
	 * @param fromEmail
	 * @param title
	 * @param content
	 */
	public void excute(String toEmail, String fromEmail, String title,
			String content);

}
