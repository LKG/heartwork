package im.heart.core.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * 
 * @author gg
 * @desc 加密解密工具类
 */
public class DesUtils {

	/**
	 * 
	 * @功能说明：base64加密
	 * @param str
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static String createSign(String str, String sign) throws Exception {
		byte[] bytes = createSignback(str,sign);
		String base64 = Base64.encodeBase64String(bytes);
		return base64;

	}

	/**
	 * 
	 * @功能说明： base64解密
	 * @param entry
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static String parseSign(String entry, String sign) throws Exception {
		byte[] bytes = Base64.decodeBase64(entry);
		return parseSignback(bytes,sign);
	}

	/**
	 * 
	 * @功能说明：base64解密
	 * @param entry
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static String parseSignback(byte[] entry, String sign) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(sign.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		byte[] ret = cipher.doFinal(entry);
		String str = new String(ret, "UTF-8");
		return str;
	}

	/**
	 * 
	 * @功能说明：base64加密
	 * @param str
	 * @param sign
	 * @return
	 * @throws Exception
	 */
	public static byte[] createSignback(String str, String sign) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(sign.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		byte[] bytes = cipher.doFinal(str.getBytes());
		return bytes;

	}
}