package com.juelunn.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 *
 * @author CTGU_LLZ(404name)
 * @date 2021/07/28
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 管理员
     */
    ADMIN(1, "管理员", "admin"),
    /**
     * 普通用户
     */
    USER(2, "用户", "user"),
    /**
     * 测试账号
     */
    TEST(3, "游客", "test");

    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 描述
     */
    private final String name;

    /**
     * 权限标签
     */
    private final String label;

}
