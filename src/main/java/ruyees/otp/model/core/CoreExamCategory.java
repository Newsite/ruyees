package ruyees.otp.model.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_OTP_CORE_EXAM_CATEGORY")
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreExamCategory extends IdEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fdName;
	private String fdDescription;
	private String fdCreatorId;
	private Date fdCreateTime;

	/**
	 * 
	 * 库名
	 */
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 
	 * 描述
	 */
	@Column(length = 2000)
	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
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
	 * 
	 * 创建时间
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

}
