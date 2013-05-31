package cn.xdf.me.otp.utils.model;

public class FlowModel {

	private String fdId;
	private String fdName;
	private boolean enableByUser;

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

	public boolean isEnableByUser() {
		return enableByUser;
	}

	public void setEnableByUser(boolean enableByUser) {
		this.enableByUser = enableByUser;
	}

}
