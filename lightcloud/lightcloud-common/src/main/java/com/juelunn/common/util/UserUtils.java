package com.juelunn.common.util;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


/**
 * 用户工具类
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/10
 */
@Component
public class UserUtils {

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static Object getLoginUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
