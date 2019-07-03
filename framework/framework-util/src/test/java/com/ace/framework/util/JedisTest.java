package com.ace.framework.util;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
/**
 * Jedis单元测试类
 *
 * @author WuZhiWei
 * @since 2015-11-07 13:49:21
 */
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JedisTest {

    @Resource
    private Jedis jedis;

    @Test
    public void test(){

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Jedis jedis = (Jedis) applicationContext.getBean("jedis");

//        ApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
//        JedisCommands jedis = applicationContext.getBean("jedis", JedisCommands.class);

        jedis.set("name", "wzw");
        String name = jedis.get("name");
        System.out.println(name+"+========");
    }
}

