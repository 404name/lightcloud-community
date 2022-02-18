package com.juelunn.lightcloud.module.blog.service;

import com.juelunn.common.entity.FriendLink;
import com.juelunn.common.dto.FriendLinkBackDTO;
import com.juelunn.common.dto.FriendLinkDTO;
import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.juelunn.common.vo.FriendLinkVO;

import java.util.List;

/**
 * 友链服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/29
 */
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * 查看友链列表
     *
     * @return 友链列表
     */
    List<FriendLinkDTO> listFriendLinks();

    /**
     * 查看后台友链列表
     *
     * @param condition 条件
     * @return 友链列表
     */
    PageResult<FriendLinkBackDTO> listFriendLinkDTO(ConditionVO condition);

    /**
     * 保存或更新友链
     *
     * @param friendLinkVO 友链
     */
    void saveOrUpdateFriendLink(FriendLinkVO friendLinkVO);

}
