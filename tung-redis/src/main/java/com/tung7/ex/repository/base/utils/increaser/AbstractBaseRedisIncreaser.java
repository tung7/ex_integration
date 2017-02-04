package com.tung7.ex.repository.base.utils.increaser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/17
 * @update
 */
public abstract class AbstractBaseRedisIncreaser {
    private static Logger logger = LoggerFactory.getLogger(AbstractBaseRedisIncreaser.class);
    private long lockExpired = 1000;

    /**
     *
     * @return
     */
    protected abstract RedisTemplate getRedisTemplate();
    protected abstract long intiSequnce();

    public void setLockExpired(long ms) {
        this.lockExpired = ms;
    }
    /**
     * 为某个key加锁。
     * @return
     *  如果返回false，就说明已经加上锁了。不执行任何操作，只等待锁
     */
    private  boolean lock(RedisConnection connection,String key) {
        if (key == null || key.trim().equals("")) {
            throw new IllegalArgumentException("Key不能为空!");
        }
        byte[] lockKey = toByteArray(key+"_lock");

        boolean ok = false;
        try {
            ok =  connection.setNX(lockKey, new byte[]{1});
            if (ok) {
                connection.pExpire(lockKey, lockExpired);
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.del(lockKey);
        }
        return ok;
    }

    private  void unlock(RedisConnection connection, String key) {
        if (key == null || key.trim().equals("")) {
            throw new IllegalArgumentException("Key不能为空!");
        }
        byte[] lockKey = toByteArray(key+"_lock");
        connection.del(lockKey);
    }

    private long init(RedisConnection connection, String key) {
        Long initVal = intiSequnce();
        connection.set(toByteArray(key), toByteArray(initVal.toString()));
        return initVal;
    }



    /**
     * 获取下一个序列，步进为 step
     * @return
     */
    private long getNextSequnce(RedisConnection connection,  String key, long step) throws Exception{
        long version = 0;
        byte[] keyBytes = toByteArray(key);
        try {
            boolean isKeyExisted =  connection.exists(keyBytes); //检查序列存放的key存不存在。
            if (isKeyExisted) { //存在则获取&自增
                version = connection.incrBy(keyBytes, step);
            } else {  // 不存在则从数据库更新。
                version = init(connection, key);
            }
        } catch (Exception e) {
            throw new RuntimeException("Get Message Version Failed! In" + this.getClass().getName() + "#getNextSequnce()");
        } finally {
            unlock(connection, key);  // 释放锁
        }
        return  version;
    }

    private byte[] toByteArray(String key) {
        if(key == null || key.trim().equals("")) {
            throw new IllegalArgumentException("key 不能为空！");
        }
        return  key.getBytes(Charset.forName("UTF-8"));
    }

    /**
     * 加锁 获取下一个序列，步进为 step
     * @return
     */
    public long next(String key, long step) throws Exception{
        long version = 0;
        // 从池中获取connection
        RedisConnection connection = getRedisTemplate().getConnectionFactory().getConnection();
        try {
            boolean ok = lock(connection, key);
            if (ok) {  //true 表示本次成功获取锁，执行操作
                logger.debug("获取全局自增器-锁："+ Thread.currentThread().getName());
                version = getNextSequnce(connection, key, step); // 获取下一个值 并 释放锁
            } else {   //false 表示未获得锁
                //等待锁
                boolean wait = lock(connection, key);
                logger.debug("等待全局自增器-锁"+ Thread.currentThread().getName());
                int times = 0;
                while (!wait) {  // 继续尝试获取锁。
                    logger.debug("继续尝试获取锁"+ Thread.currentThread().getName());
                    Thread.sleep(5); //休眠3ms是根据压测结果得出。
                    wait = lock(connection, key);
                    if (times++ > 600) { //大约3秒超时
                        throw new RuntimeException("等待锁超时！");
                    }
                }
                version = getNextSequnce(connection, key, step); // 获取下一个值 并 释放锁
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // 返回池
            connection.close();
        }
        return version;
    }

}
