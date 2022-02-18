package com.juelunn.lightcloud.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juelunn.common.entity.OperationLog;
import com.juelunn.common.dto.OperationLogDTO;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.vo.ConditionVO;

/**
 * 操作日志服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/29
 */
public interface OperationLogService extends IService<OperationLog> {

    /**
     * 查询日志列表
     *
     * @param conditionVO 条件
     * @return 日志列表
     */
    PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO);

}
