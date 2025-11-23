// common-core/src/main/java/com/linshiyi/common/annotation/RequireLogin.java
package com.linshiyi.core.annotation;

import java.lang.annotation.*;

/**
 * 标记需要登录才能访问的接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireLogin {
}