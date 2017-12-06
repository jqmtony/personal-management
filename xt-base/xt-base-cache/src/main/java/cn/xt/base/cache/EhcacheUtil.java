package cn.xt.base.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheUtil {

    private static final CacheManager CACHE_MANAGER = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache/ehcache.xml"));

    private static Cache c = null;
    public static Cache getCache(String key){
        if(c==null){
            c = CACHE_MANAGER.getCache(key);
        }
        return c;
    }

    public static Element get(Cache c,String key){
        return c.get(key);
    }
}
