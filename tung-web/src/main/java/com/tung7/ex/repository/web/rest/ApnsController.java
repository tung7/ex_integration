package com.tung7.ex.repository.web.rest;

import com.clevertap.apns.ApnsClient;
import com.clevertap.apns.Notification;
import com.clevertap.apns.NotificationResponse;
import com.clevertap.apns.NotificationResponseListener;
import com.tung7.ex.repository.web.service.IApnsService;
import com.tung7.ex.repository.web.service.ILockService;
import net.sf.json.JSONObject;
import okhttp3.Dispatcher;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.charset.Charset;

/**
 * @author Tung
 * @version 1.0
 * @date 2016/12/19
 * @update
 */
@Controller
public class ApnsController {
   @Autowired
    IApnsService apnsService;




    @RequestMapping(value = "apns-test",method = RequestMethod.GET)
    public String testUI() {
        return "testUI";
    }

    @RequestMapping(value = "apns-test",method = RequestMethod.POST)
    public String testApns() {
        // ALPN callback dropped: SPDY and HTTP/2 are disabled. Is alpn-boot on the boot class path?
        int i = 0;
        while(i < 2) {
            i++;
            apnsService.pushInAsync("Sequence ID: " + i);
        }
        return "redirect:/apns-test";
    }



}
