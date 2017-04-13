package com.tung7.ex.repository.aop.dao;

import com.tung7.ex.repository.aop.domain.AuditLogBean;
import org.springframework.stereotype.Repository;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Repository
public class AuditLogDao {
   public void save(AuditLogBean bean) {
       System.out.println("====save log : " + bean + " to db");
   }
}
