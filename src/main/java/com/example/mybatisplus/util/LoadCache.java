package com.example.mybatisplus.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Slf4j
public class LoadCache {
    private static LoadingCache<String,Object> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(3000).expireAfterWrite(20,TimeUnit.MINUTES)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String s) throws Exception {
                    //在这里可以初始化加载数据的缓存信息，读取数据库中信息或者是加载文件中的某些数据信息
                    return null;
                }
            });
    public static void putLoadingCache(String key,Object value){
        loadingCache.put(key,value);
    }
    public static Object getLoadingCache(String key){
        try {
            Object o = loadingCache.get(key);
            //删除缓存
            loadingCache.invalidate(o);
            return o;
        } catch (ExecutionException e) {
            log.error("获取缓存值异常:{}",e);
            return null;
        }
    }
}
