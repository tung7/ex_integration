package com.tung7.ex.repository.json.util;

import java.lang.annotation.*;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2018/3/7.
 * @update
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Dimension {
    String valueType();
}
