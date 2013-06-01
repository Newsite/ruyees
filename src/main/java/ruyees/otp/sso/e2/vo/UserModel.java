package ruyees.otp.sso.e2.vo;

/**
 * 
 * 当前登陆用户字段
 * 
 * @author Zaric
 * @date 2013-6-1 上午1:35:47
 */
public class UserModel {
	private String nickName;
	private String email;
	private String userId;
	private String token;

	private String loginName;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
