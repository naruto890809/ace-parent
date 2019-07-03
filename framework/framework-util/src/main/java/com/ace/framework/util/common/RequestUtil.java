package com.ace.framework.util.common;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {

	private RequestUtil() {
	}
	
	public static void setAttribute(String key,Object value,HttpServletRequest request){
		request.setAttribute(key, value);
	}
	
	public static boolean isAjax(HttpServletRequest request){
		String requestType = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equalsIgnoreCase(requestType)&&!"ajax".equals(request.getParameter("ace"));
	}
	
	public static boolean fromAce(HttpServletRequest request){
		return "ajax".equals(request.getParameter("ace"));
	}
	
	public static String getRequestURL(HttpServletRequest request){
		//获取请求URI
        StringBuffer requestPage = request.getRequestURL(); 
        //获取参数
        String queryString = (request.getQueryString() == null ? "" : "?"+request.getQueryString()); 
        return requestPage+queryString;
	}
	
	public static String getIpAddr(HttpServletRequest request)  {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getParameter(String key,HttpServletRequest request){
		return request.getParameter(key);
	}
	public static String[] getParameterValues(String key,HttpServletRequest request){
		return request.getParameterValues(key);
	}
	
	public static Long getLongParameter(String key,HttpServletRequest request){
		try{
			String strVal = request.getParameter(key);
			return Long.parseLong(strVal);
		}catch(Exception e){
			return null;
		}
	}
	
	public static Integer getIntegerParameter(String key,HttpServletRequest request){
		try{
			String strVal = request.getParameter(key);
			return Integer.parseInt(strVal);
		}catch(Exception e){
			return null;
		}
	}
	
	public static Boolean getBooleanParameter(HttpServletRequest request,String key){
		try{
			String strVal = request.getParameter(key);
			if(strVal==null){
				return null;
			}
			return Boolean.parseBoolean(strVal.trim());
		}catch(Exception e){
			return null;
		}
	}
	
	public static Date getDateParameter(String key,String formart,HttpServletRequest request){
		try{
			String strVal = request.getParameter(key);
			if(strVal==null){
				return null;
			}
			return new SimpleDateFormat(formart).parse(strVal.trim());
		}catch(Exception e){
			return null;
		}
	}
	
	public static List<Long> getLongParameters(String key,HttpServletRequest request){
		List<Long> list = new ArrayList<Long>();
		String[] parameterValues = request.getParameterValues(key);
		if(parameterValues==null){
			return list;
		}
		for(String val: parameterValues){
			try{
				list.add(Long.parseLong(val));
			}catch(Exception e){
			}
		}
		return list;
	}
	
	public static List<String> getParameters(String key,HttpServletRequest request){
		List<String> list = new ArrayList<String>();
		String[] parameterValues = request.getParameterValues(key);
		if(parameterValues==null){
			return list;
		}
		for(String val: parameterValues){
			try{
				list.add(val);
			}catch(Exception e){
			}
		}
		return list;
	}

	public static String getPostData(HttpServletRequest request){
		try(InputStream in = request.getInputStream()){
			try(ByteArrayOutputStream out = new ByteArrayOutputStream(1024)){
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf))!=-1){
					out.write(buf,0,len);
				}
				return new String(out.toByteArray(),"UTF-8");
			}
		}catch (Exception e){

		}
		return "";
	}

}
