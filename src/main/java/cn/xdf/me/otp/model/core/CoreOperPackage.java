package cn.xdf.me.otp.model.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.xdf.me.otp.model.IdEntity;

@Entity
@Table(name = "IXDF_OTP_CORE_OPER_PACKAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreOperPackage extends IdEntity {
	
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
	 * 作业包名
	 */
	@Column(name="FDNAME")
	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	/**
	 * 
	 * 作业包描述
	 */
	@Column(length = 2000,name="FDDESCRIPTION")
	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 
	 * 作业包创建者
	 */
	@Column(name="FDCREATORID")
	public String getFdCreatorId() {
		return fdCreatorId;
	}

	public void setFdCreatorId(String fdCreatorId) {
		this.fdCreatorId = fdCreatorId;
	}

	/**
	 * 
	 * 作业包创建时间
	 */
	@Column(name="FDCREATETIME")
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

}
