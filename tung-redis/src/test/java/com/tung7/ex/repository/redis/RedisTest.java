package com.tung7.ex.repository.redis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by Tung on 2016/12/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appRedisContext.xml")
public class RedisTest {
    @Autowired
    RedisTemplate<String, Object> template;


    @Test public void testRedisSet() {
        RedisSerializer serializer = new StringRedisSerializer();
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        BoundSetOperations<String,Object> ops = template.boundSetOps("tung_set");
        HashSet<Object> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        ops.add(set.toArray(new Object[]{})); //SADD "3", "2", "1", "4"

    }

    @Test public void testRedisTemplateSerialize(){
        RedisSerializer serializer = new StringRedisSerializer();
        //默认是JdkSerializationRedisSerializer,这种序列化会使得key过长，浪费内存。
        // 对不同类型使用不同序列化可以减少存储空间
//        template.setDefaultSerializer(serializer);  //这样不起作用？单纯这么设置时，monitor输出仍然带未识别字符
        template.setKeySerializer(serializer);
        template.setValueSerializer(serializer);
        BoundValueOperations<String, Object> ops = template.boundValueOps("tung7");
        ops.set("tung777777777777777");
        Assert.assertEquals("tung777777777777777",ops.get());

        ValueOperations<String,Object> vo = template.opsForValue();
        vo.set("tung7","abc");
    }


    @Test public void testRedisTemplate() {
        BoundValueOperations<String, Object> ops = template.boundValueOps("tung7");
        ops.set("tung777777777777777");
        Assert.assertEquals("tung777777777777777",ops.get());

        ValueOperations<String,Object> vo = template.opsForValue();
        vo.set("tung7","123456",10, TimeUnit.SECONDS);  // 10s expired
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(vo.get("tung7"),"123456");
    }


    @Test public void firstTest() {
        System.out.println("First");
    }
    @Before
    public void before() {
        System.out.println("================ START ==================");
    }

    @After
    public void after() {
        System.out.printf("================ END ==================");
    }
}
