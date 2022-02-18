package com.juelunn.lightcloud.strategy.impl;

import com.alibaba.fastjson.JSON;

import com.juelunn.common.config.QQConfigProperties;
import com.juelunn.common.constant.SocialLoginConst;
import com.juelunn.common.dto.QQUserInfoDTO;
import com.juelunn.common.dto.SocialTokenDTO;
import com.juelunn.common.enums.LoginTypeEnum;
import com.juelunn.common.enums.StatusCodeEnum;
import com.juelunn.common.exception.BizException;
import com.juelunn.common.util.CommonUtils;
import com.juelunn.common.vo.QQLoginVO;
import com.juelunn.common.dto.QQTokenDTO;
import com.juelunn.common.dto.SocialUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * qq登录策略实现
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/28
 */
@Service("qqLoginStrategyImpl")
public class QQLoginStrategyImpl extends com.juelunn.common.strategy.impl.AbstractSocialLoginStrategyImpl {
    @Autowired
    private QQConfigProperties qqConfigProperties;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(String data) {
        QQLoginVO qqLoginVO = JSON.parseObject(data, QQLoginVO.class);
        // 校验QQ token信息
        checkQQToken(qqLoginVO);
        // 返回token信息
        return SocialTokenDTO.builder()
                .openId(qqLoginVO.getOpenId())
                .accessToken(qqLoginVO.getAccessToken())
                .loginType(LoginTypeEnum.QQ.getType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        // 定义请求参数
        Map<String, String> formData = new HashMap<>(3);
        formData.put(SocialLoginConst.QQ_OPEN_ID, socialTokenDTO.getOpenId());
        formData.put(SocialLoginConst.ACCESS_TOKEN, socialTokenDTO.getAccessToken());
        formData.put(SocialLoginConst.OAUTH_CONSUMER_KEY, qqConfigProperties.getAppId());
        // 获取QQ返回的用户信息
        QQUserInfoDTO qqUserInfoDTO = JSON.parseObject(restTemplate.getForObject(qqConfigProperties.getUserInfoUrl(), String.class, formData), QQUserInfoDTO.class);
        // 返回用户信息
        return SocialUserInfoDTO.builder()
                .nickname(Objects.requireNonNull(qqUserInfoDTO).getNickname())
                .avatar(qqUserInfoDTO.getFigureurl_qq_1())
                .build();
    }

    /**
     * 校验qq token信息
     *
     * @param qqLoginVO qq登录信息
     */
    private void checkQQToken(QQLoginVO qqLoginVO) {
        // 根据token获取qq openId信息
        Map<String, String> qqData = new HashMap<>(1);
        qqData.put(SocialLoginConst.ACCESS_TOKEN, qqLoginVO.getAccessToken());
        try {
            String result = restTemplate.getForObject(qqConfigProperties.getCheckTokenUrl(), String.class, qqData);
            QQTokenDTO qqTokenDTO = JSON.parseObject(CommonUtils.getBracketsContent(Objects.requireNonNull(result)), QQTokenDTO.class);
            System.out.println(qqLoginVO.getOpenId() + "<====>" + qqTokenDTO.getOpenid());
            // 判断openId是否一致
            if (!qqLoginVO.getOpenId().equals(qqTokenDTO.getOpenid())) {
                throw new BizException(StatusCodeEnum.QQ_LOGIN_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(StatusCodeEnum.QQ_LOGIN_ERROR);
        }
    }

}
