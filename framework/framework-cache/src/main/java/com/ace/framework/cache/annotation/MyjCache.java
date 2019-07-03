package com.ace.framework.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author WuZhiWei
 * @since 2016-04-06 10:50
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyjCache {
    //缓存名称,默认为空
//    String cacheName() default "eternalCache";
    // 增加缓存还是删除缓存，默认为增加缓存
    boolean addOrdel() default true;
    //临时缓存还是永久缓存，默认为永久缓存
    boolean eternal() default true;

    int cacheTime() default 1800;

    Class type() default String.class;// 转Json用到的model
}

