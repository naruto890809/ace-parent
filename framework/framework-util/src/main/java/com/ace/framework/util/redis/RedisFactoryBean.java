package com.ace.framework.util.redis;

import java.lang.reflect.InvocationTargetException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


public class RedisFactoryBean implements MethodInterceptor,
        FactoryBean<MyjJedisCommend> {
    JedisPoolConfig jedisPoolConfig;
    MyjJedisPool jedisPool;
    MyjJedis jedis;

    /**
     * Jedis operation interface
     */
    MyjJedisCommend proxy;
    String host;
    int port;
    String password;

    public RedisFactoryBean(String host, int port) {
        this.host = host;
        this.port = port;
        jedis = new MyjJedis(host, port);
        proxy = (MyjJedisCommend) new ProxyFactory(MyjJedisCommend.class, this)
                .getProxy();
    }

    /**
     * Constructor using JedisPool
     *
     * @param jedisPoolConfig
     * @param host
     * @param port
     */
    public RedisFactoryBean(JedisPoolConfig jedisPoolConfig, String host,
                            int port) {
        this.host = host;
        this.port = port;
        jedisPool = new MyjJedisPool(jedisPoolConfig, host, port);
        proxy = (MyjJedisCommend) new ProxyFactory(MyjJedisCommend.class, this)
                .getProxy();
    }
    /**
     * 获取jedis实例
     * @author ZHANG Nan
     * @param jedisPoolConfig 连接池配置
     * @param host IP地址
     * @param port 端口
     * @param password 连接密码
     */
    public RedisFactoryBean(JedisPoolConfig jedisPoolConfig, String host,int port,String password) {
        this.host = host;
        this.port = port;
        this.password=password;
        jedisPool = new MyjJedisPool(jedisPoolConfig, host, port,0,password);
        proxy = (MyjJedisCommend) new ProxyFactory(MyjJedisCommend.class, this)
                .getProxy();
    }


    MyjJedisCommend getJedis() {
        return proxy;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (jedisPool == null) {
            return invokeInternal(invocation);
        } else {
            return invokeInternalWithPool(invocation);
        }
    }

    /**
     * Invoke Jedis without Pool
     * <p>
     * Use synchronized key word to ensure the thread-safe.
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    synchronized public Object invokeInternal(MethodInvocation invocation)
            throws Throwable {
        // Use synchronized for now, we'll use thread pool (or something like
        // that) to manage the resources.
        try {
            return invocation.getMethod().invoke(jedis,
                    invocation.getArguments());
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();

            if (targetException != null) {
                if (targetException instanceof JedisConnectionException) {
                    try {
                        jedis.disconnect();
                    } catch (Exception ee) {
                        // ignore error while disconnect.
                    }

                    jedis = new MyjJedis(host, port);
                }
                throw targetException;
            }
            throw e;
        }
    }

    /**
     * Invoke Jedis with JedisPool
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object invokeInternalWithPool(MethodInvocation invocation)
            throws Throwable {

        MyjJedis jedisFromPool = jedisPool.getResource();
        Object result = null;
        try {
            result = invocation.getMethod().invoke(jedisFromPool,
                    invocation.getArguments());
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();

            if (targetException != null) {
                if (targetException instanceof JedisConnectionException) {
                    returnBrokenJedis(jedisFromPool);
                } else {
                    returnJedis(jedisFromPool);
                }

                throw targetException;
            } else {
                returnJedis(jedisFromPool);
                throw e;
            }
        }

        returnJedis(jedisFromPool);

        return result;
    }

    /**
     * Return the broken Jedis to pool and ignore any error.
     *
     * @param jedisFromPool
     */
    void returnBrokenJedis(MyjJedis jedisFromPool) {
        try {
            jedisPool.returnBrokenResource(jedisFromPool);
        } catch (Exception e) {
            // print error only
            e.printStackTrace();
        }
    }

    /**
     * Return the Jedis to pool and ignore any error.
     *
     * @param jedisFromPool
     */

    void returnJedis(MyjJedis jedisFromPool) {
        try {
            jedisPool.returnResource(jedisFromPool);
        } catch (Exception e) {
            // print error only
            e.printStackTrace();
        }
    }

    @Override
    public MyjJedisCommend getObject() throws Exception {
        return getJedis();
    }

    @Override
    public Class<MyjJedisCommend> getObjectType() {
        return MyjJedisCommend.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}