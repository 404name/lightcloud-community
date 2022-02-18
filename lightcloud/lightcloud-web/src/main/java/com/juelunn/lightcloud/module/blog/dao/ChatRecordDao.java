package com.juelunn.lightcloud.module.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juelunn.common.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 聊天记录
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/10
 */
@Repository
@Mapper
public interface ChatRecordDao extends BaseMapper<ChatRecord> {
}
