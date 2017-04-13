package com.tung7.ex.repository.redis.utils.increaser;

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
public class UserMsgVersionIncreaser extends AbstractBaseRedisIncreaser {
    private static final String PREFIX = "msg_version_";

    @Autowired
    RedisTemplate template;

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
    public long getNextUserMsgVersion(Long userid) throws Exception {
        return next(PREFIX+userid, 1);
    }
}
