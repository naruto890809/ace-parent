package com.ace.framework.util.wordfilter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ace.framework.util.StreamUtil;

public class WordFilterTest {

	WordFilter wordFilter = new WordFilter();

	@Test
	public void testGetTxtKeyWords() throws UnsupportedEncodingException,
			IOException {

//		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(
//				"/sensitiveWords.txt");

		InputStream inputStream = new FileInputStream("sensitiveWords.txt");
		String input = StreamUtil.inputStreamToString(new FileInputStream("sensitiveTest.txt"));
		//String input = StreamUtil.inputStreamToString(new FileInputStream("sensitiveWords.txt"));
		System.out.println(input);
//		List<String> keywords = parseKeywords(inputStream);
//		wordFilter.addKeywords(keywords);
		long start = System.currentTimeMillis();
//		for (int i = 0; i <1000; i ++) {
//			Set<String> txtKeyWords = wordFilter.getTxtKeyWords(input);
//		}
		

		for (int i = 0; i <1000; i ++) {
			wordFilter.filterSenstiveWords(input);
		}
		String afterFilter = wordFilter.filterSenstiveWords(input);
		System.out.println(afterFilter);
		
		long spent = System.currentTimeMillis() - start;
		System.out.println("Spent: " + spent + " ms");
		//System.out.println(txtKeyWords);

	}

	private List<String> parseKeywords(InputStream inputStream)
			throws IOException {
		List<String> result = new ArrayList<String>();
		LineNumberReader lineNumberReader = new LineNumberReader(
				new InputStreamReader(inputStream));
		String line = null;
		
		while ((line = lineNumberReader.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0) {
				result.add(line);
			}
		}

		return result;
	}


}
