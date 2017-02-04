package com.tung7.ex.repository.base.utils.increaser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/17
 * @update
 */
@Component
public class MsgVersionIncreaser extends AbstractBaseRedisIncreaser {
    private static Logger logger = LoggerFactory.getLogger(MsgVersionIncreaser.class);
    private static final String KEY = "msg_version";

    @Autowired
    RedisTemplate<String, String> template;

    public void setTemplate(RedisTemplate<String, String> template) {
        this.template = template;
    }

    @Override
    protected RedisTemplate getRedisTemplate() {
        return this.template;
    }

    @Override
    protected long intiSequnce() {
        long initVal = 1;
        /***
         * TODO from db
         */
        return initVal;
    }

    /**
     * 获取下一个序列，步进为1
     * @return
     */
    public long getNextMsgVersion() throws Exception {
        return next(KEY, 1);
    }
}