package com.tung7.ex.repository.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by Tung on 2016/12/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appRedisContext.xml")
public class RedisQueueTest {
    @Autowired
    RedisTemplate<String, String> template;
    @Test public void testPush(){
        RedisSerializer serializer = new StringRedisSerializer();
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);

        BoundListOperations<String,String> listOperations = template.boundListOps("msg_queue_list_sit");
        Random r = new Random(10000);
        long before = System.currentTimeMillis();
        int i =0;
//        while (i<100) { //29576  38572 28415
//            String msg = r.nextInt()+"_"+before;
//            String v = "1,b09540ae9ff106ee77dfd2fec44bad3a7c8b52934f7a1c05c66d01ffac7d1df4,msg.mp3,"+ msg + ",c7e5424b-da56-4367-bebb-232a10f3cf21";
//            listOperations.leftPush(v);
//            i++;
//        }
        while (i<100) { //19952  6950 7359 6008              //14,15   +> 10837 9104  | 7646 7745 9527
            String msg = r.nextInt()+"_"+before;
            String v = "2,b09540ae9ff106ee77dfd2fec44bad3a7c8b52934f7a1c05c66d01ffac7d1df4,msg.mp3,"+ msg + ",c7e5424b-da56-4367-bebb-232a10f3cf21";
            listOperations.leftPush(v);
            i++;
        }


    }

    @Test public void testCal() {
        long before = 1482483190148L;
        long after = 1482483229047L;
        System.out.println("Used : " + (after - before) + "ms");
    }

}
