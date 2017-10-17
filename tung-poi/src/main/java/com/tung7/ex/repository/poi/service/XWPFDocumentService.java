package com.tung7.ex.repository.poi.service;

import org.apache.poi.xwpf.converter.core.IImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * docx 格式文档 转html
 *
 * @author Tung
 * @version 1.0
 * @date 2017/5/26.
 * @update
 */
@Service
public class XWPFDocumentService {

    private static final ThreadLocal<Map<String, String>> IMAGE_URL_LOCAL = new ThreadLocal<>();

    private String saveToMongoDB(byte[] imageData){
        return "/monogoDB/dbdbdbdbdbddbdbdb";
    }

    public void convert(XWPFDocument document, OutputStream outputStream) throws IOException {
        XHTMLOptions options = XHTMLOptions.create();
        options.setOmitHeaderFooterPages(true);

        // 存放图片的文件夹
        options.setExtractor(new MongoImageExtractor());

        // html中图片的路径
        options.URIResolver(new MongoURIResolver());

//        outputStream = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
        XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
        xhtmlConverter.convert(document, outputStream, options);
    }

    private class MongoImageExtractor implements IImageExtractor {
        @Override
        public void extract(String key, byte[] imageData) throws IOException {
            System.out.println("extract ==================== "+ key);
            //TODO mongo upload
            String uploadUrl  = saveToMongoDB(imageData);
            Map<String, String> imageUrlMap = IMAGE_URL_LOCAL.get();
            if (imageUrlMap == null) {
                IMAGE_URL_LOCAL.set(new HashMap<String, String>());
            }
            IMAGE_URL_LOCAL.get().put(key, uploadUrl);
        }
    }

    private class MongoURIResolver implements IURIResolver {
        @Override
        public String resolve(String key) {
            System.out.println("resolve:========<" + key);
            String url = IMAGE_URL_LOCAL.get().get(key);
            return url;
        }
    }

}
