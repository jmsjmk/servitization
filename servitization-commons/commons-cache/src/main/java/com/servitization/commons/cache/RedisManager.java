package com.servitization.commons.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.*;

/**
 * redis主从架构的客户端管理类, 1 支持主从复制key/value,pop/push,pub/sub,读/写分离等功能的灾难失败自动选择和恢复 2
 * 可以选择读写分离功能,主写从读,默认不启用,都使用主服务进行所有操作
 */
public class RedisManager {

    RedisManagerProxy proxy;

    /**
     * 连接ip格式，默认第一个为主服务器,后面的为从服务器,若只设置一个，则为主服务器
     */
    public static final String DEFAULT_IP_FORMAT = "127.0.0.1:6379,127.0.0.1:6380";

    private static String ips = null;

    private static Properties properties = null;

    public static final Logger logger = LoggerFactory
            .getLogger(RedisManagerProxy.class);

    /**
     * 安全创建单例对象
     */
    private static class StaticHolder {
        static final RedisManagerProxy proxy = new RedisManagerProxy(ips, properties);
        static final RedisManager instance = (RedisManager) proxy
                .getProxy(new RedisManager(proxy));
    }

    public static RedisManager getInstance() {
        return StaticHolder.instance;
    }

    public static RedisManager getInstance(String ips) {
        RedisManager.ips = ips;
        return StaticHolder.instance;
    }

    public static RedisManager getInstance(String ips, Properties p) {
        RedisManager.ips = ips;
        RedisManager.properties = p;
        return StaticHolder.instance;
    }

    public RedisManager() {
        super();
    }

    private RedisManager(RedisManagerProxy proxy) {
        this.proxy = proxy;
    }

    // ************************作为接口暴露的方法***********************//

    public void del(ICacheKey key) {
        proxy.getCurrJedis().del(key.getKey());
    }

    // ************************以下针对单字符串的操作***********************//

    public long expire(ICacheKey key) {
        ShardedJedis jedis = proxy.getCurrJedis();
        if (key.getExpirationTime() > 0) {
            return jedis.expire(key.getKey(), key.getExpirationTime());
        }
        return -1;
    }

    public void put(ICacheKey key, String value) {
        ShardedJedis jedis = proxy.getCurrJedis();
        jedis.set(key.getKey(), value);
        if (key.getExpirationTime() != -1) {
            jedis.expire(key.getKey(), key.getExpirationTime());
        }
    }

    public void put(ICacheKey key, byte[] object) {
        ShardedJedis jedis = proxy.getCurrJedis();
        jedis.set(key.getKey().getBytes(), object);
        if (key.getExpirationTime() != -1) {
            jedis.expire(key.getKey().getBytes(), jedis.ttl(key.getKey())
                    .intValue());
        }
    }

    //返回还有多长时间失效 bin.liang
    public long getTTL(ICacheKey key) {
        return proxy.getCurrJedis().ttl(key.getKey());
    }

    public String get(ICacheKey key) {
        return proxy.getCurrJedis().get(key.getKey());
    }

    public byte[] getByte(ICacheKey key) {
        return proxy.getCurrJedis().get(key.getKey().getBytes());
    }

    public boolean exists(ICacheKey key) {
        return proxy.getCurrJedis().exists(key.getKey());
    }

    public boolean existsByte(ICacheKey key) {
        return proxy.getCurrJedis().exists(key.getKey().getBytes());
    }

    // *********************以下针对list的push,pop操作********************//

    public void push(ICacheKey listKeyName, String object) {
        push(listKeyName, object.getBytes());
    }

    public void push(ICacheKey listKeyName, byte[] objects) {
        ShardedJedis jedis = proxy.getCurrJedis();
        jedis.rpush(listKeyName.getKey().getBytes(), objects);
        if (listKeyName.getExpirationTime() != -1) {
            jedis.expire(listKeyName.getKey().getBytes(),
                    listKeyName.getExpirationTime());
        }
    }

