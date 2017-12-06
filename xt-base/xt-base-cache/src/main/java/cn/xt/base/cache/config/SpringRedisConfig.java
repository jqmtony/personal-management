package cn.xt.base.cache.config;

import cn.xt.base.cache.ExtendedRedisCacheManager;
import cn.xt.base.cache.RedisUtil;
import cn.xt.base.cache.properties.RedisConfig;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.DefaultRedisCachePrefix;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * redis和spring整合的配置
 * Created by heshun on 2017/2/28.
 */
@Configuration
@EnableCaching//<cache:annotation-driven cache-manager="redisCacheManager"/>
public class SpringRedisConfig {

    @Bean(name = "poolConfig")
    public static JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(RedisConfig.MAX_IDLE);
        config.setMaxWaitMillis(RedisConfig.MAX_WAIT_MILLIS);
        config.setTestOnBorrow(RedisConfig.TEST_ON_BORROW);
        return config;
    }

    @Bean(name = "jedisConnectionFactory")
    public static JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(RedisConfig.HOST);
        connectionFactory.setPort(RedisConfig.PORT);
        connectionFactory.setPassword(RedisConfig.PASSWORD);
        connectionFactory.setTimeout(RedisConfig.TIMEOUT);
        connectionFactory.setPoolConfig(jedisPoolConfig());
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean(name = "redisTemplate")
    public static RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.afterPropertiesSet();
        //默认是JdkSerializationRedisSerializer
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        //这里可以设置一个默认的过期时间
        cacheManager.setDefaultExpiration(RedisUtil.REDIS_KEY_EXPIRE_2_M);
       /* cacheManager.setUsePrefix(true);
        //前缀命名，仅当usePrefix为true时才生效
        cacheManager.setCachePrefix(new DefaultRedisCachePrefix("|"));
        */
       return cacheManager;
    }

    /**
     * 自定义CacheManager，实现缓存有效期可配置
     */
    /*@Bean(name = "extendedRedisCacheManager")
    public static ExtendedRedisCacheManager extendedRedisCacheManager() {
        ExtendedRedisCacheManager cacheManager = new ExtendedRedisCacheManager(redisTemplate(), Arrays.asList(new String[]{"redis"}));
        //默认缓存名字
        cacheManager.setDefaultCacheName("redis");
        //是否在容器启动时初始化
        cacheManager.setLoadRemoteCachesOnStartup(true);
        //是否使用前缀
        cacheManager.setUsePrefix(false);
        //前缀命名，仅当usePrefix为true时才生效
        cacheManager.setCachePrefix(new DefaultRedisCachePrefix(":"));
        //缓存名字和有效期的分隔符
        cacheManager.setSeparator('#');
        //默认有效期1h
        cacheManager.setDefaultExpiration(3600);
        //是否事务提交
        cacheManager.setTransactionAware(false);
        return cacheManager;
    }*/
}
