package ruyees.otp.common.utils.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component("passwordEncoder")
public class Md5PasswordEncoder extends BaseDigestPasswordEncoder implements
		PasswordEncoder {

	// ~ Methods
	// ========================================================================================================

	public String encodePassword(String rawPass, Object salt) {
		String saltedPass = mergePasswordAndSalt(rawPass, salt, false);

		if (!getEncodeHashAsBase64()) {
			return DigestUtils.md5Hex(saltedPass);
		}

		byte[] encoded = Base64.encodeBase64(DigestUtils.md5(saltedPass));

		return new String(encoded);
	}

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		String pass1 = "" + encPass;
		String pass2 = encodePassword(rawPass, salt);

		return pass1.equals(pass2);
	}

	public static void main(String[] args) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword("trainee", null);
		System.out.println("password===" + password);
	}
}