package com.tung7.ex.repository.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于单台可读Redis节点的全局自增器。
 * @author Tung
 * @version 1.0
 * @date 2017/1/15
 * @update
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appRedisContext.xml")
public class IDIncreaserTest {
    @Autowired
    RedisTemplate<String, String> template;

    private static final byte[] KEY = "msg_version".getBytes(Charset.forName("UTF-8"));
    private static final byte[] LOCK_KEY = (new String(KEY) + "_lock").getBytes(Charset.forName("UTF-8"));
    private static final long LOCK_EXPIRED = 1000;


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
//            connection.multi();
            ok =  connection.setNX(LOCK_KEY, new byte[]{1});
            if (ok) {
                connection.pExpire(LOCK_KEY, ttl);
//                connection.exec();
            } else {
//                connection.discard();
            }
        } catch (Exception e) {
            e.printStackTrace();
            connection.del(LOCK_KEY);
        }
        return ok;
    }

    private  void unlock(RedisConnection connection) {
//        System.out.println("开始释放锁");
        connection.del(LOCK_KEY);
        System.out.println("释放锁完成");
    }

    private  long initMsgVersion(RedisConnection connection) {
        /**
         * DB
         */
        Long msgVer = 1990L+1;
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
                System.out.println("自增："+version);
            } else {  // 不存在则从数据库更新。
                version = initMsgVersion(connection);
                System.out.println("更新版本："+version);
            }
        } catch (Exception e) {
            // TODO 异常到这里的话 verison是0；要处理。不能让数据入库。
//            throw new RuntimeException("Get Message Version Failed! In" + this.getClass().getName() + "#getNextMsgVersion()");
        } finally {
            unlock(connection);  // 释放锁
        }
        return  version;
    }

    @Test public void test() {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    getMsgVersion();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("获取消息版本号失败，请勿入库。");
                }
            }
        };
        ExecutorService es = Executors.newCachedThreadPool();
//
        try {
            getMsgVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while(i<2) {
            es.execute(task);
            i++;
        }
    }

    public  long getMsgVersion() throws Exception{
        long version = 0;
        RedisConnection connection = template.getConnectionFactory().getConnection();
        try {
            boolean ok = lock(connection);
            if (ok) {  //true 表示本次成功获取锁，执行操作
                System.out.println("获取锁："+ok);
                version = getNextMsgVersion(connection); // 获取新消息版本号 并 释放锁
            } else {   //false 表示未获得锁
                //等待锁
                boolean wait = lock(connection);
                System.out.println("等待锁："+wait);
                while (!wait) {  // 继续尝试获取锁。
    //                System.out.println("继续尝试获取锁");
                    Thread.sleep(1);wait = lock(connection);
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
