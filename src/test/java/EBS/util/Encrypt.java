package EBS.util;

import utils.password.PasswordUtil;

public class Encrypt {
	public static void main(String[] args) {
		generateEncryptPassword();
	}

	public static void generateEncryptPassword() {
		PasswordUtil.generateEncryptPass();
	}
}
