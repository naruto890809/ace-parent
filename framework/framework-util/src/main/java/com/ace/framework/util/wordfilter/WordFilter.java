package com.ace.framework.util.wordfilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class WordFilter {
	/** 直接禁止的 */
	private HashMap keysMap = new HashMap();
	private int matchType = 1; // 1:最小长度匹配 2：最大长度匹配
	private final String[] starts = new String[] { "", "*", "**", "***",
			"****", "*****", "******", "*******", "********", "*********",
			"**********", "***********", "************", "*************",
			"**************", "***************", "****************",
			"*****************", "******************", };

	public WordFilter() {
		// Load from class path sensitiveWords-default.txt
		InputStream resourceAsStream = this.getClass().getResourceAsStream(
				"sensitiveWords-default.txt");
		if (resourceAsStream != null) {
			try {
				addKeywords(parseKeywords(resourceAsStream));
			} catch (IOException e) {
				e.printStackTrace();
				System.err
						.println("Failed to load sensitiveWords-default.txt for WordFilter");
			}
		} else {
			System.err
					.println("File sensitiveWords-default.txt not found for WordFilter");
		}

		InputStream inputStream = this.getClass().getResourceAsStream(
				"/sensitiveWords.txt");

		if (inputStream != null) {
			try {
				addKeywords(parseKeywords(inputStream));
			} catch (IOException e) {
				e.printStackTrace();
				System.out
						.println("Failed to load sensitiveWords.txt for WordFilter");
			}
		} else {
			System.out
					.println("File sensitiveWords.txt not found for WordFilter");
		}

	}

	public void addKeywords(List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			String key = keywords.get(i).trim();
			HashMap nowhash = null;
			nowhash = keysMap;
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(word);
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap<String, String> newWordHash = new HashMap<String, String>();
					newWordHash.put("isEnd", "0");
					nowhash.put(word, newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 重置关键词
	 */
	public void clearKeywords() {
		keysMap.clear();
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 */
	private int checkKeyWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}

	/**
	 * 返回txt中关键字的列表
	 */
	public Set<String> getTxtKeyWords(String txt) {
		Set set = new HashSet();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				i += len;
			} else {
				i++;
			}
		}
		txt = null;
		return set;
	}

	public String filterSenstiveWords(String txt) {
		if (txt == null) {
			return txt;
		}

		StringBuilder sb = null; //
		Set set = new HashSet();
		int l = txt.length();
		int pos = 0;
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				if (sb == null) {
					sb = new StringBuilder(txt.length());
				}

				sb.append(txt.substring(pos, i));
				if (len < starts.length) {
					sb.append(starts[len]);
				} else {
					sb.append(StringUtils.repeat("*", len));
				}
				i += len;
				pos = i;
			} else {
				i++;
			}
		}

		if (pos == 0) {
			return txt;
		}

		if (pos < l - 1) {
			sb.append(txt.substring(pos));
		}
		return sb.toString();
	}

	/**
	 * 仅判断txt中是否有关键字
	 */
	public boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}

	private List<String> parseKeywords(InputStream inputStream)
			throws IOException {
		List<String> result = new ArrayList<String>();
		LineNumberReader lineNumberReader = new LineNumberReader(
				new InputStreamReader(inputStream, "UTF-8"));
		String line = null;

		while ((line = lineNumberReader.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0) {
				result.add(line);
			}
		}

		return result;
	}

	public static void main(String[] args) {

		WordFilter filter = new WordFilter();
		List<String> keywords = new ArrayList<String>();
		keywords.add("中国人");
		keywords.add("中国男人");
		filter.addKeywords(keywords);
		String txt = "中国人民站起来了";
		boolean boo = filter.isContentKeyWords(txt);
		System.out.println(boo);
		Set set = filter.getTxtKeyWords(txt);
		System.out.println(set);
	}
}