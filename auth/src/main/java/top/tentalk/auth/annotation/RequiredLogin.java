package top.tentalk.auth.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口需要登录才能访问
 *
 * @author jefferwang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredLogin {

    /**
     * 是否必须校验登录态
     */
    boolean skip() default false;


}
