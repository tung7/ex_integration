package com.tung7.ex.repository.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/15
 * @update
 */
@Component
public class RedisMsgVerIncreaser {
    private static Logger logger = LoggerFactory.getLogger(RedisMsgVerIncreaser.class);
    private final byte[] KEY = "msg_version".getBytes(Charset.forName("UTF-8"));
    private final byte[] LOCK_KEY = (new String(KEY) + "_lock").getBytes(Charset.forName("UTF-8"));
    private final long LOCK_EXPIRED = 1000;

    @Autowired
    RedisTemplate template;

    public void setTemplate(RedisTemplate template) {
        this.template = template;
    }

    /**
     * 为某个key加锁。
     * @return
     *  如果返回false，就说明已经加上锁了。不执行任何操作，只等待锁
     */
    private  boolean lock(RedisConnection connection) {
        return lock(connection, LOCK_EXPIRED);
    }

    private  boolean lock(RedisConnection connection,long ttl) {
        boolean ok = false;
        try {
            ok =  connection.setNX(LOCK_KEY, new byte[]{1});
            if (ok) {
                connection.pExpire(LOCK_KEY, ttl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.del(LOCK_KEY);
        }
        return ok;
    }

    private  void unlock(RedisConnection connection) {
        connection.del(LOCK_KEY);
    }

    private  long initMsgVersion(RedisConnection connection) {
        /**
         * DB
         */
        // TODO
        Long msgVer = 1L;
        connection.set(KEY, msgVer.toString().getBytes(Charset.forName("UTF-8")));
        return msgVer;
    }


    /**
     *
     * @return
     */
    private  long getNextMsgVersion(RedisConnection connection) throws Exception{
        long version = 0;
        try {
            boolean isKeyExisted =  connection.exists(KEY); //检查消息版本号这个key存不存在。
            if (isKeyExisted) { //存在则获取&自增
                version = connection.incr(KEY);
            } else {  // 不存在则从数据库更新。
                version = initMsgVersion(connection);
//                System.out.println("更新版本："+version);
            }
        } catch (Exception e) {
            // TODO 异常到这里的话 verison是0；要处理。不能让数据入库。
//            throw new RuntimeException("Get Message Version Failed! In" + this.getClass().getName() + "#getNextMsgVersion()");
        } finally {
            unlock(connection);  // 释放锁
        }
        return  version;
    }

    public  long getMsgVersion() throws Exception{
        long version = 0;
        RedisConnection connection = template.getConnectionFactory().getConnection();
        try {
            boolean ok = lock(connection);
            if (ok) {  //true 表示本次成功获取锁，执行操作
                logger.debug("获取全局自增器-锁：");
                System.out.println("获取全局自增器" + Thread.currentThread().getName());
                version = getNextMsgVersion(connection); // 获取新消息版本号 并 释放锁
            } else {   //false 表示未获得锁
                //等待锁
                boolean wait = lock(connection);
                logger.debug("等待全局自增器-锁");
                int times = 0;
                while (!wait) {  // 继续尝试获取锁。
                    logger.debug("继续尝试获取锁");
                    System.out.println("继续尝试获取锁" + Thread.currentThread().getName());
                    Thread.sleep(1);
                    wait = lock(connection);
                    if (times++ > 3000) {  //大约3秒超时
                        throw new RuntimeException("等待锁超时！");
                    }
                }
                version = getNextMsgVersion(connection); // 获取新消息版本号 并 释放锁
            }
        } catch (Exception e) {
            throw e;
        } finally {
            connection.close();
        }
        return version;
    }
}
