package com.ace.framework.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Pool;

import java.net.URI;

/**
 * @author WuZhiWei
 * @since 2016-01-06 10:58
 */
public class MyjJedis extends Jedis implements MyjJedisCommend{


    public MyjJedis(String host) {
        super(host);
    }

    public MyjJedis(String host, int port) {
        super(host, port);
    }

    public MyjJedis(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public MyjJedis(JedisShardInfo shardInfo) {
        super(shardInfo);
    }

    public MyjJedis(URI uri) {
        super(uri);
    }

    public MyjJedis(URI uri, int timeout) {
        super(uri, timeout);
    }

    protected Pool<MyjJedis> dataSource = null;

    @Override
    public void close() {
        if (dataSource != null) {
            if (client.isBroken()) {
                this.dataSource.returnBrokenResource(this);
            } else {
                this.dataSource.returnResource(this);
            }
        } else {
            client.close();
        }
    }

    public void setMyjDataSource(Pool<MyjJedis> jedisPool) {
        this.dataSource = jedisPool;
    }
}
