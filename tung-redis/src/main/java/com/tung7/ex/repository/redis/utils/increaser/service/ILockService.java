package com.tung7.ex.repository.redis.utils.increaser.service;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/15
 * @update
 */
public interface ILockService {
     long getGlobalMsgVersion();
     long getUserMsgVersion(Long userid);
}
