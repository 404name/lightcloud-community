package com.juelunn.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/28
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * @return 操作类型
     */
    String optType() default "";

}
