package com.tung7.ex.repository.redis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Tung on 2016/12/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appRedisContext.xml")
public class RedisTest {
    @Autowired
    RedisTemplate<String, String> template;

    @Test public void testRedisTemplate() {
        BoundValueOperations<String, String> ops = template.boundValueOps("tung7");
        ops.set("tung777777777777777");
        Assert.assertEquals(ops.get(),"tung777777777777777");
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
