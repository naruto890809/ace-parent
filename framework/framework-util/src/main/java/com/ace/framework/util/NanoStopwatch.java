package com.ace.framework.util;

/**
 * 用于获取操作的时间间隔
 * Measure the time interval.
 * <p>
 * And the time is measured in nano seconds, providing better precision.
 * @author LIU Fangran
 *
 */
public class NanoStopwatch implements Stopwatch {
	/**
	 * 开始时间
	 */
	Long startTime;
	public NanoStopwatch () {
		startTime = System.nanoTime();
	}
	
	/**
	 * 获得时间间隔，单位是纳秒
	 */
	public Long getSpentTime() {
		return System.nanoTime() - startTime;
	}

}
