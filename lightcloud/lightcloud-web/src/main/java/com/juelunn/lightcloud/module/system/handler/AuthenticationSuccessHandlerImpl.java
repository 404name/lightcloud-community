package com.juelunn.lightcloud.module.system.handler;

import com.alibaba.fastjson.JSON;
import com.juelunn.lightcloud.module.system.dao.UserAuthDao;
import com.juelunn.common.dto.UserDetailDTO;
import com.juelunn.common.util.UserUtils;
import com.juelunn.common.dto.UserInfoDTO;
import com.juelunn.common.entity.UserAuth;
import com.juelunn.common.util.BeanCopyUtils;
import com.juelunn.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 登录成功处理
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/28
 */
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        // 返回登录信息
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(((UserDetailDTO) UserUtils.getLoginUser()), UserInfoDTO.class);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.ok(userLoginDTO)));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户信息
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(((UserDetailDTO)UserUtils.getLoginUser()).getId())
                .ipAddress(((UserDetailDTO)UserUtils.getLoginUser()).getIpAddress())
                .ipSource(((UserDetailDTO)UserUtils.getLoginUser()).getIpSource())
                .lastLoginTime(((UserDetailDTO)UserUtils.getLoginUser()).getLastLoginTime())
                .build();
        userAuthDao.updateById(userAuth);
    }

}
