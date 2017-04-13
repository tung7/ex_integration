package com.tung7.ex.repository.redis.utils.increaser.service;

import com.tung7.ex.repository.redis.utils.increaser.MsgVersionIncreaser;
import com.tung7.ex.repository.redis.utils.increaser.UserMsgVersionIncreaser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/15
 * @update
 */
@Service
public class LockServiceImpl implements ILockService {
    private static Set<Long> sets = new TreeSet<>();
    @Autowired
    MsgVersionIncreaser increaser;

    @Autowired
    UserMsgVersionIncreaser userIncreaser;

    public long getGlobalMsgVersion() {
        long ver = 0;
        try {
            ver = increaser.getNextMsgVersion();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return ver;
    }

    @Override
    public long getUserMsgVersion(Long userid) {
        long ver = 0;
        try {
            ver = userIncreaser.getNextUserMsgVersion(userid);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return ver;
    }
}
