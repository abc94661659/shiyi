package com.linshiyi.core.annotation;

import java.lang.annotation.*;

/**
 * 用户操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {
    /**
     * 操作描述
     *
     * @return 描述
     */
    String desc() default "";

    /**
     * 资源类型
     *
     * @return 资源类型
     */
    String resourceType() default "";

    /**
     * 操作类型
     *
     * @return 操作类型
     */
    String operationType() default "";
}
