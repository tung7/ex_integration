package com.tung7.ex.repository.qiniu.generator;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.tung7.ex.repository.qiniu.App;

import java.util.List;

/**
 * https://developer.qiniu.com/kodo/manual/1208/upload-token
 *
 * @author Tung
 * @version 1.0
 * @date 2017/10/18.
 * @update
 */

public class UploadTokenGenerator extends AbstractTokenGenerator {
    private static final Long EXPIRED_PERIOD = 3600L;

    //https://developer.qiniu.com/kodo/manual/1206/put-policy
    private static final StringMap POLICY = new StringMap().putNotEmpty(
            "returnBody",
            "{" +
            "\"name\":$(fname)," +
            "\"size\":$(fsize)," +
            "\"width\":$(imageInfo.width)," +
            "\"height\":$(imageInfo.height)," +
            "\"format\":$(imageInfo.format)," +
            "\"mime\":$(mimeType)," +
            "\"hash\":$(etag)," +
            "\"key\":$(key)" +
            "}"
    ).putNotNull("fsizeLimit", 2*1024*1000);
//            .putNotEmpty("mimeLimit", "image/jpeg;image/png;application/json;text/plain;");  这个不太现实。。

    public static String generate() {
        Auth auth = Auth.create(App.ACCESS_KEY, App.SECRET_KEY);
        String token = auth.uploadToken(App.BUCKET_NAME, null, EXPIRED_PERIOD, POLICY);
        return token;
    }

    public static void main(String[] args) {

    }
}
