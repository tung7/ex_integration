package com.tung7.ex.repository.poi.controller;

import com.tung7.ex.repository.poi.service.HWPFDocumentService;
import com.tung7.ex.repository.poi.service.XWPFDocumentService;
import org.apache.poi.POIXMLException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/5/26.
 * @update
 */
@Controller
@RequestMapping("/poi")
public class POIController {
    private Logger logger = LoggerFactory.getLogger(POIController.class);

    @Autowired
    XWPFDocumentService xwpfDocumentService;
    @Autowired
    HWPFDocumentService hwpfDocumentService;

    private enum DocType {
        DOC("doc"), DOCX("docx");
        private String name;
        DocType(String name) {
            this.name = name;
        }
        @Override
        public String toString() {
            return this.name();
        }
        public String getName() {
            return name;
        }
    }
    @RequestMapping(value = "/convert", method = RequestMethod.GET)
    public String convertUI() {
        return "docConvert";
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public void convert(HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {
        ServletOutputStream os = response.getOutputStream();
        try {
            XWPFDocument docx = new XWPFDocument(file.getInputStream());
            xwpfDocumentService.convert(docx, os);
        } catch (Exception e) {
            if (e instanceof POIXMLException) {
                logger.warn("非docx类型，尝试使用HWPFDocument解析！" + e.getMessage());
                try {
                    HWPFDocument doc = new HWPFDocument(file.getInputStream());
                    hwpfDocumentService.convert(doc, os);
                } catch (Exception ee) {
                    logger.error("不能识别成WORD文档类型！" + ee.getMessage());
                    throw  ee;
                }
            } else {
                throw  e;
            }
        }
    }
}
