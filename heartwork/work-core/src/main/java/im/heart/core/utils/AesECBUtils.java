package im.heart.core.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesECBUtils {
	public static final String KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM_PKCS5 = "AES/ECB/PKCS5Padding";
	private static final String CIPHER_ALGORITHM_NOPADDING = "AES/ECB/NoPadding";
	public enum KeySize {
		ks16(16), ks24(24), ks32(32);
		private int value;

		KeySize(int value) {
			this.value = value;
		}
	}

	public static byte[] encryptNoPadding(byte[] content, String passwd){
		return encryptNoPadding(content,passwd,KeySize.ks16);
	}
	public static String encryptNoPaddingBase64(byte[] content, String passwd) {
		return encryptNoPaddingBase64(content,passwd,KeySize.ks16);
	}
	public static byte[] decryptNoPadding(byte[] content, String passwd) {
		return decryptNoPadding(content,passwd,KeySize.ks16);
	}
	/**
	 * AES加密
	 * 
	 * @param content
	 * @param passwd
	 * @param ks
	 *
	 * @return
	 */
	public static byte[] encryptNoPadding(byte[] content, String passwd,
			KeySize ks) {
		try {
			Cipher aesECB = Cipher.getInstance(CIPHER_ALGORITHM_NOPADDING);
			byte[] zeroPassword = zeroPadding(passwd.getBytes(), ks);
			SecretKeySpec key = new SecretKeySpec(zeroPassword, KEY_ALGORITHM);
			aesECB.init(Cipher.ENCRYPT_MODE, key);
			int mod = content.length%16;
			if(mod!=0){// 内容补零
				int copyLen = content.length+16-mod;
				byte[] out = new byte[copyLen];
				System.arraycopy(content, 0, out, 0, content.length);
				return aesECB.doFinal(out);
			}
			return aesECB.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static byte[] decryptNoPadding(byte[] content, String passwd, KeySize ks) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_NOPADDING);// 创建密码器
			byte[] zeroPassword = zeroPadding(passwd.getBytes(), ks);
			SecretKeySpec key = new SecretKeySpec(zeroPassword, KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			return cipher.doFinal(content); // 解密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static byte[] decryptNoPaddingBase64(String content, String passwd,
			KeySize ks) throws IOException {
		byte[] deBytes = Base64.decodeBase64(content);
		return decryptNoPadding(deBytes, passwd, ks);
	}
	
	public static String encryptNoPaddingBase64(byte[] content, String passwd,
			KeySize ks) {
		byte[] bytes = encryptNoPadding(content, passwd, ks);
		if (bytes != null) {
			return Base64.encodeBase64String(bytes);
		}
		return null;
	}

	/**
	 * AES加密
	 * 
	 * @param content
	 * @param passwd
	 * @param ks
	 *
	 * @return
	 */
	public static byte[] encryptPKCS5(byte[] content, String passwd, KeySize ks) {
		try {
			Cipher aesECB = Cipher.getInstance(CIPHER_ALGORITHM_PKCS5);
			// 处理密钥
			byte[] zeroPassword = zeroPadding(passwd.getBytes(), ks);
			SecretKeySpec key = new SecretKeySpec(zeroPassword, KEY_ALGORITHM);
			aesECB.init(Cipher.ENCRYPT_MODE, key);
			return aesECB.doFinal(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 * @param passwd
	 * @param ks
	 *
	 * @return
	 */
	public static byte[] decryptPKCS5(byte[] content, String passwd, KeySize ks) {
		try {
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_PKCS5);// 创建密码器
			byte[] zeroPassword = zeroPadding(passwd.getBytes(), ks);
			SecretKeySpec key = new SecretKeySpec(zeroPassword, KEY_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			return cipher.doFinal(content); // 解密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密，先Base64解密，在进行AES解密
	 * 
	 * @param content
	 * @param passwd
	 * @param ks
	 *
	 * @return
	 *
	 * @throws IOException
	 */
	public static byte[] decryptPKCS5Base64(String content, String passwd,
			KeySize ks) throws IOException {
		byte[] deBytes = Base64.decodeBase64(content);
		return decryptPKCS5(deBytes, passwd, ks);
	}

	/**
	 * AES加密后生成Base64二次加密
	 * 
	 * @param content
	 * @param passwd
	 * @param ks
	 *
	 * @return
	 */
	public static String encryptPKCS5Base64(byte[] content, String passwd,
			KeySize ks) {
		byte[] bytes = encryptPKCS5(content, passwd, ks);
		if (bytes != null) {
			return Base64.encodeBase64String(bytes);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		String content = "我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好我很好";
		String key = "1234567899999999999999999999999999999999999999";
		// System.out.println(Util.byte2hex(AES.encrypt(content.getBytes("utf-8"),
		// key, KeySize.ks16)));
		// System.out.println(new
		// String(AES.decrypt(Util.hex2byte("0da244f228acb14d6f9c02aac527dc52"),
		// key, KeySize.ks16),"utf-8"));
		String eb = encryptPKCS5Base64(content.getBytes("utf-8"), key,KeySize.ks16);
		System.out.println(eb);
		System.out.println(new String(decryptPKCS5Base64(eb, key, KeySize.ks16), "utf-8"));
		 String eb2 = encryptNoPaddingBase64(content.getBytes("utf-8"), key,KeySize.ks16);
		 System.out.println(eb2);
		System.out.println(new String(decryptNoPaddingBase64(eb2, key, KeySize.ks16)));
		 
		// System.out.println(Util.byte2hex(eb.getBytes()));

	}


	/**
	 * 密码自动补全
	 * 
	 * @param in
	 * @param ks
	 *            如果密码的长度不是16,24,32，则自动补全（二进制0）
	 *
	 * @return
	 */
	private static byte[] zeroPadding(byte[] in, KeySize ks) {
		Integer copyLen = in.length;
		if (copyLen > ks.value) {
			copyLen = ks.value;
		}
		byte[] out = new byte[ks.value];
		System.arraycopy(in, 0, out, 0, copyLen);
		return out;
	}
}