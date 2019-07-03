package com.ace.framework.util;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StreamUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInputStreamToString() throws UnsupportedEncodingException,
			IOException {
		String input = "spentNanos=122223\n{token=1234def13k}";
		ByteArrayInputStream inputStream = new ByteArrayInputStream(input
				.getBytes(Charset.forName("UTF-8")));

		assertEquals(input, StreamUtil.inputStreamToString(inputStream));
	}


}
