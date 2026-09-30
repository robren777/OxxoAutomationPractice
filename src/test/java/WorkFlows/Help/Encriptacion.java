package WorkFlows.Help;

import utils.password.PasswordUtil;

public class Encriptacion {

	public static void main(String[] ar) {

		encriptar();

		// desencriptar("");

	}

	public static void encriptar() {
		PasswordUtil.generateEncryptPass();
	}

	public static void desencriptar(String texto) {
		System.out.println(PasswordUtil.decryptPassword(texto));
	}
}
