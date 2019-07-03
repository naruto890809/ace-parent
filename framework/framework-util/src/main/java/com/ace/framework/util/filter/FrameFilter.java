package com.ace.framework.util.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;

public class FrameFilter extends AbstractFilter {
	private static final String TBC_HEAD = "<!--TBC.HEAD-->";
	private String headText = "";
	private String footText = "";
	private boolean enabled = true;
	private int minutesBetweenRequests = 1;
	volatile boolean destroyed = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		final String baseUrl = paramMap.get("baseUrl");
		if (StringUtils.isEmpty(baseUrl)) {
			System.err
					.println("Can't find baseUrl for frameFilter; skip the filtering.");
			return;
		}

		setHeadText(fetchContent(baseUrl + "/head.ftl"));

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!destroyed) {
					String fetchContent = fetchContent(baseUrl + "/head.ftl");
					if (isValidResultContent(fetchContent)) {
						setHeadText(fetchContent);
					}
					synchronized (this) {
						try {
							wait(minutesBetweenRequests * 60000);
						} catch (InterruptedException e) {
							e.printStackTrace();// do nothing.
						}

					}
				}

			}
		}).start();

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (!isEnabled()) {
			chain.doFilter(request, response);
			return;
		}

		final HttpServletResponse originalResponse = (HttpServletResponse) response;
		HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(
				originalResponse) {

			PrintWriter proxyWriter;

			ServletOutputStream proxyOutputStream;
			boolean first = true;

			@Override
			public ServletOutputStream getOutputStream() throws IOException {

				if (proxyOutputStream == null) {
					final ServletOutputStream targetOutputStream = super
							.getOutputStream();
					proxyOutputStream = new ServletOutputStream() {

						public void write(int b) throws IOException {

							if (isEnabled()) {
								if (first) {
									targetOutputStream.print(headText);
									first = false;
								}
							}
							targetOutputStream.write(b);
						}

						@Override
						public void close() throws IOException {
							super.close();
						}

					};
				}
				return proxyOutputStream;

			}

			@Override
			public void setContentLength(int len) {
				if (isEnabled()) {
					super.setContentLength(len + headText.length());
				} else {

					super.setContentLength(len);
				}
			}

			@Override
			public PrintWriter getWriter() throws IOException {
				if (proxyWriter == null) {
					final PrintWriter targetWriter = super.getWriter();
					proxyWriter = new PrintWriter(targetWriter) {

						@Override
						public void write(char[] arg0, int arg1, int arg2) {
							if (isEnabled()) {
								if (first) {
									targetWriter.print(headText);
									first = false;
								}
							}
							targetWriter.write(arg0, arg1, arg2);
						}

						@Override
						public void close() {
							targetWriter.print(footText);
							super.close();

						}

					};
				}
				return proxyWriter;
			}

		};
		chain.doFilter(request, responseWrapper);

	}

	@Override
	public void destroy() {
		destroyed = true;
	}

	public String fetchContent(String url2) {
		HttpURLConnection urlConnection = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;

		try {
			URL url = new URL(url2);
			urlConnection = ((HttpURLConnection) url.openConnection());

			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(false);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);

			inputStream = urlConnection.getInputStream();
			String resultContent = getResultContent(inputStream);
			if (StringUtils.isEmpty(resultContent)) {
				return "";
			}

			if (!resultContent.startsWith(TBC_HEAD)) {
				return "";
			}

			return resultContent;

		} catch (Exception e) {
			return ""; // Return empty.
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
					// ignore.
				}
			}

			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
					// ignore.
				}
			}

			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

	}

	protected boolean isValidResultContent(String resultContent) {
		if (StringUtils.isEmpty(resultContent)) {
			return false;
		}

		if (!resultContent.startsWith(TBC_HEAD)) {
			return false;
		}

		return true;
	}

	protected String getResultContent(InputStream inputStream)
			throws IOException {

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		LineNumberReader lineNumberReader = new LineNumberReader(
				inputStreamReader);
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = lineNumberReader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		String result = sb.toString();
		return result;

	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getHeadText() {
		return headText;
	}

	public void setHeadText(String headText) {
		this.headText = headText;
	}

	public String getFootText() {
		return footText;
	}

	public void setFootText(String footText) {
		this.footText = footText;
	}

	public int getMinutesBetweenRequests() {
		return minutesBetweenRequests;
	}

	public void setMinutesBetweenRequests(int minutesBetweenRequests) {
		this.minutesBetweenRequests = minutesBetweenRequests;
	}
}
