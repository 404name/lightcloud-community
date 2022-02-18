package com.juelunn.lightcloud.module.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juelunn.common.entity.OperationLog;
import org.springframework.stereotype.Repository;


/**
 * 操作日志
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/10
 */
@Repository
public interface OperationLogDao extends BaseMapper<OperationLog> {
}
