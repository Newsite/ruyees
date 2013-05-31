package cn.xdf.me.otp.common.utils.security;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @author Zaric
 * @date 2013-6-1 上午12:59:20
 */
public interface PasswordEncoder {
	
	// ~ Methods
	// ========================================================================================================

	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException;

	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException;
}