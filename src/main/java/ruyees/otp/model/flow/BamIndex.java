package ruyees.otp.model.flow;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import ruyees.otp.model.base.AttMain;
import ruyees.otp.model.core.CoreIndex;

/**
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:15:28
 */
@Entity
@Table(name = "IXDF_OTP_BAM_INDEX")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@PrimaryKeyJoinColumn(name = "bamIndexId")
public class BamIndex extends CoreIndex {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 状态(0:未上传,1:已上传，2：已提交，3：驳回，4：审核通过)
	 */
	private int status;

	/**
	 * 上传时间
	 */
	private Timestamp uploadTime;

	/**
	 * 审批时间
	 */
	private Timestamp appoveTime;

	/**
	 * 对应上传的作业包
	 */
	private AttMain bamAttMain;
	/**
	 * 指导教师对新教师的评分
	 */
	private Float fdToValue;

	/**
	 * 对应作业
	 */
	private BamOperation bamOperation;

	/**
	 * 作业评语
	 */
	private String remark;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 
	 * 对应作业
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bamOperationId")
	public BamOperation getBamOperation() {
		return bamOperation;
	}

	public void setBamOperation(BamOperation bamoperation) {
		this.bamOperation = bamoperation;
	}

	/**
	 * 审核时间
	 * 
	 * @return
	 */
	public Timestamp getAppoveTime() {
		return appoveTime;
	}

	/**
	 * 审核时间
	 * 
	 * @param appoveTime
	 */
	public void setAppoveTime(Timestamp appoveTime) {
		this.appoveTime = appoveTime;
	}

	/**
	 * 上传时间
	 * 
	 * @return
	 */
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	/**
	 * 上传时间
	 * 
	 * @param uploadTime
	 */
	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * 状态(0:未上传,1:已上传，2：已提交，3：驳回，4：审核通过)
	 * 
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * 状态(0:未上传,1:已上传，2：已提交，3：驳回，4：审核通过)
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 指导教师对新教师的评分
	 * 
	 * @return
	 */
	@Column(precision = 10, scale = 1)
	public Float getFdToValue() {
		return fdToValue;
	}

	/**
	 * 指导教师对新教师的评分
	 * 
	 * @param fdToValue
	 */
	public void setFdToValue(Float fdToValue) {
		this.fdToValue = fdToValue;
	}

	/**
	 * 新教师上传的指标（作业）
	 * 
	 * @return
	 */
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "bamAttMainId")
	public AttMain getBamAttMain() {
		return bamAttMain;
	}

	/**
	 * 新教师上传的指标（作业）
	 * 
	 * @param bamAttMain
	 */
	public void setBamAttMain(AttMain bamAttMain) {
		this.bamAttMain = bamAttMain;
	}

	public BamIndex() {
	}

	public BamIndex(String fdId) {
		this.fdId = fdId;
	}

	/**
	 * 上传按钮的Class 状态(0:未上传,1:已上传，2：已提交，3：驳回，4：审核通过)
	 * 
	 * @return
	 */
	@Transient
	public String getUploadButtonClass() {
		if (status == 2 || status == 4) {
			return "disabled";
		}
		return "";
	}

	/**
	 * 提交按钮的class 状态(0:未上传,1:已上传，2：已提交，3：驳回，4：审核通过)
	 * 
	 * @return
	 */
	@Transient
	public String getSubmitButtonClass() {
		if (status == 0 || status == 2 || status == 3 || status == 4) {
			return "disabled";
		}
		return "";
	}

	/**
	 * 查看按钮的class 状态(0:未上传,1:已上传，2：已提交，3：驳回，4：审核通过)
	 * 
	 * @return
	 */
	@Transient
	public String getSeeButtonClass() {
		if (status == 0) {
			return "disabled";
		}
		return "";
	}

}