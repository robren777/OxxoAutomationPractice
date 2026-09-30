package RDM.utils.password;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PasswordUtil {

	private String key;
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);
	
	public PasswordUtil() {
		key = "949CB93BA1DA5170B8464DE9C97A002D";
	}
	
	public PasswordUtil(String key) {
		this.key = key;
	}
	
	public static String generateKey() {
		String key = null;
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance(GenerateKey.AES);
	        keyGen.init(128);
	        SecretKey sk = keyGen.generateKey();
	        key = GenerateKey.byteArrayToHexString(sk.getEncoded());
		} catch (Exception e) {
			logger.error("Failed to generate key", e);
		}
        return key;
	}
	
	public String encryptPass(String pass) {
		String encryptedpwd = null;
		try {
			byte[] bytekey = GenerateEncryptionPassword.hexStringToByteArray(key);
	        SecretKeySpec sks = new SecretKeySpec(bytekey, GenerateEncryptionPassword.AES);
	        Cipher cipher = Cipher.getInstance(GenerateEncryptionPassword.AES);
	        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
	        byte[] encrypted = cipher.doFinal(pass.getBytes());
	        encryptedpwd = GenerateEncryptionPassword.byteArrayToHexString(encrypted);
		} catch (Exception e) {
			logger.error("Failed to encrypt password", e);
		}
		return encryptedpwd;
	}
	
	public String decryptPass(String encryptPass) {
		String decryptPass = null;
		try {
			 byte[] bytekey = GeneratePlainPassword.hexStringToByteArray(key);
	        SecretKeySpec sks = new SecretKeySpec(bytekey, GeneratePlainPassword.AES);
	        Cipher cipher = Cipher.getInstance(GeneratePlainPassword.AES);
	        cipher.init(Cipher.DECRYPT_MODE, sks);
	        byte[] decrypted = cipher.doFinal(GeneratePlainPassword.hexStringToByteArray(encryptPass));
	        decryptPass = new String(decrypted);
		} catch (Exception e) {
			logger.error("Failed to decrypt password", e);
		}
		return decryptPass;
	}
	
	
	public static void generateEncryptPass() {
		String pass = JOptionPane.showInputDialog("Inserte la contrase�a a encriptar");
		PasswordUtil p = new PasswordUtil();
		
		String encryptPass = p.encryptPass(pass);
		
		new PassMessagePane(encryptPass);
	}
	
	public static String decryptPassword(String encryptPass) {
		PasswordUtil p = new PasswordUtil();
		
		String decryptPass = p.decryptPass(encryptPass);
		
		return decryptPass;
	}
	
	
}
