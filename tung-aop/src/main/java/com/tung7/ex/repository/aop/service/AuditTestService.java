package com.tung7.ex.repository.aop.service;

import com.tung7.ex.repository.aop.AuditLog;
import org.springframework.stereotype.Service;

/**
 * TODO Complete The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Service
public class AuditTestService {
    @AuditLog(type = "1", description = "删除所有角色")
    public String deleteAllRole() {
        System.out.println("deleteAllRole success!");
        return "hahah delete";
    }

    @AuditLog(type = "1", description = "更新角色")
    public String update(Long rid) {
        System.out.println("updateRole success!");
        return "hahah updateRole";
    }

    @AuditLog(type = "1", description = "更新用户")
    public String update(Integer rid) {
        System.out.println("updateUser success!");
        return "hahah updateUser";
    }
}
