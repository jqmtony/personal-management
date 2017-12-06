package cn.xt.base.cache;


import cn.xt.base.cache.properties.RedisConfig;
import cn.xt.base.util.JsonUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.*;

/**
 * 缓存的redis实现
 * Created by heshun on 16-2-29.
 */
public class RedisUtil {


    private static Logger log = LoggerFactory.getLogger(RedisUtil.class);
    private static JedisPool pool = null;

    /**
     * redis的key的失效时间,2分钟
     */
    public static final int REDIS_KEY_EXPIRE_2_M = 120;

    /**
     * redis的key的失效时间，10分钟。
     */
    public static final int REDIS_KEY_EXPIRE_10_M = 600;
    /**
     * redis的key的失效时间，30分钟。
     */
    public static final int REDIS_KEY_EXPIRE_30_M = 1800;
    /**
     * redis的key的失效时间，两小时。
     */
    public static final int REDIS_KEY_EXPIRE_TWO_HOUR = 60 * 60 * 2;

    /**
     * redis的key的失效时间，一天
     */
    public static final int REDIS_KEY_EXPIRE_ONE_DAY = 60 * 60 * 24;

    /**
     * 构建redis连接池
     *
     * @return JedisPool
     */
    public static JedisPool getPool() {
        if (pool == null) {

            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
            config.setMaxTotal(RedisConfig.MAX_TOTAL);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
            config.setMaxIdle(RedisConfig.MAX_IDLE);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWaitMillis(RedisConfig.MAX_WAIT_MILLIS);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            pool = new JedisPool(config,
                    RedisConfig.HOST,
                    RedisConfig.PORT,
                    RedisConfig.TIMEOUT,
                    RedisConfig.PASSWORD);
        }
        return pool;
    }


    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static String get(String key) {
        Jedis jedis = getClient();
        try {
            return jedis.get(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    public static int set(String key, String value) {
        Jedis jedis = getClient();
        try {
            jedis.set(key, value);
            return 1;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 增量更新键
     *
     * @param key 键
     */
    public static Long incr(String key) {
        Jedis jedis = getClient();
        try {
            return jedis.incr(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 保存值
     *
     * @param key    键
     * @param value  值
     * @param expire 过期时间
     */
    public static int set(String key, String value, Integer expire) {
        Jedis jedis = getClient();
        try {
            jedis.set(key, value);
            if (null != expire) {
                jedis.expire(key, expire);
            }
            return 1;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 关闭连接
     *
     * @param jedis
     */
    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


    /**
     * push到消息队列
     *
     * @param key     键
     * @param content 消息内容
     */
    public static void lpush(byte[] key, byte[] content) {
        Jedis jedis = getClient();
        try {
            jedis.lpush(key, content);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 保存对象，使用hash方式保存至redis中。
     *
     * @param key    键
     * @param obj    对象
     * @param expire 过期时间
     */
    public static int setObj(String key, byte[] obj, Integer expire) {

        Jedis jedis = getClient();
        try {
            jedis.set(key.getBytes(), obj);
            if (null != expire) {
                jedis.expire(key.getBytes(), expire);
            }
            return 1;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 保存对象，使用hash方式保存至redis中。
     *
     * @param key    键
     * @param obj    对象
     * @param expire 过期时间
     */
    public static void setObj(String key, Object obj, Integer expire) {
        String json = JsonUtils.toJson(obj);
        set(key, json, expire);
    }

    /**
     * 返回序列化对象
     *
     * @param key      key
     * @param startKey 开始key
     * @param endKey   结束key
     * @return
     */
    public static List deserializeObj(String key, String startKey, String endKey) {
        Jedis jedis = getClient();
        try {
            Set<String> keys = jedis.zrangeByLex(key, startKey, endKey);
            if (!keys.isEmpty()) {
                List list = new ArrayList(keys.size());
                keys.forEach(k -> {
                    byte[] bytes = jedis.get(key.getBytes());
                    if (null != bytes) {
                        list.add(SerializationUtils.deserialize(bytes));
                    }
                });
                return list;
            }
        } finally {
            closeJedis(jedis);
        }
        return null;
    }

    /**
     * 获取对象形式的值
     *
     * @param key 键
     * @param cla 对象的Class
     */
    public static <T> T getObj(String key, Class<T> cla) {
        Jedis jedis = getClient();
        try {
            String json = get(key);
            if (json != null && !"".equals(json)) {
                return JsonUtils.fromJson(json, cla);
            }
            return null;
        } finally {
            closeJedis(jedis);
        }

    }


    /**
     * 根据正则表达式删除
     *
     * @param key
     */
    public static Long deleteByReg(String key) {

        Jedis jedis = getClient();
        try {
            Set<String> keys = jedis.keys(key);
            if (keys.size() > 0) {
                return jedis.del(keys.toArray(new String[keys.size()]));
            }
            return 0L;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 精确删除
     *
     * @param key
     * @return
     */
    public static Long delete(String key) {
        Jedis jedis = getClient();
        try {
            return jedis.del(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取被序列化的对象
     *
     * @param key 缓存key
     * @return
     */
    public static <T> T getSerializedObj(String key) {

        Jedis jedis = getClient();
        try {
            byte[] bytes = jedis.get(key.getBytes());
            if (null != bytes) {
                return SerializationUtils.deserialize(bytes);
            }
            return null;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 根据前缀获得包含该前缀的所有key
     *
     * @return
     */
    public static Set<String> getKeysByPrefix(String prefix) {
        Jedis jedis = getClient();
        Set<String> keys = jedis.keys(prefix + "*");
        return keys;
    }

    /**
     * 获取redis客户端
     *
     * @return
     */
    private static Jedis getClient() {
        return getPool().getResource();
    }

    /**
     * 精确删除
     *
     * @param key
     * @return
     */
    public static Long delete(byte[] key) {
        Jedis jedis = getClient();
        try {
            return jedis.del(key);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 模糊删除,由于对象序列化的时候,key是byte[],因此删除时候,和key是string的时候,不一样。
     *
     * @param key
     * @return
     */
    public static Long deleteByReg(byte[] key) {

        Jedis jedis = getClient();
        try {
            Set<byte[]> keys = jedis.keys(key);
            if (keys.size() > 0) {
                return jedis.del((byte[][]) keys.toArray());
            }
            return 0L;
        } finally {
            closeJedis(jedis);
        }

    }

    /**
     * 设置key的过期时间
     *
     * @param key         key
     * @param expiredTime 过期时间
     */
    public static Long expire(String key, int expiredTime) {
        Jedis jedis = getClient();
        try {
            return jedis.expire(key, expiredTime);
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 根据key进行遍历
     *
     * @param key
     * @return
     */
    public static List findDeserializeObj(String key) {
        JedisPool pool;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            Set<String> keys = jedis.keys(key);
            List list = new ArrayList(keys.size());
            if (keys.size() > 0) {
                Jedis finalJedis = jedis;
                keys.forEach(k -> {
                    byte[] bytes = finalJedis.get(k.getBytes());
                    if (null != bytes) {
                        list.add(SerializationUtils.deserialize(bytes));
                    }
                });
            }
            return list;
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 根据key查找
     *
     * @param key
     * @return map集合，map的key是redis中的key，value是redis的value
     */
    public static List<Map<String, String>> find(String key) {
        Jedis jedis = getClient();
        try {
            Set<String> keys = jedis.keys(key);
            List<Map<String, String>> list = new ArrayList<>(keys.size());
            keys.forEach(k -> {
                Map<String, String> map = new HashMap<>(1);
                map.put(k, jedis.get(k));
                list.add(map);
            });
            return list;
        } finally {
            closeJedis(jedis);
        }
    }
}
