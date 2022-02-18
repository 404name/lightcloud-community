package com.juelunn.lightcloud.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juelunn.common.entity.Role;
import com.juelunn.common.vo.PageResult;
import com.juelunn.common.dto.RoleDTO;
import com.juelunn.common.dto.UserRoleDTO;
import com.juelunn.common.vo.ConditionVO;
import com.juelunn.common.vo.RoleVO;

import java.util.List;

/**
 * 角色服务
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/08/10
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取用户角色选项
     *
     * @return 角色
     */
    List<UserRoleDTO> listUserRoles();

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    PageResult<RoleDTO> listRoles(ConditionVO conditionVO);

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 删除角色
     * @param roleIdList 角色id列表
     */
    void deleteRoles(List<Integer> roleIdList);

}
