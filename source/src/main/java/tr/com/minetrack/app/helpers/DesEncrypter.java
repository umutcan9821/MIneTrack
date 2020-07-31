package tr.com.minetrack.app.helpers;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import tr.com.minetrack.app.db.jdbc.PostgreSQL;

@SuppressWarnings("unused")
public class DesEncrypter {
	Cipher eCipher;
	Cipher dCipher;

	public DesEncrypter(SecretKey key) {
		try {
			eCipher = Cipher.getInstance("DES");
			dCipher = Cipher.getInstance("DES");
			eCipher.init(Cipher.ENCRYPT_MODE, key);
			dCipher.init(Cipher.DECRYPT_MODE, key);

		} catch (javax.crypto.NoSuchPaddingException e) {
		} catch (java.security.NoSuchAlgorithmException e) {
		} catch (java.security.InvalidKeyException e) {
		}
	}

	@SuppressWarnings({ "restriction", "hiding" })
	public String encrypt(String str) {
		try {
			byte[] utf8 = str.getBytes("UTF8");

			// Şifreleme
			byte[] enc = eCipher.doFinal(utf8);

			// Bytes dizisini BASE64 ile karakter dizisine çevir
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}

	@SuppressWarnings("restriction")
	public String decrypt(String str) {
		try {
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

			// Şifreyi çözme
			byte[] utf8 = dCipher.doFinal(dec);

			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}

	@SuppressWarnings({ "null", "unused" })
	public static void main(String[] args) {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			// SecretKey to String:
			// create new key
//	    SecretKey secretKey = KeyGenerator.getInstance("DES").generateKey();
			// get base64 encoded version of the key
//	    String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			// System.out.println(encodedKey);
			// String to SecretKey:
			// decode the base64 encoded string
			byte[] decodedKey = Base64.getDecoder().decode("aP2dg/Hfp8g=");
			// rebuild key using SecretKeySpec
			SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

			// Şifreleyecek objeyi yarat
			DesEncrypter encrypter = new DesEncrypter(originalKey);

			// Şifrele
			String encrypted = encrypter.encrypt("20/06/2021");

			// Şifreyi çöz
			String decrypted = encrypter.decrypt(encrypted);
			System.out.println("encrypted >>" + encrypted);

			System.out.println("decrypted >>" + decrypted);

			// rebuild key using SecretKeySpec
			originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

			// Şifreleyecek objeyi yarat
			encrypter = new DesEncrypter(originalKey);

			// Şifreyi çöz
			decrypted = encrypter.decrypt(encrypted);
			// System.out.println(decrypted);
		} catch (Exception e) {

		} finally {
			if (statement != null) {
				try {
					con.setAutoCommit(true);
				} catch (SQLException e3) {
					System.out.println("error in closing[delete method]");
				}
			}
		}
	}
}