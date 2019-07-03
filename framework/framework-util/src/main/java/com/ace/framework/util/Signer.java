package com.ace.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signer {

	private static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String calculateSign(String action, String secret, String corpCode, String usernName,
			 Long timestamp) {
		String signingText = secret + "|"+ action + "|" + corpCode + "|" + usernName + "|" + timestamp + "|" + secret;

		return Signer.md5(signingText);
	}

	public static String md5(String input) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// impossible to be here.
			e1.printStackTrace();
		}
		try {
			md.update(input.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// impossible to be here.
			e.printStackTrace();
		}
		byte[] byteDigest = md.digest();

		return toHexString(byteDigest);
	}

	public static String toHexString(byte[] byteDigest) {
		char[] chars = new char[byteDigest.length * 2];
		for (int i = 0; i < byteDigest.length; i++) {
			// left is higher.
			chars[i * 2] = HEX_DIGITS[byteDigest[i] >> 4 & 0x0F];
			// right is lower.
			chars[i * 2 + 1] = HEX_DIGITS[byteDigest[i] & 0x0F];
		}
		return new String(chars);
	}

	public static void main(String... args){
		String secretMd5 = Signer.md5("hello");//Signer.calculateSign("sso", "abc", "21tb", "admin", 1342433828436L);
		System.out.println(secretMd5);
	}

}
