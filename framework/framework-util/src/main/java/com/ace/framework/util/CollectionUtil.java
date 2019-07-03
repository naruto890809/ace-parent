package com.ace.framework.util;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * 集合实用类
 * 
 * @author LIU Fangran
 * 
 */
public class CollectionUtil {

	/**
	 * 将List对象转化为Object数组
	 * 
	 * @param arguments
	 *            List对象
	 * @return Object数组
	 */
	public static Object[] toObjectArray(List<?> arguments) {
		Object[] result = new Object[arguments.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = arguments.get(i);
		}
		return result;
	}
    public static boolean checkContains(List list,Object entity,String methodName){
        try {
            String name= (String) methodInvoke(entity,methodName);
            for(Object t:list){
                String nameT= (String) methodInvoke(t,methodName);
                if(name.equals(nameT)){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    };
    public static Object methodInvoke(Object entity,String methodName){
        try {
            Method getNameMethod = entity.getClass().getMethod(methodName);
            return getNameMethod.invoke(entity);
        } catch (Exception e) {
            return null;
        }
    }
}