    public List<String> pull(ICacheKey listKeyName) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.lrange(listKeyName.getKey(), 0,
                jedis.llen(listKeyName.getKey()));
    }

    public List<String> pull(ICacheKey listKeyName, int fromIndex, int lastIndex) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.lrange(listKeyName.getKey(), fromIndex, lastIndex);
    }

    public String pull(ICacheKey listKeyName, int index) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.lindex(listKeyName.getKey(), index);
    }

    public long length(ICacheKey listKeyName) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.llen(listKeyName.getKey());
    }

    public byte[] popByte(ICacheKey listKeyName) {
        ShardedJedis jedis = proxy.getCurrJedis();
        if (listKeyName.getExpirationTime() != -1) {
            jedis.expire(listKeyName.getKey().getBytes(),
                    listKeyName.getExpirationTime());
        }
        return jedis.lpop(listKeyName.getKey().getBytes());
    }

    // ***********************以下针对hash表操作*********************//

    public void hashPut(ICacheKey hashKeyName, String key, String value) {
        ShardedJedis jedis = proxy.getCurrJedis();
        if (hashKeyName.getExpirationTime() != -1) {
            jedis.expire(hashKeyName.getKey(), hashKeyName.getExpirationTime());
        }
        jedis.hset(hashKeyName.getKey(), key, value);
    }

    public void hashLength(ICacheKey hashKeyName) {
        proxy.getCurrJedis().hlen(hashKeyName.getKey());
    }

    public String hashGet(ICacheKey hashKeyName, String key) {
        return proxy.getCurrJedis().hget(hashKeyName.getKey(), key);
    }

    public Map<String, String> hashGetAll(ICacheKey hashKeyName) {
        return proxy.getCurrJedis().hgetAll(hashKeyName.getKey());
    }

    public void hashPut(ICacheKey hashKeyName, String key, byte[] value) {
        ShardedJedis jedis = proxy.getCurrJedis();
        jedis.hset(hashKeyName.getKey().getBytes(), key.getBytes(), value);
        if (hashKeyName.getExpirationTime() != -1) {
            jedis.expire(hashKeyName.getKey().getBytes(),
                    hashKeyName.getExpirationTime());
        }
    }

    public void hashByteLength(ICacheKey hashKeyName) {
        proxy.getCurrJedis().hlen(hashKeyName.getKey().getBytes());
    }

    public byte[] hashByteGet(ICacheKey hashKeyName, String key) {
        return proxy.getCurrJedis().hget(hashKeyName.getKey().getBytes(),
                key.getBytes());
    }

    /**
     * 以Object形式写入缓存
     *
     * @param key
     * @param object
     */
    public void put(ICacheKey key, Object object) {
        ShardedJedis jedis = proxy.getCurrJedis();
        String toJsonStr = JSON.toJSONString(object);
        jedis.set(key.getKey(), toJsonStr);
        if (key.getExpirationTime() != -1) {
            jedis.expire(key.getKey(), key.getExpirationTime());
        }
    }

    /**
     * 以Object形式写入缓存,不修改其缓存时间
     *
     * @param key
     * @param object
     */
    public void modify(ICacheKey key, Object object) {
        ShardedJedis jedis = proxy.getCurrJedis();
        int expirationTime = jedis.ttl(key.getKey()).intValue();
        String toJsonStr = JSON.toJSONString(object);
        jedis.set(key.getKey(), toJsonStr);
        if (key.getExpirationTime() != -1) {
            jedis.expire(key.getKey(),
                    (expirationTime == -1 || expirationTime == -2) ? key.getExpirationTime()
                            : expirationTime);
        }
    }

    /**
     * 获取字符串
     *
     * @param key
     * @return
     */
    public String getStr(ICacheKey key) {
        return proxy.getCurrJedis().get(key.getKey());
    }

    /**
     * 获取Object
     *
     * @param key
     * @return
     */
    public Object getObj(ICacheKey key) {
        String cacheValue = proxy.getCurrJedis().get(key.getKey());
        return JSON.parseObject(cacheValue, new TypeReference<Object>() {
        });
    }

    /**
     * 通过指定类获取Map
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> T getMap(ICacheKey key, Class<T> clazz) {
        String cacheValue = proxy.getCurrJedis().get(key.getKey());
        T t = JSON.parseObject(cacheValue, clazz);
        return t;
    }

    /**
     * 通过TypeReference获取Map
     *
     * @param key
     * @param clazz
     * @return
     */
    public <K, V> Map<K, V> getMap(ICacheKey key, TypeReference<Map<K, V>> clazz) {
        String cacheValue = proxy.getCurrJedis().get(key.getKey());
        Map<K, V> map = (Map<K, V>) JSON.parseObject(cacheValue, clazz);
        return map;
    }

    /**
     * 获取List
     *
     * @param key
     * @param clazz
     * @return
     */
    public <T> List<T> getList(ICacheKey key, Class<T> clazz) {
        String cacheValue = proxy.getCurrJedis().get(key.getKey());
        List<T> vList = JSON.parseArray(cacheValue, clazz);
        return vList;
    }

    /**
     * 批量取缓存对象
     *
     * @param keyList 所有KEY的list集合
     * @param clazz   缓存对象类型
     * @return 返回一个缓存对象List集合
     * @author Chao.Mu
     */
    public <T> List<T> mgetObjList(List<ICacheKey> keyList, Class<T> clazz) {
        List<T> resultList = null;
        List<String> resultStringList = null;
        List<String> clientInfoList = null;
        if (keyList != null && keyList.size() > 0) {
            resultStringList = new ArrayList<String>();
            resultList = new ArrayList<T>();
            clientInfoList = new ArrayList<String>();
            String keys[] = new String[keyList.size()];
            for (int i = 0; i < keyList.size(); i++) {
                keys[i] = keyList.get(i).getKey();
            }
            for (int i = 0; i < keys.length; i++) {
                // 取出每一个KEY所在节点的Jedis实例
                Jedis jedis = proxy.getCurrJedis().getShard(keys[i]);
                String clientInfo = jedis.getClient().getHost()
                        + jedis.getClient().getPort();
                // 判断是否mget过，如果没有mget过则把信息加入clientInfoList并使用该实例mget所有元素
                if (!clientInfoList.contains(clientInfo)) {
                    clientInfoList.add(clientInfo);
                    List<String> singleList = jedis.mget(keys);
                    // 遍历取出的元素，如果不为空则加入到string结果集中,如果为空则加入空字符串
                    for (int j = 0; j < singleList.size(); j++) {
                        String single = singleList.get(j);
                        if (!StringUtils.isBlank(single)) {
                            resultStringList.add(single);
                        } else {
                            resultStringList.add("{}");
                        }
                    }
                }
            }
            // 将结果集反序列化 生成结果返回
            for (int i = 0; i < resultStringList.size(); i++) {
                String cacheValue = resultStringList.get(i);
                T t = JSON.parseObject(cacheValue, clazz);
                resultList.add(t);
            }
        }
        return resultList;
    }

    /**
     * 批量取缓存对象
     *
     * @param keyList 所有KEY的list集合
     * @param clazz   缓存对象类型
     * @return 返回一个缓存对象Map
     * @author Chao.Mu
     */
    public <T> Map<String, T> mgetObjMap(List<ICacheKey> keyList, Class<T> clazz) {
        List<String> clientInfoList = null;
        List<Jedis> jedisList = null;
        Map<String, List<String>> groupKeysMap = null;
        Map<String, T> resultMap = null;
        if (keyList != null && keyList.size() > 0) {
            resultMap = new HashMap<String, T>();
            clientInfoList = new ArrayList<String>();
            jedisList = new ArrayList<Jedis>();
            groupKeysMap = new HashMap<String, List<String>>();
            // 把KEY先分组
            String keys[] = new String[keyList.size()];
            for (int i = 0; i < keyList.size(); i++) {
                keys[i] = keyList.get(i).getKey();
            }
            for (int i = 0; i < keys.length; i++) {
                // 取出每一个KEY所在节点的Jedis实例
                Jedis jedis = proxy.getCurrJedis().getShard(keys[i]);
                String clientInfo = jedis.getClient().getHost()
                        + jedis.getClient().getPort();
                // 判断是否有该分组，如果没有，创建新list存入，如果有则添加对应位置的KEY
                List<String> subKeyList = groupKeysMap.get(clientInfo);
                if (subKeyList == null) {
                    subKeyList = new ArrayList<String>();
                }
                subKeyList.add(keys[i]);
                groupKeysMap.put(clientInfo, subKeyList);
                // 保存此redis实例，过滤掉相同的服务器
                if (!clientInfoList.contains(clientInfo)) {
                    clientInfoList.add(clientInfo);
                    jedisList.add(jedis);
                }
            }
            // 遍历redis实例的列表，从分组的map中取出在此服务器上的key
            for (int i = 0; i < jedisList.size(); i++) {
                Jedis jedis = jedisList.get(i);
                String clientInfo = jedis.getClient().getHost()
                        + jedis.getClient().getPort();
                List<String> subKeyList = groupKeysMap.get(clientInfo);
                String[] subKeysArray = new String[subKeyList.size()];
                subKeysArray = subKeyList.toArray(subKeysArray);
                List<String> subResultList = jedis.mget(subKeysArray);
                for (int j = 0; j < subResultList.size(); j++) {
                    String single = subResultList.get(j);
                    if (!StringUtils.isBlank(single)) {
                        T t = JSON.parseObject(single, clazz);
                        resultMap.put(subKeysArray[j], t);
                    } else {
                        resultMap.put(subKeysArray[j], null);
                    }
                }
            }
        }
        return resultMap;
    }

    /**
     * 批量取缓存对象
     *
     * @param keyList 所有KEY的list集合
     * @return 返回一个缓存对象List集合
     * @author Chao.Mu
     */
    public Map<String, String> mgetStrMap(List<ICacheKey> keyList) {
        List<String> clientInfoList = null;
        List<Jedis> jedisList = null;
        Map<String, List<String>> groupKeysMap = null;
        Map<String, String> resultMap = null;
        if (keyList != null && keyList.size() > 0) {
            resultMap = new HashMap<String, String>();
            clientInfoList = new ArrayList<String>();
            jedisList = new ArrayList<Jedis>();
            groupKeysMap = new HashMap<String, List<String>>();
            // 把KEY先分组
            String keys[] = new String[keyList.size()];
            for (int i = 0; i < keyList.size(); i++) {
                keys[i] = keyList.get(i).getKey();
            }
            for (int i = 0; i < keys.length; i++) {
                // 取出每一个KEY所在节点的Jedis实例
                Jedis jedis = proxy.getCurrJedis().getShard(keys[i]);
                String clientInfo = jedis.getClient().getHost()
                        + jedis.getClient().getPort();
                // 判断是否有该分组，如果没有，创建新list存入，如果有则添加对应位置的KEY
                List<String> subKeyList = groupKeysMap.get(clientInfo);
                if (subKeyList == null) {
                    subKeyList = new ArrayList<String>();
                }
                subKeyList.add(keys[i]);
                groupKeysMap.put(clientInfo, subKeyList);
                // 保存此redis实例，过滤掉相同的服务器
                if (!clientInfoList.contains(clientInfo)) {
                    clientInfoList.add(clientInfo);
                    jedisList.add(jedis);
                }
            }
            // 遍历redis实例的列表，从分组的map中取出在此服务器上的key
            for (int i = 0; i < jedisList.size(); i++) {
                Jedis jedis = jedisList.get(i);
                String clientInfo = jedis.getClient().getHost()
                        + jedis.getClient().getPort();
                List<String> subKeyList = groupKeysMap.get(clientInfo);
                String[] subKeysArray = new String[subKeyList.size()];
                subKeysArray = subKeyList.toArray(subKeysArray);
                List<String> subResultList = jedis.mget(subKeysArray);
                for (int j = 0; j < subResultList.size(); j++) {
                    String single = subResultList.get(j);
                    if (!StringUtils.isBlank(single)) {
                        resultMap.put(subKeysArray[j], single);
                    } else {
                        resultMap.put(subKeysArray[j], "");
                    }
                }
            }
        }
        return resultMap;
    }

    public long incr(ICacheKey key) {
        ShardedJedis jedis = proxy.getCurrJedis();
        if (key.getExpirationTime() != -1) {
            jedis.expire(key.getKey(), key.getExpirationTime());
        }
        long cacheValue = jedis.incr(key.getKey()).longValue();
        return cacheValue;
    }

    /**
     * 如果在插入的过程用，参数中有的成员在Set中已经存在，该成员将被忽略，而其它成员仍将会被正常插入。如果执行该命令之前，该Key并不存在，
     * 该命令将会创建一个新的Set，此后再将参数中的成员陆续插入。如果该Key的Value不是Set类型，该命令将返回相关的错误信息。
     *
     * @param key
     * @param members
     * @return
     */
    public long sadd(ICacheKey key, final String... members) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.sadd(key.getKey(), members);
    }

    /**
     * 获取与该Key关联的Set中所有的成员。
     *
     * @param key
     * @return
     */
    public Set<String> smembers(ICacheKey key) {
        return proxy.getCurrJedis().smembers(key.getKey());
    }

    public long decr(ICacheKey key) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.decr(key.getKey()).longValue();
    }

    public Set<String> hkeys(ICacheKey key) {
        return proxy.getCurrJedis().hkeys(key.getKey());
    }

    public long srem(ICacheKey key, final String... members) {
        ShardedJedis jedis = proxy.getCurrJedis();
        return jedis.srem(key.getKey(), members);
    }
}
