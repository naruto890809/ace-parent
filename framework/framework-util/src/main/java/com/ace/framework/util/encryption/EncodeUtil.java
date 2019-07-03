package com.ace.framework.util.encryption;

import java.security.MessageDigest;

public class EncodeUtil {

	private EncodeUtil(){

	}
	/**
	 * MD5加密，默认大写
	 * 
	 * @param
	 *            s 需要加密字符串
	 * @return String 加密后字符串
	 */
	public static String md5(String s) {
		return encode(s,"MD5");
	}
	
	/**
	 * SHA1加密 加密后大写字符串
	 * @param s 需要加密字符串
	 * @return String 加密后字符串
	 */
	public static String sha1(String s) {
		return encode(s,"SHA1");
	}
	
	private static final String encode(String string,String algorithm){
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = string.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance(algorithm);
			mdInst.update(btInput);
			
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
}
