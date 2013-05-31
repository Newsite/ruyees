package cn.xdf.me.otp.action.view.model;

/**
 * 页面参与人员提示信息
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:41:45
 */
public class VUser {

	/**
	 * 员工编码
	 */
	private String uid;

	/**
	 * 员工姓名
	 */
	private String uname;

	/**
	 * 部门编码
	 */
	private String deptId;

	/**
	 * 部门名称
	 */
	private String deptName;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
