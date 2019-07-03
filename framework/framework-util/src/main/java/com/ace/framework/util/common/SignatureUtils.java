package com.ace.framework.util.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtils {

	private SignatureUtils(){

	}
	/**
	 * 将验证签名的bean转成TreeMap
	 * @param obj javaBean
	 * @return TreeMap
	 */
	public static TreeMap<String, String> bean2TreeMap(Object obj) {
		BeanInfo beanInfo=null;
		TreeMap<String, String> data = new TreeMap<String, String>();
		//内省构造参数map
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass(),Object.class);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor pd:pds){
				if("sign".equals(pd.getName())||"signature".equals(pd.getName())){
					continue;
				}
				Object value = pd.getReadMethod().invoke(obj);
				if(value==null){
					continue;
				}
				data.put(pd.getName(), value.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return data;
		}
		return data;
	}
	
	/**
	 * 字典序md5,成功返回true,失败返回false
	 * @param sig 签名
	 * @param obj 验证obj除了signature的所有属性
	 * @return
	 */
	public static boolean checkSignature(Object obj,String sig,String keyStr){
		TreeMap<String, String> data = bean2TreeMap(obj);
		return checkSignature(sig,keyStr,data);
	}

	
	/**
	 * 字典序md5,成功返回true,失败返回false
	 * @param sig
	 * @param keyStr 附加字符串
	 * @param data
	 * @return
	 */
	public  static boolean checkSignature(String sig,String keyStr,TreeMap<String,String> data){
		if(sig==null){
			return false;
		}

    		return sig.equalsIgnoreCase(createMD5Signature(data, keyStr));
	}
	
	/**
	 * 签名计算
	 * @param data 请求参数
	 * @param keyStr 附加字符串
	 * @return
	 */
	public static String createMD5Signature(TreeMap<String, String> data,String keyStr) {

		String res = getLinkString(data);
		System.out.println(res+keyStr);
		if(keyStr!=null){
			return md5(res + keyStr);
		}
		return md5(res);
	}

	public static String getLinkString(Map<String, String> data) {
		StringBuilder strParam = new StringBuilder();

		for (String key : data.keySet()) {
			if (key.equals("signature"))
				continue;

			String value = data.get(key).toString();
			strParam.append("&").append(key).append("=").append(value);
		}
		return strParam.substring(1);
	}

	public static String md5(String s) {
		return encode(s,"MD5");
	}
	
	public static String SHA1(String s) {
		return encode(s,"SHA1");
	}
	
	public static final String encode(String string,String algorithm){
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
