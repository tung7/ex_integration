package com.tung7.ex.repository.qiniu;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * Created by tung on 17-10-18.
 */
public class UploadTokenGenerator {
    public static final String ACCESS_KEY = "VRoHI3-p7-0-oT2YtQjte7JtQqThkVPpsUFoftsi";
    public static final String SECRET_KEY = "bQ1HOlAsRRVDraY96-VnnMhCtqaBifX3cJ7IECBT";
    public static final String BUCKET_NAME = "note";

    public static final long EXPIRED_PERIOD = 3600;
    public static final StringMap policy = new StringMap()
            .putNotNull(
                    "returnBody",
                    "{" +
                            "\"name\":$(fname)," +
                            "\"size\":$(fsize)," +
                            "\"width\":$(imageInfo.width)," +
                            "\"height\":$(imageInfo.height)," +
                            "\"format\":$(imageInfo.format)," +
                            "\"hash\":$(etag)" +
                            "\"key\":$(key)" +
                            "}"
            );

    public static String generate() {
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String token = auth.uploadToken(BUCKET_NAME, null, EXPIRED_PERIOD, policy);
        return token;
    }
}
