package cn.xdf.me.otp.action.view.model;

/**
 * 页面部门提示信息
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:39:15
 */
public class VDept {

	/**
	 * 部门编码
	 */
	private String fdId;

	/**
	 * 部门名称
	 */
	private String fdName;

	/**
	 * 部门编码
	 */
	private String fdNo;

	public VDept(String id, String name) {
		this.fdId = id;
		this.fdName = name;
	}

	public String getFdId() {
		return fdId;
	}

	public void setFdId(String fdId) {
		this.fdId = fdId;
	}

	public String getFdName() {
		return fdName;
	}

	public void setFdName(String fdName) {
		this.fdName = fdName;
	}

	public String getFdNo() {
		return fdNo;
	}

	public void setFdNo(String fdNo) {
		this.fdNo = fdNo;
	}

}
