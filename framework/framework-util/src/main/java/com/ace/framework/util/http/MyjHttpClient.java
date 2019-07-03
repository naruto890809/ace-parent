package com.ace.framework.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

public class MyjHttpClient implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8473243144381618739L;
	
	public final static Logger logger = Logger.getLogger(MyjHttpClient.class);
	
	private static CookieStore cookieStore = null;

	public static CookieStore getCookieStore() {
		return cookieStore;
	}

	public static void setCookieStore(CookieStore cookieStore) {
		MyjHttpClient.cookieStore = cookieStore;
	}

	/**
	 * 
	 * @param httpclient
	 * @param url
	 *            请求地址
	 * @param headers
	 *            请求头
	 * @param params
	 *            post 参数
	 * @param charset
	 *            编码格式 默认utf-8
	 * @return
	 */
	public static String getPostText(CloseableHttpClient httpclient,
			String url, Map<String, String> headers,
			Map<String, String> params, String charset) {
		if (charset == null || "".equals(charset)) {
			charset = "UTF-8";
		}

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		String text = "";
		if (httpclient == null) {
			httpclient = HttpClients.createDefault();
		}

		if (headers != null) {
			Iterator<Map.Entry<String, String>> headersiterator = headers
					.entrySet().iterator();
			while (headersiterator.hasNext()) {
				Map.Entry<String, String> entry = headersiterator.next();
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		if (params != null) {
			Iterator<Map.Entry<String, String>> paramsiterator = params.entrySet().iterator();
			while (paramsiterator.hasNext()) {
				Map.Entry<String, String> entry = paramsiterator.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

			HttpClientContext localContext = HttpClientContext.create();
			cookieStore = new BasicCookieStore();
			localContext.setCookieStore(cookieStore);
			try {
				response = httpclient.execute(httpPost, localContext);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 

			text = in2Text(response.getEntity().getContent(), charset);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpPost.abort();
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}
	
	
	
	public static String getGetTextCookies(String url, Map<String, String> headers, String charset)  {
		
		//超星配置
		
		String text = null;
		CloseableHttpClient client = null;
		if (cookieStore == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}

		HttpGet httpGet = new HttpGet(url);
		
		//设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*30).setConnectTimeout(1000*30).build();
		httpGet.setConfig(requestConfig);
		
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		try {
			
			HttpClientContext localContext = HttpClientContext.create();
			cookieStore = new BasicCookieStore();
			localContext.setCookieStore(cookieStore);
			
			HttpResponse httpResponse = client.execute(httpGet,localContext);
			text = in2Text(httpResponse.getEntity().getContent(), charset);
		} catch (TimeoutException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return text;
	}
	

	public static String getPostTextNoC(CookieStore cookieStore_, String url,
			Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = null;
		if (cookieStore_ == null) {
			httpclient = HttpClients.createDefault();
		} else {
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore_).build();
		}

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		String text = "";

		if (headers != null) {
			Iterator<Map.Entry<String, String>> headersiterator = headers
					.entrySet().iterator();
			while (headersiterator.hasNext()) {
				Map.Entry<String, String> entry = headersiterator.next();
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		if (params != null) {
			Iterator<Map.Entry<String, String>> paramsiterator = params
					.entrySet().iterator();
			while (paramsiterator.hasNext()) {
				Map.Entry<String, String> entry = paramsiterator.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}

		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			
			//设置请求和传输超时时间
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*60).setConnectTimeout(1000*60).build();
			httpPost.setConfig(requestConfig);
			
			response = httpclient.execute(httpPost);

			text = in2Text(response.getEntity().getContent(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpPost.abort();
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	public static String getPostText(CookieStore cookieStore_, String url,
			Map<String, String> headers, Map<String, String> params) {
		CloseableHttpClient httpclient = null;
		if (cookieStore_ == null) {
			httpclient = HttpClients.createDefault();
		} else {
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore_).build();
		}

		HttpPost httpPost = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		String text = "";

		if (headers != null) {
			Iterator<Map.Entry<String, String>> headersiterator = headers
					.entrySet().iterator();
			while (headersiterator.hasNext()) {
				Map.Entry<String, String> entry = headersiterator.next();
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}

		if (params != null) {
			Iterator<Map.Entry<String, String>> paramsiterator = params
					.entrySet().iterator();
			while (paramsiterator.hasNext()) {
				Map.Entry<String, String> entry = paramsiterator.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}

		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

			HttpClientContext localContext = HttpClientContext.create();
			cookieStore = new BasicCookieStore();
			localContext.setCookieStore(cookieStore);
			response = httpclient.execute(httpPost, localContext);
			 

			text = in2Text(response.getEntity().getContent(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpPost.abort();
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}

	public static String getPostBody(CookieStore cookieStore_, String url,
			Map<String, String> headers, String body) {
		CloseableHttpClient httpclient = null;
		if (cookieStore_ == null) {
			httpclient = HttpClients.createDefault();
		} else {
			httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore_).build();
		}
		
		HttpPost httpPost = new HttpPost(url);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		
		String text = "";
		
		if (headers != null) {
			Iterator<Map.Entry<String, String>> headersiterator = headers
					.entrySet().iterator();
			while (headersiterator.hasNext()) {
				Map.Entry<String, String> entry = headersiterator.next();
				httpPost.setHeader(entry.getKey(), entry.getValue());
			}
		}
		

		
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new StringEntity(body, "UTF-8"));
			
			HttpClientContext localContext = HttpClientContext.create();
			cookieStore = new BasicCookieStore();
			localContext.setCookieStore(cookieStore);
			response = httpclient.execute(httpPost, localContext);
			
			
			text = in2Text(response.getEntity().getContent(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			httpPost.abort();
			try {
				response.close();
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text;
	}


	public static String getGetText(CookieStore cookieStore, String url,
			Map<String, String> headers, String charset)  {
		
		//超星配置
		
		String text = null;
		CloseableHttpClient client = null;
		if (cookieStore == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}
		 

		HttpGet httpGet = new HttpGet(url);
		 
		//设置请求和传输超时时间
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*120).setConnectTimeout(1000*120).build();
//		httpGet.setConfig(requestConfig);
		
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		try {
			HttpResponse httpResponse = client.execute(httpGet);
			try {
				text = in2Text(httpResponse.getEntity().getContent(), charset);
			} catch (Exception e) {
//				text = in2Text(new GzipDecompressingEntity(httpResponse.getEntity()).getContent(), charset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return text;
	}
	

	public static String getGetTextException(CookieStore cookieStore, String url,
			Map<String, String> headers, String charset) throws Exception {
		
		//超星配置
		
		String text = null;
		CloseableHttpClient client = null;
		if (cookieStore == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}

		HttpGet httpGet = new HttpGet(url);
		
		//设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000*120).setConnectTimeout(1000*120).build();
		httpGet.setConfig(requestConfig);
		
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		
		HttpResponse httpResponse = client.execute(httpGet);
		text = in2Text(httpResponse.getEntity().getContent(), charset);
		
		
		return text;
	}
	
	

	public static String getLocation(CookieStore cookieStore, String url,
			Map<String, String> headers, String charset) {

		String text = null;
		CloseableHttpClient client = null;
		if (cookieStore == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}

		HttpGet httpGet = new HttpGet(url);
	    //httpclient-4.4   禁止浏览器自动跳转  获取302状态路径
		RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
	    httpGet.setConfig(requestConfig);
	    
	    
	    
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
	 
		HttpContext localContext = new BasicHttpContext();
		
		try {
			HttpResponse httpResponse = client.execute(httpGet,localContext);
			
			Header hearder = httpResponse.getFirstHeader("Location");
			if (hearder == null) {
				return null;
			}else{
				return hearder.getValue();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return text;
	}
	

	public static String getLocationNew(CookieStore cookieStore, String url,
			Map<String, String> headers, String charset) {

		String text = null;
		CloseableHttpClient client = null;
		if (cookieStore == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}

		HttpGet httpGet = new HttpGet(url);
	    //httpclient-4.4   禁止浏览器自动跳转  获取302状态路径
//		RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
//	    httpGet.setConfig(requestConfig);
	    
	    
	    
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
	 
		HttpContext localContext = new BasicHttpContext();
		
		try {
			 HttpResponse httpResponse = client.execute(httpGet,localContext);
			 HttpHost target = (HttpHost) localContext.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			 HttpUriRequest req = (HttpUriRequest) localContext.getAttribute(ExecutionContext.HTTP_REQUEST);
			 return target.toString() + req.getURI();
			/*Header hearder = httpResponse.getFirstHeader("Location");
			if (hearder == null) {
				return null;
			}else{
				return hearder.getValue();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return text;
	}
	
	public static String getLocationLoop(CookieStore cookieStore, String url,
			Map<String, String> headers, String charset) {

		CloseableHttpClient client = null;
		if (cookieStore == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}

		HttpGet httpGet = new HttpGet(url);
	    //httpclient-4.4   禁止浏览器自动跳转  获取302状态路径
		RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(false).build();
	    httpGet.setConfig(requestConfig);
	    
	    
	    
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
					.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
	 
		HttpContext localContext = new BasicHttpContext();
		
		try {
			HttpResponse httpResponse = client.execute(httpGet,localContext);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			 
			if (statusCode >= 300 && statusCode < 400) {
				Header hearder = httpResponse.getFirstHeader("Location");
				 
				return  getLocationLoop(cookieStore, hearder.getValue(), headers, charset);
			}else{
				return url;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public static String getGetTextLoopCXw(CookieStore cookieStore_, String url,
			Map<String, String> headers, String charset) {
		
		String responseBody = null;
		CloseableHttpClient client = null;
		if (cookieStore_ == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore_)
			.build();
		}
		
		HttpGet httpGet = new HttpGet(url);
		
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
			.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		try {
			HttpResponse httpResponse = client.execute(httpGet);
//			text = in2Text(httpResponse.getEntity().getContent(), charset);
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode >= 300 && statusCode < 400) {
				responseBody = getGetText(cookieStore_, httpResponse.getFirstHeader(
						"Location").getValue().replace("isphone=false", "isphone=true"),headers,charset);
			} else {
				InputStream in = httpResponse.getEntity().getContent();
				responseBody =in2Text(in,charset);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseBody;
	}
	

	public static String getGetLocation(CookieStore cookieStore_, String url,
			Map<String, String> headers, String charset) {
		
		String responseBody = null;
		CloseableHttpClient client = null;
		if (cookieStore_ == null) {
			client = HttpClients.createDefault();
		} else {
			client = HttpClients.custom().setDefaultCookieStore(cookieStore_)
			.build();
		}
		
		HttpGet httpGet = new HttpGet(url);
		
		if (headers != null) {
			Iterator<Map.Entry<String, String>> iterator = headers.entrySet()
			.iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				httpGet.setHeader(entry.getKey(), entry.getValue());
			}
		}
		
		try {
			HttpResponse httpResponse = client.execute(httpGet);
			
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode >= 300 && statusCode < 400) {
				 
				return  httpResponse.getFirstHeader("Location").getValue();
			}  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getGetLocation(CookieStore cookieStore_, String url) {
		return getGetLocation(cookieStore_, url, null, null);
	}

    public static String in2Text(InputStream in, String charset)
            throws Exception {
        if (charset == null || "".equals(charset)) {
            charset = "UTF-8";
        }

		/*
		 * String text = ""; BufferedReader br = new BufferedReader(new
		 * InputStreamReader(in)); String temp = ""; while (temp != null) { text
		 * += temp; temp = br.readLine(); } System.out.println(new
		 * String(text.getBytes("ISO8859_1"),"utf-8"));
		 */
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buf = new byte[1024];
        int len = 0;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        buf = out.toByteArray();
        out.close();
        return new String(buf, charset);

    }

	


}
