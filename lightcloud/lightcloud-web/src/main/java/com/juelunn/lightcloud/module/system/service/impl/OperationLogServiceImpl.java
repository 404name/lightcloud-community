package com.juelunn.lightcloud.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juelunn.lightcloud.module.system.dao.OperationLogDao;
import com.juelunn.common.dto.OperationLogDTO;
import com.juelunn.common.util.PageUtils;
import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.entity.OperationLog;
import com.juelunn.lightcloud.module.system.service.OperationLogService;
import com.juelunn.common.util.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/08
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLog> implements OperationLogService {

    @Override
    public PageResult<OperationLogDTO> listOperationLogs(ConditionVO conditionVO) {
        Page<OperationLog> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        // 查询日志列表
        Page<OperationLog> operationLogPage = this.page(page, new LambdaQueryWrapper<OperationLog>()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptModule, conditionVO.getKeywords())
                .or()
                .like(StringUtils.isNotBlank(conditionVO.getKeywords()), OperationLog::getOptDesc, conditionVO.getKeywords())
                .orderByDesc(OperationLog::getId));
        List<OperationLogDTO> operationLogDTOList = BeanCopyUtils.copyList(operationLogPage.getRecords(), OperationLogDTO.class);
        return new PageResult<>(operationLogDTOList, (int) operationLogPage.getTotal());
    }

}
