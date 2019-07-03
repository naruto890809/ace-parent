package com.ace.framework.util.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public abstract class AbstractFilter implements Filter {
	/**
	 * If the paramMap is null, means this filter hasn't been properly
	 * initialized yet by invoking super.init(filterConfig).
	 */
	protected Map<String, String> paramMap;
	protected WebApplicationContext webApplicationContext;

	@SuppressWarnings("unchecked")
	@Override
	/*
	 * Must be invoked as super.init(filterConfig) to be fully initialized.
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		String filterName = filterConfig.getFilterName();
		this.webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		paramMap = (Map<String, String>) webApplicationContext
				.getBean(filterName + "Map");

		if (paramMap == null) {
			paramMap = new HashMap<String, String>(); // empty map.
		}
	}

	public String getParam(String propertyName) {
		Assert.notNull(propertyName, "propertyName can't be null");
		try {
			return paramMap.get(propertyName);
		} catch (NullPointerException e) {
			throw new NullPointerException(
					"paramMap is null, means this filter hasn't been properly initialized yet by invoking super.init(filterConfig).");
		}
	}

	public boolean getBooleanParam(String propertyName, boolean defaultValue) {
		String paramValue = getParam(propertyName);
		if (paramValue == null) {
			return defaultValue;
		}

		return Boolean.parseBoolean(paramValue);

	}

	public int getIntegerParam(String propertyName, int defaultValue) {
		String paramValue = getParam(propertyName);
		if (paramValue == null) {
			return defaultValue;
		}

		return Integer.parseInt(paramValue);

	}
	
	public long getLongParam(String propertyName, long defaultValue) {
		String paramValue = getParam(propertyName);
		if (paramValue == null) {
			return defaultValue;
		}
		
		return Long.parseLong(paramValue);
		
	}

	public String getStringParam(String propertyName, String defaultValue) {
		String paramValue = getParam(propertyName);
		if (paramValue == null) {
			return defaultValue;
		}

		return paramValue;

	}

	public WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public void setWebApplicationContext(
			WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

}
