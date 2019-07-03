package com.ace.framework.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WatchFactory {

	Log log;
	
	public WatchFactory(Class<?> clazz) {
		log = LogFactory.getLog(clazz);
	}
	
	public WatchFactory(String name) {
		log = LogFactory.getLog(name);
	}

	public  Watch watch(String message) {
		Watch performMonitor = new Watch(log, message);
		return performMonitor;
	}
}
