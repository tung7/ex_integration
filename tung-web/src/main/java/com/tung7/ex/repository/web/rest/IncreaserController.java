package com.tung7.ex.repository.web.rest;

import com.tung7.ex.repository.web.service.ILockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Tung
 * @version 1.0
 * @date 2017/1/17
 * @update
 */
@Controller
public class IncreaserController {
    @Autowired
    ILockService lockService;

    @RequestMapping(value = "lock-test",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getGlobalMsgVersion() {
        Long i = lockService.getGlobalMsgVersion();
        return new ResponseEntity<byte[]>(i.toString().getBytes(), new HttpHeaders(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "lock-test/{userId}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> getUserMsgVersion(@PathVariable("userId") Long userId) {
        Long i = lockService.getUserMsgVersion(userId);
        return new ResponseEntity<byte[]>(i.toString().getBytes(), new HttpHeaders(), HttpStatus.CREATED);
    }

}
