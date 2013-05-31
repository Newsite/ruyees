package cn.xdf.me.otp.model.notify.ntype;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cn.xdf.me.otp.model.notify.NotifyExcute;
import cn.xdf.me.otp.model.notify.NotifyTodo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 邮件
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:20:14
 */
public class NotifyMailExcute implements NotifyExcute {

	private static final String FROM_MAIL = "km@xdf.cn";
	private static final Logger log = LoggerFactory.getLogger(NotifyMailExcute.class);

	/**
	 * @use org.springframework.mail.javamail.JavaMailSenderImpl is easy
	 */
	public void excute(NotifyTodo todo) {

		try {
			if (todo.getToMails() == null)
				return;
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true,
					DEFAULT_ENCODING);
			for (String mail : todo.getToMails()) {
				log.info("toMail=========" + mail);
			}
			helper.setTo(todo.getToMails());
			// helper.setFrom(todo.getSendUser().getNotifyEntity().getFdEmail());
			helper.setFrom(FROM_MAIL);
			helper.setSubject(todo.getTitle());

			String content = generateContent(todo);
			helper.setText(content, true);
			mailSender.send(msg);
			logger.info("HTML版邮件已发送");
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}

	}

	/**
	 * 直接发送邮件
	 */
	public void excute(String toEmail, String fromEmail, String title,
			String content) {
		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true,
					DEFAULT_ENCODING);

			helper.setTo(toEmail);
			helper.setFrom(fromEmail);
			helper.setSubject(title);
			helper.setText(content, true);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送");
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory
			.getLogger(NotifyMailExcute.class);

	private JavaMailSender mailSender;

	private Template template;

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	@SuppressWarnings("rawtypes")
	private String generateContent(NotifyTodo todo) throws MessagingException {

		try {
			Map context = Collections.singletonMap("todo", todo);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template,
					context);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 获取classpath中的附件.
	 */
	/*
	 * private File generateAttachment() throws MessagingException { try {
	 * Resource resource = new ClassPathResource( "/email/mailAttachment.txt");
	 * return resource.getFile(); } catch (IOException e) {
	 * logger.error("构造邮件失败,附件文件不存在", e); throw new
	 * MessagingException("附件文件不存在", e); } }
	 */

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration)
			throws IOException {
		// 根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl",
				DEFAULT_ENCODING);
	}

}
