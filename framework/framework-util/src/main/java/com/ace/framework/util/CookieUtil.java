package com.ace.framework.util;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Convenience class for setting and retrieving cookies.
 * 
 * @author SHI Shaofei
 * @since 2011-11-16 14:55
 */
public class CookieUtil {

	private static final int MINUTE_SECONDS = 60;

	/**
	 * 
	 * @param name cookie名称
	 * @param value 值
	 * @param value
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value) {
		setCookie(response, name, value, "/", null);
	}

	/**
	 * 
	 * @param response
	 * @param name cookie名称
	 * @param value 值
	 * @param path 存储路径
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value, String path) {
		setCookie(response, name, value, path, null);
	}

	/**
	 * 
	 * @param response
	 * @param name cookie名称
	 * @param value 值
	 * @param path 存储路径
	 * @param age 分钟
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value, String path, String age) {

		setCookie(response, name, value, path, age, null);
	}

	/**
	 * 
	 * @param response
	 * @param name cookie名称
	 * @param value 值
	 * @param path 存储路径
	 * @param age 分钟
	 * @param domain 域
	 */
	public static void setCookie(HttpServletResponse response, String name,
			String value, String path, String age, String domain) {

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		if(StringUtils.isNotEmpty(domain)){
			cookie.setDomain(domain);
		}
		int ageValue = getAgeIntValue(age);
		if (ageValue >= 0) {
			cookie.setMaxAge(ageValue);
		}
		response.addCookie(cookie);
	}

	private static int getAgeIntValue(String age) {
		try {
			return Integer.valueOf(age) * MINUTE_SECONDS;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Convenience method to get a cookie by name
	 * 
	 * @param request
	 *            the current request
	 * @param name
	 *            the name of the cookie to find
	 * 
	 * @return the cookie (if found), null if not found
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];
			if (thisCookie.getName().equals(name)) {
				returnCookie = thisCookie;
				break;
			}
		}

		return returnCookie;
	}

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		if (cookie == null) {
			return null;
		}

		return cookie.getValue();
	}

	public static String getAllCookieNameAndValue(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		StringBuffer cs = new StringBuffer();

		for (int i = 0; i < cookies.length; i++) {
			cs.append(cookies[i].getName()).append("=")
					.append(cookies[i].getValue()).append(";");
		}
		return cs.toString();
	}


    public static void deleteCookie(HttpServletResponse response, Cookie cookie,String cookieDomain,String cookiePath) {
        if (cookie != null) {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge(0);
            cookie.setPath(cookiePath);
            cookie.setDomain(cookieDomain);
            response.addCookie(cookie);
        }
    }
	/**
	 * Convenience method to get the application's URL based on request
	 * variables.
	 */
	public static String getAppURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80))
				|| (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}
}
