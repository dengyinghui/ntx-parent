package com.ntx.base.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁
 */
@Slf4j
public class DistributedLockUtil {

    /**
     * 加锁
     * @param key 加锁key
     * @param waitTime 等待时间
     * @param expireTime 过期时间
     * @throws Exception
     */
    public static boolean tryLock(RedissonClient redissonClient, String key, long waitTime, long expireTime){
        RLock lock = redissonClient.getLock(key);
        boolean flag = false;
        try{
            flag = lock.tryLock(waitTime, expireTime, TimeUnit.SECONDS);
            if(flag){
                log.info("key:" + key + " ====== 加锁成功");
            } else{
                log.info("key:" + key + " ====== 加锁失败");
            }
        } catch (Exception e){
            e.printStackTrace();
            log.info("key:" + key + " ====== 加锁发送异常,加锁失败:" + e.getMessage());
        }
        return flag;
    }

    /**
     * 加锁
     * @param key 加锁key
     * @throws Exception
     */
    public static boolean tryLock(RedissonClient redissonClient, String key){
        return tryLock(redissonClient, key, 5L, 10L);
    }

    /**
     * 释放锁
     * @param key 加锁key
     */
    public static void unLock(RedissonClient redissonClient, String key){
        RLock lock = redissonClient.getLock(key);
        if(lock.isLocked()){
            if(lock.isHeldByCurrentThread()){
                log.info("key:" + key + " ====== 释放成功");
                lock.unlock();
            } else{
                log.info("key:" + key + " ====== 已被其他线程锁住,当前线程释放该锁失败.");
            }
        }
    }

}
