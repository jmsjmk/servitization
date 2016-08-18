package com.servitization.commons.cache;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;

/**
 * Redis客户端池 基于一致性哈希算法实现的分布式Redis集群客户端
 */
public class RedisClientPool {

    private final ShardedJedisPool slavePool;
    private final ShardedJedisPool masterPool;

    RedisClientPool(Config config, List<JedisShardInfo> masterClientList,
                    List<JedisShardInfo> slaveClientList) {
        this.masterPool = new ShardedJedisPool(config, masterClientList);
        this.slavePool = new ShardedJedisPool(config, slaveClientList);
    }

    /**
     * 支持单分片模式
     *
     * @param config
     * @param masterClientList
     */
    RedisClientPool(Config config, List<JedisShardInfo> masterClientList) {
        this.masterPool = new ShardedJedisPool(config, masterClientList);
        this.slavePool = null;
    }

    ShardedJedis getMasterResource() {
        try {
            return masterPool.getResource();
        } catch (Exception e) {
            throw new JedisConnectionException("获取jedis连接失败", e);
        }
    }

    ShardedJedis getSlaveResource() {
        if (slavePool != null) {
            try {
                return slavePool.getResource();
            } catch (Exception e) {
                throw new JedisConnectionException("获取jedis连接失败", e);
            }
        } else
            throw new RuntimeException("单分片模式无法获取从资源");
    }

    void returnMasterResource(final Object resource) {
        try {
            masterPool.returnResource((ShardedJedis) resource);
        } catch (Exception e) {
            throw new JedisException("回收jedis连接失败", e);
        }
    }

    void returnSlaveResource(final Object resource) {
        if (slavePool != null) {
            try {
                slavePool.returnResource((ShardedJedis) resource);
            } catch (Exception e) {
                throw new JedisException("回收jedis连接失败", e);
            }
        } else
            throw new RuntimeException("单分片模式无法放回从资源");
    }

    void destoryMasterAll() {
        if (masterPool != null) {
            masterPool.destroy();
        }
    }

    void destorySlaveAll() {
        if (slavePool != null) {
            slavePool.destroy();
        }
    }
}
