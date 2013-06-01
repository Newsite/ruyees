package ruyees.otp.model.notify;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.util.CollectionUtils;

import ruyees.otp.common.utils.TimeUtils;
import ruyees.otp.model.IdEntity;
import ruyees.otp.model.sys.SysOrgPerson;

/**
 * 代办消息
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:19:49
 */
@Entity
@Table(name = "IXDF_OTP_SYS_NOTIFYTODO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotifyTodo extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotifyTodo() {
	}

	/**
	 * 初始化数据
	 * 
	 * <pre>
	 * 
	 * 已读默认为:false
	 * 
	 * 已发默认为:false
	 * 
	 * </pre>
	 * 
	 * @param title
	 *            发送标题
	 * @param body
	 *            发送内容
	 * @param sendUser
	 *            发送人
	 * @param acceptUsers
	 *            接受人
	 */
	public NotifyTodo(String title, String body, SysOrgPerson sendUser,
			List<NotifyAcceptUser> acceptUsers, NotifyEnum notifyEnum,
			NotifyTypeEnum notifyTypeEnum) {
		this.title = title;
		this.body = body;
		this.sendUser = sendUser;
		this.acceptUser = acceptUsers;
		this.notifyEnum = notifyEnum;
		this.notifyTypeEnum = notifyTypeEnum;
		this.isRead = false;
		this.isSend = false;
	}

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 内容
	 */
	private String body;

	/**
	 * 发送人
	 */
	private SysOrgPerson sendUser;

	/**
	 * 接受人
	 */
	private List<NotifyAcceptUser> acceptUser;

	/**
	 * 是否已读
	 */
	private boolean isRead;

	/**
	 * 是否已发
	 */
	private boolean isSend;

	/**
	 * 连接
	 */
	private String link;

	/**
	 * 发送类型
	 */
	private NotifyEnum notifyEnum;

	/*
	 * 消息类型
	 */
	private NotifyTypeEnum notifyTypeEnum;

	@Column(name = "notifyTitle", length = 500)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "notifyBody")
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sendUserId")
	public SysOrgPerson getSendUser() {
		return sendUser;
	}

	public void setSendUser(SysOrgPerson sendUser) {
		this.sendUser = sendUser;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH,
			CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "notifyTodo")
	public List<NotifyAcceptUser> getAcceptUser() {
		return acceptUser;
	}

	public void setAcceptUser(List<NotifyAcceptUser> acceptUser) {
		this.acceptUser = acceptUser;
	}

	/**
	 * 是否已读
	 * 
	 * @return
	 */
	public boolean getIsRead() {
		return isRead;
	}

	/**
	 * 是否已读
	 * 
	 * @param isRead
	 */
	public void setIsRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * 代办连接
	 * 
	 * @return
	 */
	@Column(length = 300)
	public String getLink() {
		return link;
	}

	/**
	 * 代办连接
	 * 
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	public boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public NotifyEnum getNotifyEnum() {
		return notifyEnum;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public NotifyTypeEnum getNotifyTypeEnum() {
		return notifyTypeEnum;
	}

	public void setNotifyEnum(NotifyEnum notifyEnum) {
		this.notifyEnum = notifyEnum;
	}

	public void setNotifyTypeEnum(NotifyTypeEnum notifyTypeEnum) {
		this.notifyTypeEnum = notifyTypeEnum;
	}

	@Transient
	public String[] getToMails() {
		if (CollectionUtils.isEmpty(acceptUser))
			return null;
		String[] tos = new String[acceptUser.size()];
		int i = 0;
		String mails = null;
		for (NotifyAcceptUser accept : acceptUser) {
			mails = accept.getPerson().getFdEmail();
			if (StringUtils.isBlank(mails))
				continue;
			tos[i] = mails;
			i++;
		}
		return tos;
	}

	@Transient
	public String[] getToMobileNos() {
		if (CollectionUtils.isEmpty(acceptUser))
			return null;
		String[] tos = new String[acceptUser.size()];
		int i = 0;
		String message = null;
		for (NotifyAcceptUser accept : acceptUser) {
			message = accept.getNotifyEntity().getFdMobileNo();
			if (StringUtils.isBlank(message))
				continue;
			tos[i] = message;
			i++;
		}
		return tos;
	}

	@Transient
	public String getSendDate() {
		if (acceptUser == null || acceptUser.size() < 1)
			return null;
		NotifyAcceptUser user = acceptUser.get(0);
		return TimeUtils.covertTimeStamp(user.getSendTime());
	}

}
