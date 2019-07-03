package com.ace.framework.util;

/**
 * 比较实用类
 * @author LIU Fangran
 *
 */
public class CompareUtil {

	/**
	 * 比较两个对象
	 * @param c1 对象1
	 * @param c2 对象2
	 * @return 当c1>c2时返回正数；当c1 &lt c2时，返回负数；相等时返回0.
	 */
	public static <T> int compare(Comparable<T> c1, T c2) {
		if (c1 == null) { // null is smallest
			if (c2 == null) {
				return 0;
			} else {
				return -1;
			}
		} else {
			if (c2 == null) {
				return 1;
			} else {
				return c1.compareTo(c2);
			}
		}
	}

	/**
	 * 比较两组值。先比较p1, otherP1，如果相等，再比较p2, otherP2.
	 * @param p1 优先比较的属性
	 * @param p2 次要的比较属性
	 * @param otherP1 另一个优先比较的属性
	 * @param otherP2 另一个次要的比较属性
	 * @return 比较的结果数值。
	 */
	public static <T, T2> int compare(Comparable<T> p1, Comparable<T2> p2,
			T otherP1, T2 otherP2) {
		int result = CompareUtil.compare(p1, otherP1);
		if (result != 0) {
			return result;
		}
		return CompareUtil.compare(p2, otherP2);
	}

}
