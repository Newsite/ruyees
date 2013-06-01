package ruyees.otp.model.core;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.IdEntity;
import ruyees.otp.model.base.AttMain;

@Entity
@Table(name = "IXDF_OTP_CORE_INDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreIndex extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fdIndexName;
	private Double fdValue;
	private String fdFileName;
	private Integer fdOrder;
	
	/**
	 * 对应附件表
	 */
	private AttMain attMain;

	//@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	//@OneToOne(cascade = {CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@OneToOne(cascade = {CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "attMainId")
	public AttMain getAttMain() {
		return attMain;
	}

	public void setAttMain(AttMain attMain) {
		this.attMain = attMain;
	}

	/**
	 * 
	 * 指标名称
	 */
	public String getFdIndexName() {
		return fdIndexName;
	}

	public void setFdIndexName(String fdIndexName) {
		this.fdIndexName = fdIndexName;
	}

	/**
	 * 
	 * 指标分值
	 */
	public Double getFdValue() {
		return fdValue;
	}

	public void setFdValue(Double fdValue) {
		this.fdValue = fdValue;
	}

	/**
	 * 
	 * 模板名
	 */

	public String getFdFileName() {
		return fdFileName;
	}

	public void setFdFileName(String fdFileName) {
		this.fdFileName = fdFileName;
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
	
}
