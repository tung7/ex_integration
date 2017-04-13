package com.tung7.ex.repository.apns;

import com.tung7.ex.repository.apns.service.IApnsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
