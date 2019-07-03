package com.ace.framework.cache.aop;

import com.alibaba.fastjson.JSON;
import com.ace.framework.cache.annotation.MyjCache;
import com.ace.framework.util.JsonUtil;
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


/**
 * 
 * @author WuZhiWei
 * 
 */
@Component
@Aspect
public class MyjCacheAspect {
	
	@Resource(name="jedisCache")
	private MyjJedisCommend jedisCache;

	@Pointcut("@annotation(com.ace.framework.cache.annotation.MyjCache)")
    public void simplePointcut() {

	}
	 

//	@AfterReturning(pointcut = "simplePointcut()")
//	public void simpleAdvice() {
//	}

	@Around("simplePointcut()")
	public Object aroundLogCalls(ProceedingJoinPoint joinPoint)
			throws Throwable {
		 
		
		
		String targetName = joinPoint.getTarget().getClass().toString();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
        Class modelType =null;
                //试图得到标注的Ehcache类
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        MyjCache aceCache = null;
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length) {
                    aceCache = m.getAnnotation(MyjCache.class);

                    break;
                }
            }
        }
        if (aceCache == null) {
            return proceed(joinPoint,arguments);
        }
        modelType=aceCache.type();
		Object result;
		String cacheKey = getCacheKey(targetName, methodName, arguments);
//  // 得到被代理的方法上的注解


        boolean addOrdel=aceCache.addOrdel();
        if(!addOrdel){ //清空缓存
            jedisCache.del(cacheKey);
            return  proceed(joinPoint,arguments);
        }

        String cacheResult = jedisCache.get(cacheKey);

        if (StringUtils.isBlank(cacheResult)) {

			result = proceed(joinPoint,arguments);

            jedisCache.set(cacheKey,serialize(result));
            if(!aceCache.eternal()){
                jedisCache.expire(cacheKey,aceCache.cacheTime());
            }
		}else{
            Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
            result = deserialize(cacheResult, returnType,modelType);
        }

		return result;
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
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(JSON.toJSONString(arguments[i]));
			}
		}
		return sb.toString();
	}

    protected String serialize(Object target) {
        return JsonUtil.obj2Json(target,false);
    }

    protected Object deserialize(String jsonString, Class clazz,Class modelType) {
        // 序列化结果应该是List对象
        if (clazz.isAssignableFrom(List.class)) {
            if(modelType==null){
                return null;
            }
            return JSON.parseArray(jsonString, modelType);
        }
        // 序列化结果是普通对象
        return JSON.parseArray(jsonString, modelType);
    }
}
