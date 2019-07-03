package com.ace.framework.util.redis;

import redis.clients.jedis.*;

import java.io.Closeable;

/**
 * @author WuZhiWei
 * @since 2016-01-06 10:58
 */
public interface MyjJedisCommend extends  BinaryJedisCommands, MultiKeyBinaryCommands,
        AdvancedBinaryJedisCommands, BinaryScriptingCommands, Closeable,JedisCommands, MultiKeyCommands,
        AdvancedJedisCommands, ScriptingCommands, BasicCommands, ClusterCommands, SentinelCommands {
}
