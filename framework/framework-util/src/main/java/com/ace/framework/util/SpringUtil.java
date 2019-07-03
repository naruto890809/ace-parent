package com.ace.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *   需要在配置文件中增加  <bean id="springUtil" class="com.ace.framework.util.SpringUtil" />
 * @author WuZhiWei
 * @since 2015-12-24 13:22:00
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    public static<T>  T getBean(String beanName,Class<T> clazz){
        return applicationContext.getBean(beanName,clazz);
    }
}
