package ruyees.otp.model.notify;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;
import ruyees.otp.model.NotifyEntity;
import ruyees.otp.model.sys.SysOrgPerson;

/**
 * 发送人
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:19:08
 */
@Entity
@Table(name = "IXDF_OTP_NOTIFYTODO_ACCEPTS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class NotifyAcceptUser extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 接收人
	 */
	private SysOrgPerson person;

	/**
	 * 是否已读
	 */
	private boolean isRead;

	/**
	 * 是否已发
	 */
	private boolean isSend;

	/**
	 * 发送时间
	 */
	private Timestamp sendTime;

	private NotifyTodo notifyTodo;

	private NotifyEntity notifyEntity;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "fdMobileNo", column = @Column(name = "FD_MOBILE_NO")),
			@AttributeOverride(name = "fdEmail", column = @Column(name = "fdEmail")),
			@AttributeOverride(name = "realName", column = @Column(name = "realName")), })
	public NotifyEntity getNotifyEntity() {
		return notifyEntity;
	}

	public void setNotifyEntity(NotifyEntity notifyEntity) {
		this.notifyEntity = notifyEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "notifyTodoId")
	public NotifyTodo getNotifyTodo() {
		return notifyTodo;
	}

	public void setNotifyTodo(NotifyTodo notifyTodo) {
		this.notifyTodo = notifyTodo;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personId")
	public SysOrgPerson getPerson() {
		return person;
	}

	public void setPerson(SysOrgPerson person) {
		this.person = person;
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
	 * 是否发送
	 * 
	 */
	public boolean getIsSend() {
		return isSend;
	}

	/**
	 * 是否发送
	 * 
	 * @param isSend
	 */
	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	/**
	 * 发送时间
	 */
	public Timestamp getSendTime() {
		return sendTime;
	}

	/**
	 * 发送时间
	 * 
	 * @param sendTime
	 */
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
}
