package com.tung7.ex.repository.qiniu.uploader;

import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.tung7.ex.repository.base.utils.Utils;
import com.tung7.ex.repository.qiniu.generator.UploadTokenGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;


public class SimpleFileUploader {
    private static final String IMG = "E:\\Tung\\Pictures\\Saved Pictures\\291585-106.jpg"; // image/jpeg
    private static final String SQL = "E:\\Tung\\Documents\\kn_resource.sql"; //application/x-sql
    private static final String DOC = "E:\\SVN\\src\\doc\\usermanual\\移动管理平台_数据字典.doc"; //application/msword
    private static final String DOCX = "E:\\SVN\\src\\doc\\usermanual\\移动打包平台_接口说明书.docx"; // application/vnd.openxmlformats-officedocument.wordprocessingml.document

    private static final String FILE_PATH = DOCX;

    public static Response upload(File file) throws Exception {
        String key = Utils.getMD5(new FileInputStream(file));
        String uploadToken = UploadTokenGenerator.generate();
        UploadManager uploadManager = new UploadManager(new Configuration());
        return uploadManager.put(file, key+"/"+file.getName(), uploadToken);
    }

    public static void main(String[] args) throws Exception {
        File f= new File(IMG);
        Response upload = upload(f);
        System.out.println(upload.bodyString());
    }

}
