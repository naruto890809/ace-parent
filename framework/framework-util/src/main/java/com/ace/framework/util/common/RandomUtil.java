package com.ace.framework.util.common;

import java.util.UUID;

public class RandomUtil {

	private RandomUtil(){

	}
	/**
	 * 获取UUID
	 * @return uuid
	 */
	public static String getUUID(){
		return  UUID.randomUUID().toString();
	}
}
