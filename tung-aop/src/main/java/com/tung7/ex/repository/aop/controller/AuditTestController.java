package com.tung7.ex.repository.aop.controller;

import com.tung7.ex.repository.aop.service.AuditTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Controller
public class AuditTestController {
    @Autowired
    AuditTestService auditTestService;

    @RequestMapping("/audit/custom_anno/test")
    @ResponseBody
    public String deleteAllRole() {
        return auditTestService.deleteAllRole();
    }

    @RequestMapping("/audit/custom_anno/update_role/{rid}")
    @ResponseBody
    public String updateRole(@PathVariable("rid") Long rid) {
        return auditTestService.update(rid);
    }
    @RequestMapping("/audit/custom_anno/update_user/{uid}")
    @ResponseBody
    public String updateUser(@PathVariable("uid") int uid) {
        return auditTestService.update(uid);
    }
}
