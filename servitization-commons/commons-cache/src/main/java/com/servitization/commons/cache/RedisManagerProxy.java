package com.servitization.commons.cache;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * redis操作代理类，处理通用操作
 */
public class RedisManagerProxy implements MethodInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RedisManagerProxy.class);

    private RedisClientPool jedisPool;
    private static final ThreadLocal<ShardedJedis> currJedis = new ThreadLocal<>();
    private Properties resources = null;
    private boolean singleShard = false;
    private Random random = new Random();
    private AtomicLong hit = new AtomicLong();
    private AtomicLong miss = new AtomicLong();

    private static final Map<String, OP> methodNameMap = new HashMap<>();

    static {
        methodNameMap.put("del", OP.WRITE);
        methodNameMap.put("put", OP.WRITE);
        methodNameMap.put("getTTL", OP.READ);
        methodNameMap.put("get", OP.READ);
        methodNameMap.put("getByte", OP.READ);
        methodNameMap.put("exists", OP.READ);
        methodNameMap.put("existsByte", OP.READ);
        methodNameMap.put("push", OP.WRITE);
        methodNameMap.put("pull", OP.READ);
        methodNameMap.put("length", OP.READ);
        methodNameMap.put("popByte", OP.READ);
        methodNameMap.put("pop", OP.READ);
        methodNameMap.put("hashPut", OP.WRITE);
        methodNameMap.put("hashLength", OP.READ);
        methodNameMap.put("hashGet", OP.READ);
        methodNameMap.put("hashGetAll", OP.READ);
        methodNameMap.put("hashByteLength", OP.READ);
        methodNameMap.put("hashByteGet", OP.READ);
        methodNameMap.put("flush", OP.WRITE);
        methodNameMap.put("flushByte", OP.WRITE);
        methodNameMap.put("getList", OP.READ);
        methodNameMap.put("getStr", OP.READ);
        methodNameMap.put("getObj", OP.READ);
        methodNameMap.put("getMap", OP.READ);
        methodNameMap.put("mgetObjList", OP.READ);
        methodNameMap.put("mgetObjMap", OP.READ);
        methodNameMap.put("incr", OP.WRITE);
        methodNameMap.put("sadd", OP.WRITE);
        methodNameMap.put("smembers", OP.READ);
        methodNameMap.put("decr", OP.WRITE);
        methodNameMap.put("hkeys", OP.READ);
        methodNameMap.put("expire", OP.WRITE);
        methodNameMap.put("modify", OP.WRITE);
        methodNameMap.put("srem", OP.WRITE);
    }

    public RedisManagerProxy(String ips, Properties p) {
        if (p == null) {
            resources = ConfigFactory.getRedisConfig();
        } else {
            resources = p;
        }
        Config config = loadPoolConfig();

        if (ips == null) {
            if (resources != null && resources.get("redis.ip") != null) {
                ips = (String) resources.get("redis.ip");
            } else {
                ips = RedisManager.DEFAULT_IP_FORMAT;
            }
        }
        logger.info(ips);
        String[] ip = ips.split(",");
        int cmdTimeout = 2000;
        if (resources != null && resources.get("redis.cmdtimeout") != null) {
            cmdTimeout = Integer.parseInt((String) resources.get("redis.cmdtimeout"));
        }
        List<JedisShardInfo> masterList = new LinkedList<>();
        List<JedisShardInfo> slaveList = new LinkedList<>();
        JedisShardInfo jedisInfo;
        try {
            for (int i = 0; i < ip.length; i++) {
                String[] ipInfo = ip[i].split(":");
                if (ipInfo.length == 4) {
                    jedisInfo = new JedisShardInfo(ipInfo[0],
                            Integer.valueOf(ipInfo[1]), cmdTimeout, ipInfo[3]);
                    if ("0".equals(ipInfo[2])) {
                        masterList.add(jedisInfo);
                    } else {
                        slaveList.add(jedisInfo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ip格式不对#，示例:" + RedisManager.DEFAULT_IP_FORMAT);
            System.exit(-1);
        }

        if (masterList.size() > 0 && slaveList.size() > 0) {
            jedisPool = new RedisClientPool(config, masterList, slaveList);
        } else if (masterList.size() > 0 && slaveList.size() == 0) {
            jedisPool = new RedisClientPool(config, masterList);
            singleShard = true;
        } else if (masterList.size() == 0 && slaveList.size() > 0) {
            jedisPool = new RedisClientPool(config, slaveList);
            singleShard = true;
        } else {
            throw new RuntimeException("ip地址解析失败，错误的ip地址配置!");
        }

        Thread reporter = new Thread() {

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                    logger.info("The redis was hit " + hit.get() + " times and miss " + miss.get() + " times in last minute.");
                    hit.set(0L);
                    miss.set(0L);
                }
            }
        };
        reporter.start();
    }

    ShardedJedis getCurrJedis() {
        return currJedis.get();
    }

    RedisManager target;

    Object getProxy(Object target) {
        this.target = (RedisManager) target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object arg0, Method arg1, Object[] arg2,
                            MethodProxy methodPxoxy) throws Throwable {
        OP op = methodNameMap.get(methodPxoxy.getSignature().getName());
        if (OP.READ == op) {
            // 随机读
            boolean useMaster = singleShard || random.nextBoolean();    //单分片模式下，只用master
            try {
                if (useMaster) {
                    currJedis.set(jedisPool.getMasterResource());
                } else {
                    currJedis.set(jedisPool.getSlaveResource());
                }
                Object oj = methodPxoxy.invoke(target, arg2);
                // 统计命中率
                if (oj != null) hit.incrementAndGet();
                else miss.incrementAndGet();
                return oj;
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage() == null ? "MethodProxy.throw.exception"
                        : e.getTargetException().getMessage(), e);
            } catch (Throwable e) {
                logger.error(e.getMessage() == null ? "MethodProxy.throw.exception"
                        : e.getMessage(), e);
            } finally {
                if (useMaster) {
                    jedisPool.returnMasterResource(currJedis.get());
                } else {
                    jedisPool.returnSlaveResource(currJedis.get());
                }
                currJedis.remove();
            }
        } else {
            // 双写
            Object oj = null;
            try {
                currJedis.set(jedisPool.getMasterResource());
                oj = methodPxoxy.invoke(target, arg2);
            } catch (InvocationTargetException e) {
                logger.error(e.getMessage() == null ? "MethodProxy.throw.exception"
                        : e.getTargetException().getMessage(), e);
            } catch (Throwable e) {
                logger.error(e.getMessage() == null ? "MethodProxy.throw.exception"
                        : e.getMessage(), e);
            } finally {
                jedisPool.returnMasterResource(currJedis.get());
                if (singleShard) {    //单分片模式
                    currJedis.remove();
                    return oj;
                } else {
                    try {
                        currJedis.set(jedisPool.getSlaveResource());
                        oj = methodPxoxy.invoke(target, arg2);
                        return oj;
                    } catch (InvocationTargetException e) {
                        logger.error(e.getMessage() == null ? "MethodProxy.throw.exception"
                                : e.getTargetException().getMessage(), e);
                    } catch (Throwable e) {
                        logger.error(e.getMessage() == null ? "MethodProxy.throw.exception"
                                : e.getMessage(), e);
                    } finally {
                        jedisPool.returnSlaveResource(currJedis.get());
                        currJedis.remove();
                    }
                }
            }
        }
        return null;
    }

    public RedisClientPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(RedisClientPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Config loadPoolConfig() {
        Config config = new Config();
        Map<String, String> prop = new HashMap<>();
        try {
            if (resources != null) {
                Enumeration<?> en = resources.propertyNames();
                while (en.hasMoreElements()) {
                    String name = (String) en.nextElement();
                    if (name.startsWith("redis.pool.")) {
                        prop.put(name.substring(11), resources.getProperty(name));
                    }
                }
            }
            // 设置默认
            config.maxActive = prop.get("maxActive") == null ? 50 : Integer
                    .valueOf(prop.get("maxActive"));
            config.maxIdle = prop.get("maxIdle") == null ? 5 : Integer
                    .valueOf(prop.get("maxIdle"));
            config.maxWait = prop.get("maxWait") == null ? 5000 : Integer
                    .valueOf(prop.get("maxWait"));
            config.testOnBorrow = prop.get("testOnBorrow") == null ? true
                    : Boolean.valueOf(prop.get("testOnBorrow"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }
}
