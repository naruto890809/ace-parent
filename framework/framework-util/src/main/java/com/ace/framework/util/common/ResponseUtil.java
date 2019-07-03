package com.ace.framework.util.common;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ResponseUtil {
	
	public final static String CONTENT_TYPE_HTML = "text/html";
	public final static String CONTENT_TYPE_XML = "text/xml";
	public final static String CONTENT_TYPE_JSON = "application/json";
	public final static String CONTENT_TYPE_SCRIPT = "application/x-javascript";
	public final static String CONTENT_TYPE_FILE = "multipart/form-data";
	public final static String CHARSET = ";charset=";
	public final static String KEY_CHARSET = "UTF-8";
	
	private ResponseUtil() {
	}

	public static String sendRedirect(HttpServletResponse response,String url){
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String setContentType(HttpServletResponse response,String contentType){
		response.setContentType(contentType);
		return null;
	}
	
	public static String setContentTypeAndCharset(HttpServletResponse response,String contentType,String charset){
		return setContentType(response, contentType+CHARSET+charset);
	}
	
	public static String setContentTypeDefaultCharset(HttpServletResponse response,String contentType){
		String charset = KEY_CHARSET;
		setContentType(response, contentType+CHARSET+charset);
		response.setCharacterEncoding(charset);
		return null;
	}

	
	public static String write(HttpServletResponse response, String text){
		if(text==null){
			text = "";
		}
		try{
            PrintWriter writer = response.getWriter();
			writer.write(text);
		} catch (IOException e) {
		}
		return null;
	}
	
	public static String writeHtml(HttpServletResponse response,String text){
		setContentTypeDefaultCharset(response,ResponseUtil.CONTENT_TYPE_HTML);
		return write(response, text);
	}
	
	public static String writeXml(HttpServletResponse response,String text){
		setContentTypeDefaultCharset(response,ResponseUtil.CONTENT_TYPE_XML);
		return write(response, text);
	}

	
	public static String writeJson(HttpServletResponse response,String text){
		setContentTypeDefaultCharset(response,ResponseUtil.CONTENT_TYPE_JSON);
		return write(response, text);
	}
	
	/*public static HttpServletResponse writeJson(JSONObject json){
		return write(setContentTypeDefaultCharset(ResponseUtil.CONTENT_TYPE_JSON), json.toString());
	}*/
	
	public static String writeScript(HttpServletResponse response,String text){
		setContentTypeDefaultCharset(response,ResponseUtil.CONTENT_TYPE_SCRIPT);
		return write(response, text);
	}
	
	public static String writeFile(HttpServletResponse response,File file,String fileName){
		ResponseUtil.setContentTypeDefaultCharset(response,CONTENT_TYPE_FILE);
		try{
			response.setHeader("Content-Disposition", "attachment;fileName="+new String(fileName.getBytes(KEY_CHARSET),"iso-8859-1"));
            InputStream inputStream=new FileInputStream(file);
            OutputStream os=response.getOutputStream();
            byte[] b=new byte[1024];
            int length;
            while((length=inputStream.read(b))>0){
                os.write(b,0,length);
            }
		}catch(Exception e){
			
		}
        return null;
	}
	
	public static HttpServletResponse redirect2Url(HttpServletResponse response,String url){
        response.setStatus(301); 
        response.setHeader("Location", url); 
        response.setHeader("Pragma","No-cache"); 
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
		return response;
	}
}
