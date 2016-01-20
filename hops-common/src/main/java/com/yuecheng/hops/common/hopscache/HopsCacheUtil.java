package com.yuecheng.hops.common.hopscache;


import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;


public class HopsCacheUtil
{
    private static EhCacheCacheManager ehCacheCacheManager;

    /**
     * @return the ehCacheCacheManager
     */
    public static EhCacheCacheManager getEhCacheCacheManager()
    {
        return ehCacheCacheManager;
    }

    public static Object get(String cacheName, Object key)
    {
        Cache cache = ehCacheCacheManager.getCache(cacheName);
        if (cache != null)
        {
            ValueWrapper valueWrapper = cache.get(key);
            if (valueWrapper != null)
            {
                return valueWrapper.get();
            }
        }
        return null;
    }

    public static void put(String cacheName, Object key, Object value)
    {
        Cache cache = ehCacheCacheManager.getCache(cacheName);
        if (cache != null)
        {
            cache.put(key, value);

        }
    }

    public static boolean remove(String cacheName, Object key)
    {
        Cache cache = ehCacheCacheManager.getCache(cacheName);
        if (cache != null)
        {
            cache.evict(key);
        }
        return true;
    }

    public void setEhCacheCacheManager(EhCacheCacheManager ehCacheCacheManager)
    {
        HopsCacheUtil.ehCacheCacheManager = ehCacheCacheManager;
    }
}