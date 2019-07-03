package com.ace.framework.util.redis;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.JedisURIHelper;
import redis.clients.util.Pool;
import java.net.URI;

/**
 * @author WuZhiWei
 * @since 2016-01-06 11:06
 */
public class MyjJedisPool  extends Pool<MyjJedis> {
    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final String host) {
        this(poolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE, null);
    }

    public MyjJedisPool(String host, int port) {
        this(new GenericObjectPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE, null);
    }

    public MyjJedisPool(final String host) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            String h = uri.getHost();
            int port = uri.getPort();
            String password = JedisURIHelper.getPassword(uri);
            int database = JedisURIHelper.getDBIndex(uri);
            this.internalPool = new GenericObjectPool<MyjJedis>(new MyjJedisFactory(h, port,
                    Protocol.DEFAULT_TIMEOUT, password, database, null), new GenericObjectPoolConfig());
        } else {
            this.internalPool = new GenericObjectPool<MyjJedis>(new MyjJedisFactory(host,
                    Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE, null),
                    new GenericObjectPoolConfig());
        }
    }

    public MyjJedisPool(final URI uri) {
        this(new GenericObjectPoolConfig(), uri, Protocol.DEFAULT_TIMEOUT);
    }

    public MyjJedisPool(final URI uri, final int timeout) {
        this(new GenericObjectPoolConfig(), uri, timeout);
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE, null);
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE, null);
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                     final int timeout) {
        this(poolConfig, host, port, timeout, null, Protocol.DEFAULT_DATABASE, null);
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database) {
        this(poolConfig, host, port, timeout, password, database, null);
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                     int timeout, final String password, final int database, final String clientName) {
        super(poolConfig, new MyjJedisFactory(host, port, timeout, password, database, clientName));
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri) {
        this(poolConfig, uri, Protocol.DEFAULT_TIMEOUT);
    }

    public MyjJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri, final int timeout) {
        super(poolConfig, new MyjJedisFactory(uri, timeout, null));
    }

    @Override
    public MyjJedis getResource() {
        MyjJedis jedis = super.getResource();
        jedis.setMyjDataSource(this);
        return jedis;
    }

    /**
     * @deprecated starting from Jedis 3.0 this method won't exist. Resouce cleanup should be done
     *             using @see {@link redis.clients.jedis.Jedis#close()}
     */
    @Deprecated
    public void returnBrokenResource(final MyjJedis resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }

    /**
     * @deprecated starting from Jedis 3.0 this method won't exist. Resouce cleanup should be done
     *             using @see {@link redis.clients.jedis.Jedis#close()}
     */
    @Deprecated
    public void returnResource(final MyjJedis resource) {
        if (resource != null) {
            try {
                resource.resetState();
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResource(resource);
                throw new JedisException("Could not return the resource to the pool", e);
            }
        }
    }
}
