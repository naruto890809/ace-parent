package com.ace.framework.util;

/**
 * 用于benchmark性能
 * Used to benchmark the performance.
 * <p>
 * 用于替换如下被写过无数遍的语句。
 * An easy way to substitute
 * <p>
 * <code>
 * long start = System.currentMillis();
 * <p>
 * ...
 * <p>
 * long spent = System.currentMillis() - start;
 * </code>
 * <p>
 * 
 * @author LIU Fangran
 * 
 */
public interface Stopwatch {
	/**
	 * 获取跑表开始至今的时间长度。
	 * Get time from the time the stopwatch being created.
	 * 
	 * @return
	 */
	public Long getSpentTime();
}
