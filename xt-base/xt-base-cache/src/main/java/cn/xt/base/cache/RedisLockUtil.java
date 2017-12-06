package cn.xt.base.cache;

import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * redis方式实现独占锁，参考：http://www.jianshu.com/p/535efcab356d
 */
public class RedisLockUtil {

    //默认的超时时间，一分钟
    private static final int defaultExpire = 60000;

    /**
     * 获得锁
     *
     * @param key    redis key
     * @param expire 过期时间，单位秒
     * @return true:加锁成功，false，加锁失败
     */
    public static boolean acquire(String key, Integer expire) {

        Jedis resource = null;
        try {
            resource = RedisUtil.getPool().getResource();
            if (expire == null) {
                expire = defaultExpire;
            }
            // setnx 仅当key不存在时设值，如果key不存在返回1，否则返回0
            long status = resource.setnx(key, (System.currentTimeMillis() + expire) + "");
            if (status == 1) { // setnx成功
                return true;
            }
            // setnx失败，说明key已经有了。

            String val = resource.get(key);
            long oldExpireTime = 0;
            if (val != null) {
                oldExpireTime = Long.parseLong(val);
            }

            //旧的值时间戳 < 当前时间戳 表示锁已经过期，可以允许别的请求重新设置
            if (oldExpireTime < System.currentTimeMillis()) {
                //计算新的超时时间戳
                long newExpireTime = System.currentTimeMillis() + expire;

                // getset 设置新值，如果key已经存在，则返回旧值，否则返回null
                String set = resource.getSet(key, String.valueOf(newExpireTime));
                if (!StringUtils.isEmpty(set)) {//说明redis已存在该key
                    long currentExpireTime = Long.parseLong(set);
                    if (currentExpireTime == oldExpireTime) {
                        //如果相等，说明当前getset设置成功，获取到了锁。如果不相等，说明这个锁又被别的请求获取走了，那么当前请求可以直接返回失败，或者继续重试。
                        return true;
                    }
                }
            }
            return false;
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
    }

    /**
     * 释放锁
     *
     * @param key
     * @return true表示释放成功
     */
    public static boolean release(String key) {

        Jedis resource = null;
        try {
            resource = RedisUtil.getPool().getResource();
            String val = resource.get(key);
            long oldExpireTime = 0;
            if (val != null) {
                oldExpireTime = Long.parseLong(val);
            }
            //redis中的值大于当前时间，说明还没有过期，可以手动释放
            if (oldExpireTime > System.currentTimeMillis()) {
                resource.del(key);
                return true;
            }
            return false;
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
    }
}
