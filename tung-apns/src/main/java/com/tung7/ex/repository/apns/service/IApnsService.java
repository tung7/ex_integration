package com.tung7.ex.repository.apns.service;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/19
 * @update
 */
public interface IApnsService {
    String pushInSync(String msg);

    void pushInAsync(String msg);
}
