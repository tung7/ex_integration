package com.tung7.ex.repository.aop;

import java.lang.annotation.*;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2017/4/13.
 * @update
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditLog {
    String OPT_LEVEL = "optLevel";
    String SAFT_LEVEL = "saveLevel";

    String description();
    String type();
    String trueWhen() default "";

}