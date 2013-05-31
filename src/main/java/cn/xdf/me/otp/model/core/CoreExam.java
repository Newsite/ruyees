package cn.xdf.me.otp.model.core;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.xdf.me.otp.model.IdEntity;

import com.google.common.collect.ImmutableList;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "IXDF_OTP_CORE_EXAM")
@Inheritance(strategy = InheritanceType.JOINED)
public class CoreExam extends IdEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer fdIsUsual;
	private Date fdInvalidationTime;
	private String fdInvalidationStatus;
	private String fdDescription;
	private String fdSubject;
	private Double fdStandardScore;
	private Date fdCreateTime;
	private String fdOption;
	private String fdAnswer;
	private String fdCreatorId;
	private Integer fdType;
	private Integer fdOrder;
	
	/**
	 * 
	 * 是否常用
	 */
	public Integer getFdIsUsual() {
		return fdIsUsual;
	}

	public void setFdIsUsual(Integer fdIsUsual) {
		this.fdIsUsual = fdIsUsual;
	}

	/**
	 * 
	 * 失效时间
	 */
	public Date getFdInvalidationTime() {
		return fdInvalidationTime;
	}

	public void setFdInvalidationTime(Date fdInvalidationTime) {
		this.fdInvalidationTime = fdInvalidationTime;
	}

	/**
	 * 
	 * 失效状态
	 */
	public String getFdInvalidationStatus() {
		return fdInvalidationStatus;
	}

	public void setFdInvalidationStatus(String fdInvalidationStatus) {
		this.fdInvalidationStatus = fdInvalidationStatus;
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
	 * 题目
	 */
	public String getFdSubject() {
		return fdSubject;
	}

	public void setFdSubject(String fdSubject) {
		this.fdSubject = fdSubject;
	}

	/**
	 * 
	 * 标准分
	 */
	public Double getFdStandardScore() {
		return fdStandardScore;
	}

	public void setFdStandardScore(Double fdStandardScore) {
		this.fdStandardScore = fdStandardScore;
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
	 * 选项
	 */
	public String getFdOption() {
		return fdOption;
	}

	public void setFdOption(String fdOption) {
		this.fdOption = fdOption;
	}

	/**
	 * 
	 * 答案
	 */
	public String getFdAnswer() {
		return fdAnswer;
	}

	public void setFdAnswer(String fdAnswer) {
		this.fdAnswer = fdAnswer;
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
	 * 0:单选题1：多选题
	 */
	public Integer getFdType() {
		return fdType;
	}

	public void setFdType(Integer fdType) {
		this.fdType = fdType;
	}

	/**
	 * 
	 * 试题排序
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	@Transient
	public List<String> getOptions() {
		if (getFdOption() == null)
			return null;
		return ImmutableList.copyOf(StringUtils.split(getFdOption(), ","));
	}

	@Transient
	public String getHtmlTag() {
		if (getFdType() == 1) {
			return "checkbox";
		}
		return "radio";
	}

}
