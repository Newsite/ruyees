package ruyees.otp.model.core;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;

@Entity
@Table(name = "IXDF_OTP_CORE_OPERTION")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreOperation extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fdName;
	private Integer fdOrder;
	private String fdMustBook;
	private String fdReferBook;
	private String fdRequest;
	private String fdDescription;
	private String fdCreatorId;
	private Date fdCreateTime;

	/**
	 * 作业背景图片
	 */
	private String backgroundUrl;
	/**
	 * 
	 * 作业名称
	 */
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 
	 * 作业序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 
	 * 必看书目
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdMustBook() {
		return fdMustBook;
	}

	public void setFdMustBook(String fdMustBook) {
		this.fdMustBook = fdMustBook;
	}

	/**
	 * 
	 * 参考书目
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdReferBook() {
		return fdReferBook;
	}

	public void setFdReferBook(String fdReferBook) {
		this.fdReferBook = fdReferBook;
	}

	/**
	 * 
	 * 成果要求
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdRequest() {
		return fdRequest;
	}

	public void setFdRequest(String fdRequest) {
		this.fdRequest = fdRequest;
	}

	/**
	 * 
	 * 描述
	 */
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 
	 * 创建时间
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 
	 * 创建者
	 */
	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	/**
	 * 对应作业的背景图片的url
	 * 
	 * @return
	 */
	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}
	
}
