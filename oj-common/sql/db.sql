CREATE DATABASE IF NOT EXISTS `sb-oj` DEFAULT CHARACTER SET utf8mb4;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT 'id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci  NULL DEFAULT NULL COMMENT '角色名称',
    `remark`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '部门ID',
    `creator`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `updater`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_dept_id` (`dept_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色管理'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`              bigint(20)                                                    NOT NULL COMMENT 'id',
    `role_id`         bigint(20)                                                    NULL DEFAULT NULL COMMENT '角色ID',
    `permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单项ID',
    `create_time`     datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色数据权限'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`
(
    `id`          bigint(20) NOT NULL COMMENT 'id',
    `role_id`     bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
    `user_id`     bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
    `creator`     bigint(20) NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime   NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id` ASC) USING BTREE,
    INDEX `idx_user_id` (`user_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色用户关系'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20) NOT NULL COMMENT 'id',
    `real_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
    `nick_name`   varchar(255)                                                  DEFAULT NULL COMMENT '昵称',
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
    `salt`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '盐值',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
    `gender`      tinyint(3) unsigned                                           DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
    `email`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
    `dept_id`     bigint(20)                                                    DEFAULT NULL COMMENT '部门ID',
    `status`      tinyint(4)                                                    DEFAULT NULL COMMENT '状态  0：停用   1：正常',
    `creator`     bigint(20)                                                    DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `updater`     bigint(20)                                                    DEFAULT NULL COMMENT '更新者',
    `update_time` datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_create_date` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='系统用户';

SET FOREIGN_KEY_CHECKS = 1;
