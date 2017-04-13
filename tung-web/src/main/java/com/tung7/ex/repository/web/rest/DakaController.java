package com.tung7.ex.repository.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/3/28.
 * @update
 */
@Controller
public class DakaController {
    @RequestMapping(value = "/mobile/binary", method = RequestMethod.POST)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //http://apilocate.amap.com/
        String xml2 = "<?xml version=\"1.0\" encoding=\"GBK\" ?><Cell_Req Ver=\"4.0.0\"><BIZ></BIZ><HDA Version=\"4.0.0\" SuccessCode=\"1\"></HDA><DRA><apiTime>1490701088535</apiTime><retype>-1</retype><citycode>0755</citycode><adcode>440305</adcode><cenx>113.9472418</cenx><ceny>22.538855</ceny><radius>35</radius><desc><![CDATA[广东省 深圳市 南山区 高新南一道 靠近联想大厦]]></desc><revergeo><country><![CDATA[中国]]></country><province><![CDATA[广东省]]></province><city><![CDATA[深圳市]]></city><district><![CDATA[南山区]]></district><road><![CDATA[高新南一道]]></road><street><![CDATA[深南大道]]></street><number><![CDATA[9811号]]></number><aoiname><![CDATA[联想大厦]]></aoiname><poiname><![CDATA[联想大厦]]></poiname></revergeo></DRA><COA><eab>1</eab><ctl>144723967</ctl><suc>1</suc></COA></Cell_Req>";
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\" ?><Cell_Req Ver=\"4.0.0\"><BIZ></BIZ><HDA Version=\"4.0.0\" SuccessCode=\"1\"></HDA><DRA><apiTime>1490627801561</apiTime><retype>-1</retype><citycode>0755</citycode><adcode>440305</adcode><cenx>113.9471257</cenx><ceny>22.5389483</ceny><radius>29</radius><desc><![CDATA[广东省 深圳市 南山区 高新南一道 靠近联想大厦]]></desc><revergeo><country><![CDATA[中国]]></country><province><![CDATA[广东省]]></province><city><![CDATA[深圳市]]></city><district><![CDATA[南山区]]></district><road><![CDATA[高新南一道]]></road><street><![CDATA[深南大道]]></street><number><![CDATA[9811号]]></number><aoiname><![CDATA[联想大厦]]></aoiname><poiname><![CDATA[联想大厦]]></poiname></revergeo></DRA><COA><eab>1</eab><ctl>144723967</ctl><suc>1</suc></COA></Cell_Req>";
        response.setHeader("Content-Type", "application/octet-stream;charset=UTF-8");
        OutputStream os = response.getOutputStream();
        os.write(xml.getBytes("UTF-8"));
        os.flush();
        os.close();
    }
}
