package com.ace.framework.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
/**
 * 流实用类。
 * @author LIU Fangran
 *
 */
public class StreamUtil {
	private static final String UTF_8 = "utf-8";

	/**
	 * 读取输入流，返回字符串
	 * @param inputStream 输入流
	 * @return 字符串，按照utf-8编码对输入流进行解码。
	 * @throws java.io.IOException IO错误
	 * @throws java.io.UnsupportedEncodingException 不支持的编码，实际上不可能被抛出
	 */
	public static String inputStreamToString(InputStream inputStream) throws IOException,
			UnsupportedEncodingException {
		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				inputStream);
		byte[] tmpbuf = new byte[2000];
		int readbytes = 0;
		// set initial size to 4k.
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(4000);
		while ((readbytes = bufferedInputStream.read(tmpbuf)) != -1) {
			outputStream.write(tmpbuf, 0, readbytes);
		}

		String resultString = new String(outputStream.toByteArray(), UTF_8);
		return resultString;
	}
	

}
