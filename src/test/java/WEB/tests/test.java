package WEB.tests;

import utils.password.PasswordUtil;

public class test {

	public static void main(String[] args) {
	

		
		PasswordUtil.generateEncryptPass();
		System.out.print(PasswordUtil.decryptPassword("A0EBA344FE443EEFCBC8C462E60E340E"));

	}
}

