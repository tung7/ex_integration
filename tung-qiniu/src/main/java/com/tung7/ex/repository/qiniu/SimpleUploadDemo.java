package com.tung7.ex.repository.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.tung7.ex.repository.base.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by tung on 17-10-18.
 */
public class SimpleUploadDemo {
    public static final String FILE_NAME  = "/home/tung/Pictures/183801-106.jpg";

    public static void main(String[] args) throws Exception {
        UploadManager uploadManager = new UploadManager(new Configuration());

        String token = UploadTokenGenerator.generate();

//        Response r = uploadManager.put("hello world".getBytes(), "yourkey", token);
        Response r = uploadFile(uploadManager, token, FILE_NAME);
        System.out.println(r.bodyString());
    }

    private static Response uploadFile(UploadManager uploadManager, String token, String filePath) throws IOException, NoSuchAlgorithmException {
        File f = new File(filePath);
        String key = getMD5(f);
        return uploadManager.put(f, key, token);
    }

    private static String getMD5(File file) throws IOException, NoSuchAlgorithmException {
        return Utils.getMD5(new FileInputStream(file));
    }
}
