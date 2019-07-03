/**
 * Copyright (c) 2005-2009 springside.org.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Struts2Utils.java 624 2009-11-05 13:31:47Z calvinxiu $
 */
package com.ace.framework.base;

import com.ace.framework.util.common.RequestUtil;
import com.ace.framework.util.fastJson.FastJsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Struts2 Utils类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * @author WuZhiWei
 * @since 2015-11-03 17:24:43
 */
public class Struts2Utils {

	// -- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";
	private static Log logger = LogFactory.getLog(Struts2Utils.class);

	// -- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 * 
	 * @return session
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化函数.
	 * 
	 * @return request
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpResponse的简化函数.
	 * 
	 * @return response
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 取得Request Parameter的简化方法.
	 * 
	 * @return 参数
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content,final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName,
						NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName
							+ "Is not a valid header type");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				response.setHeader("Pragma", "No-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
			}
			logger.debug(content);
			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
    public static void render(final String contentType, final String content,final String callback,final String... headers) {
        try {
            // 分析headers参数
            String encoding = ENCODING_DEFAULT;
            boolean noCache = NOCACHE_DEFAULT;
            for (String header : headers) {
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");

                if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
                    encoding = headerValue;
                } else if (StringUtils.equalsIgnoreCase(headerName,
                        NOCACHE_PREFIX)) {
                    noCache = Boolean.parseBoolean(headerValue);
                } else
                    throw new IllegalArgumentException(headerName
                            + "Is not a valid header type");
            }

            HttpServletResponse response = ServletActionContext.getResponse();

            // 设置headers参数
            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if (noCache) {
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
            }
            logger.debug(content);

            if(StringUtils.isNotBlank(callback)){
                response.getWriter().write(callback+"("+content+")");
            }else{
                response.getWriter().write(content);
            }
            response.getWriter().flush();

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString,
			final String... headers) {
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object,String callback) {
		renderJson(object, callback,false);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object,final String callback, boolean ignoreNull) {
//		String jsonString = JsonUtil.obj2Json(object, ignoreNull);
        HashMap<Class<?>, String[]> excludes = new HashMap<Class<?>, String[]>();
        excludes.put(BaseEntity.class,new String[]{"createBy","lastModifyTime","lastModifyBy"});
        String jsonString = FastJsonUtil.obj2Json(object,excludes);
        //

        render(JSON_TYPE,jsonString,callback,new String[]{});
	}
    /**
     * 直接输出JSON.
     *
     * @param object
     *            Java对象,将被转化为json字符串.
     * @see #render(String, String, String...)
     */
    public static void renderJson(final Object object, HashMap<Class<?>, String[]> excludes) {
        String jsonString = FastJsonUtil.obj2Json(object,excludes);
        render(JSON_TYPE, jsonString);
    }
	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param contentMap
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("rawtypes")
	public static void renderJsonp(final String callbackName,
			final Map contentMap, final String... headers) {
		String jsonParam = FastJsonUtil.obj2Json(contentMap,null);
		StringBuilder result = new StringBuilder().append(callbackName)
				.append("(").append(jsonParam).append(");");

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{content:'Hello World!!!'}");
		render(JS_TYPE, result.toString(), headers);
	}
}
