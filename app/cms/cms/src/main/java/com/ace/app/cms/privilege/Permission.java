package com.ace.app.cms.privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author WuZhiWei
 * @since 2015-11-23 16:45
 */
@Retention(RetentionPolicy.RUNTIME) //代表Permission注解保留在的阶段
@Target(ElementType.METHOD)
public @interface Permission {
    /** 模块 */
    String module();
    /** 权限值 */
    String privilege();


}