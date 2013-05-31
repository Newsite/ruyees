package cn.xdf.me.otp.action.view.model;

/**
 * 作业对应步骤
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:40:18
 */
public class VOperation {

	/**
	 * 改状是否成功
	 */
	private Boolean isCuss;

	/**
	 * 作业名称
	 */
	private String fileName;

	/**
	 * 返回路径
	 */
	private String fdPath;

	/**
	 * 附件Id
	 */
	private String attId;

	public VOperation() {
	}

	public VOperation(Boolean isCuss, String fileName, String fdPath) {
		this.isCuss = isCuss;
		this.fileName = fileName;
		this.fdPath = fdPath;
	}

	public Boolean getIsCuss() {
		return isCuss;
	}

	public void setIsCuss(Boolean isCuss) {
		this.isCuss = isCuss;
	}

	public String getFdPath() {
		return fdPath;
	}

	public void setFdPath(String fdPath) {
		this.fdPath = fdPath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAttId() {
		return attId;
	}

	public void setAttId(String attId) {
		this.attId = attId;
	}
}
