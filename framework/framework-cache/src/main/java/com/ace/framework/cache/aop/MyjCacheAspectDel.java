package com.ace.framework.cache.aop;

import com.alibaba.fastjson.JSON;
import com.ace.framework.cache.annotation.MyjCache;
import com.ace.framework.util.redis.MyjJedisCommend;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;


/**
 * 
 * @author WuZhiWei
 * 
 */
@Component
@Aspect
public class MyjCacheAspectDel {
	
	@Resource(name="jedisCache")
	private MyjJedisCommend jedisCache;

	@Pointcut("execution(* com.ace..service..*.*(..))")
    public void simplePointcut() {

	}

	@Around("simplePointcut()")
	public Object aroundLogCalls(ProceedingJoinPoint joinPoint)
			throws Throwable {

		
		String targetName = joinPoint.getTarget().getClass().toString();
		String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();

        //获取所有方法，如果没有注解则通过
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        boolean flag = true;
        for (Method m : methods) {
                if(m.getAnnotation(MyjCache.class)!=null){
                    flag=false;
                }
        }
        if(flag){
            return proceed(joinPoint,arguments);
        }

        MyjCache aceCache =null;
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    aceCache = m.getAnnotation(MyjCache.class);

                    break;
                }
            }
        }
		if (aceCache != null) {  //如果有注解，怎么通过
            return proceed(joinPoint,arguments);
        }

        if(methodName.contains("update")||methodName.contains("del")||methodName.contains("save")||methodName.contains("insert")){
            String cacheKey = getCacheKey(targetName, methodName, arguments);

            Set<String> keys = jedisCache.keys(cacheKey);
            for(String key:keys){
                jedisCache.del(key);
            }
        }

        return proceed(joinPoint,arguments);
	}
    public Object proceed(ProceedingJoinPoint joinPoint,Object[] arguments){
        try {
            Object result=null;
            if ((arguments != null) && (arguments.length != 0)) {
                result = joinPoint.proceed(arguments);
            } else {
                result = joinPoint.proceed();
            }
            return result;
        } catch (Throwable throwable) {
            return null;
        }
    }
	/**
	 * 获得cache key的方法，cache key是Cache中一个Element的唯一标识 cache key包括
	 * 包名+类名+方法名，如com.co.cache.service.UserServiceImpl.getAllUser
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName);//.append(".").append(methodName);
		/*if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(JSON.toJSONString(arguments[i]));
			}
		}*/
		return sb.toString();
	}

    protected String serialize(Object target) {
        return JSON.toJSONString(target);
    }

    protected Object deserialize(String jsonString, Class clazz) {
        // 序列化结果应该是List对象
        if (clazz.isAssignableFrom(List.class)) {
            return JSON.parseArray(jsonString, clazz);
        }
        // 序列化结果是普通对象
        return JSON.parseObject(jsonString, clazz);
    }
}
