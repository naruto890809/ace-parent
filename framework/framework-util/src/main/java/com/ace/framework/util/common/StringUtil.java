package com.ace.framework.util.common;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.*;

public class StringUtil {

	private StringUtil(){

	}
	/**
	 * str==null||str.trim().length()==0
	 * @param str 待判断的字符串
	 * @return boolean
	 */
	public static boolean isNull(String str){
		return str==null||str.trim().length()==0;
	}
	
	/**
	 * 将字符串数组用逗号连接','  除去空字符串
	 * @param arr
	 * @return
	 */
	public static String arr2str(String[] arr){
		StringBuilder buf = new StringBuilder();
		if(arr==null){
			return "";
		}
		for(String str:arr){
			buf.append(","+str);
		}
		if(buf.length()>1){
			return buf.substring(1);
		}
		return "";
	}


	/**
	 * 将字符串转换成String boolean integer long
	 * @param str 参数
	 * @param clazz 转换的类型
	 * @param <T> 泛型
	 * @return 转换后的数据
	 */
	public static<T> T parse(String str,Class<T> clazz){
		if (str == null || clazz == null) {
			return null;
		}
		String className = clazz.getName();
		if (className.equals("java.lang.String")) {
			return (T) str;
		} else if (className.equals("java.lang.Boolean")) {
			return (T) Boolean.valueOf(str);
		} else if (className.equals("java.lang.Integer")) {
			return (T) new Integer(str);
		}else if (className.equals("java.lang.Long")) {
			return (T) new Long(str);
		}
		return null;
	}


	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 * @param str 待编码的字符串
	 * @return 16位string
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 * @param bytes 待解码的16位字符串
	 * @return 解码后的字符串
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static String getRandomNumberStr(int length){
		return RandomStringUtils.randomNumeric(length);
	}

	public static String getRandomNumberStr(int min,int max){
		Random random = new Random();
		return (random.nextInt(max)%(max-min+1) + min)+"";
	}

	public static String toNumber(String str){
		String reg = "[a-zA-Z]";
		StringBuffer strBuf = new StringBuffer();
		str = str.toLowerCase();
		if (null != str && !"".equals(str)) {
			for (char c : str.toCharArray()) {
				if (String.valueOf(c).matches(reg)) {
					strBuf.append(c - 86);
				} else {
					strBuf.append((int)c);
				}
			}
			return strBuf.toString();
		} else {
			return str;
		}
	}

	/**

	 * 使用gzip进行压缩
	 */
	public static String gzip(String primStr) {
		if (primStr == null || primStr.length() == 0) {
			return primStr;
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		GZIPOutputStream gzip=null;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(primStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(gzip!=null){
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		return new sun.misc.BASE64Encoder().encode(out.toByteArray());
	}

	/**
	 *
	 * <p>Description:使用gzip进行解压缩</p>
	 * @param compressedStr
	 * @return
	 */
	public static String gunzip(String compressedStr){
		if(compressedStr==null){
			return null;
		}

		ByteArrayOutputStream out= new ByteArrayOutputStream();
		ByteArrayInputStream in=null;
		GZIPInputStream ginzip=null;
		byte[] compressed=null;
		String decompressed = null;
		try {
			compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			in=new ByteArrayInputStream(compressed);
			ginzip=new GZIPInputStream(in);

			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = ginzip.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed=out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ginzip != null) {
				try {
					ginzip.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

	/**
	 * 使用zip进行压缩
	 * @param str 压缩前的文本
	 * @return 返回压缩后的文本
	 */
	public static final String zip(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;
		String compressedStr = null;
		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
			compressedStr = new sun.misc.BASE64Encoder().encodeBuffer(compressed);
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressedStr;
	}

	/**
	 * 使用zip进行解压缩
	 * @param compressed 压缩后的文本
	 * @return 解压后的字符串
	 */
	public static final String unzip(String compressedStr) {
		if (compressedStr == null) {
			return null;
		}

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed = null;
		try {
			byte[] compressed = new sun.misc.BASE64Decoder().decodeBuffer(compressedStr);
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}
}
