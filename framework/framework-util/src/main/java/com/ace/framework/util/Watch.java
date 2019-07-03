package com.ace.framework.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

public class Watch {
	Log log;
	long startTimestamp;
	String message;

	Watch(Log log, String message) {
		this.log = log;
		startTimestamp = System.currentTimeMillis();
		this.message = message;
	}

	public long stop() {

		long spentTime = System.currentTimeMillis() - startTimestamp;

		String corpCode = ExecutionContext.getCorpCode();
		String userId = ExecutionContext.getUserId();
		String methodStackTrace = ExecutionContext.getMethodStackTrace();
		String methodStackTraceText = StringUtils.isEmpty(methodStackTrace) ? ""
				: methodStackTrace;
		log.info("Spent(ms): " + spentTime + " ["
				+ Thread.currentThread().getName() + "] -on- (corpCode:"
				+ corpCode + ",userId:" + userId + ") " + message + " -on- " + methodStackTraceText);

		return spentTime;
	}
}
