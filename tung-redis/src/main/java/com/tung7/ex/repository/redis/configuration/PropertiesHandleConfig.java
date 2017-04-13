package com.tung7.ex.repository.redis.configuration;

import jdk.internal.util.xml.impl.Input;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Configuration
public class PropertiesHandleConfig {

    static Properties properties = new Properties();

    static {
        InputStream is = PropertiesHandleConfig.class.getClassLoader().getResourceAsStream("app.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory f = new JedisConnectionFactory();
        f.setHostName(properties.getProperty("redis.host", "127.0.0.1"));
        f.setPort(Integer.parseInt(properties.getProperty("redis.port", "6379")));
        f.setPassword(properties.getProperty("redis.password", null));
        return f;
    }

    @Bean
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate t = new RedisTemplate();
        t.setConnectionFactory(jedisConnectionFactory);
        t.setKeySerializer(new StringRedisSerializer());
        t.setValueSerializer(new StringRedisSerializer());

        return t;
    }
}
