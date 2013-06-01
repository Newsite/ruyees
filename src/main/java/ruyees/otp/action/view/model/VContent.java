package ruyees.otp.action.view.model;

/**
 * 页面文档内容图片
 * @author Zaric
 * @date  2013-6-1 上午12:39:05
 */
public class VContent {

	/**
	 * 返回状态
	 */
	private String status;

	/**
	 * 图片序号
	 */
	private int fdNo;

	/**
	 * 内容地址
	 */
	private String fdUrl;

	/**
	 * 内容地址
	 */
	private String remark;

	public VContent(String status, int fdNo, String fdUrl, String remark) {
		super();
		this.status = status;
		this.fdNo = fdNo;
		this.fdUrl = fdUrl;
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFdNo() {
		return fdNo;
	}

	public void setFdNo(int fdNo) {
		this.fdNo = fdNo;
	}

	public String getFdUrl() {
		return fdUrl;
	}

	public void setFdUrl(String fdUrl) {
		this.fdUrl = fdUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
