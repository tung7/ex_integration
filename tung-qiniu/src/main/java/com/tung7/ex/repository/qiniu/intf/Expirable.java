package com.tung7.ex.repository.qiniu.intf;

/**
 * 上传文件的有效时间设定
 *
 * @author Tung
 * @version 1.0
 * @date 2017/10/18.
 * @update
 */
public interface Expirable {
    Long getExpired();
    void setExpored();
}
