package com.tung7.ex.repository.aop.service;

import com.tung7.ex.repository.aop.AuditLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Service
@Transactional
public class AuditTestService {
    @AuditLog(type = "1", description = "删除所有角色")
    public String deleteAllRole() {
        System.out.println("deleteAllRole success!");
        return "hahah delete";
    }

    @AuditLog(type = "1", description = "更新角色")
    public String update(Long rid) {
        return "hahah updateRole";
    }

    @AuditLog(type = "1", description = "更新用户")
    public String update(long rid) {
        if (rid == 0) {
            throw new IllegalArgumentException("参数错误，不能为0");
        }
        return "hahah updateUser";
    }
}
