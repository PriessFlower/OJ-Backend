CREATE DATABASE IF NOT EXISTS `sb-oj` DEFAULT CHARACTER SET utf8mb4;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oj_problem
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem`;
CREATE TABLE `oj_problem`
(
    `id`           bigint(20)                                                     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `title`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '标题',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '内容',
    `tags`         varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '标签列表（json 数组）',
    `difficulty`   tinyint(4)                                                     NULL     DEFAULT NULL COMMENT '题目难度，0简单，1中等，2困难',
    `answer`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '题目答案',
    `submit_num`   int(11)                                                        NOT NULL DEFAULT 0 COMMENT '题目提交数',
    `accepted_num` int(11)                                                        NOT NULL DEFAULT 0 COMMENT '题目通过数',
    `judge_case`   text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '判题用例（json 数组）',
    `judge_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          NULL COMMENT '判题配置（json 对象）',
    `thumb_num`    int(11)                                                        NOT NULL DEFAULT 0 COMMENT '点赞数',
    `favour_num`   int(11)                                                        NOT NULL DEFAULT 0 COMMENT '收藏数',
    `create_time`  datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `creator`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '创建人（id）',
    `update_time`  datetime                                                       NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `updater`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '更新人（id）',
    `del_flag`     tinyint(4)                                                     NOT NULL DEFAULT 0 COMMENT '逻辑删除，0未删除，1删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1770353538222379010
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '题目'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_problem_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_submit`;
CREATE TABLE `oj_problem_submit`
(
    `id`         bigint(20)                                                    NOT NULL AUTO_INCREMENT COMMENT 'id',
    `language`   varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编程语言',
    `code`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NOT NULL COMMENT '用户代码',
    `judgeInfo`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         NULL COMMENT '判题信息（json 对象）',
    `status`     int(11)                                                       NOT NULL DEFAULT 0 COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
    `questionId` bigint(20)                                                    NOT NULL COMMENT '题目 id',
    `userId`     bigint(20)                                                    NOT NULL COMMENT '创建用户 id',
    `createTime` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updateTime` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `isDelete`   tinyint(4)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_questionId` (`questionId` ASC) USING BTREE,
    INDEX `idx_userId` (`userId` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1770361965266980867
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '题目提交'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT 'id',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL DEFAULT NULL COMMENT '角色名称',
    `remark`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `dept_id`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '部门ID',
    `creator`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `updater`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_dept_id` (`dept_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色管理'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`
(
    `id`              bigint(20)                                                    NOT NULL COMMENT 'id',
    `role_id`         bigint(20)                                                    NULL DEFAULT NULL COMMENT '角色ID',
    `permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单项ID',
    `create_time`     datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_role_id` (`role_id` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色数据权限'
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
  COLLATE = utf8mb4_general_ci COMMENT = '角色用户关系'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          bigint(20)                                                    NOT NULL COMMENT 'id',
    `nick_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `username`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
    `password`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
    `salt`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐值',
    `real_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    `avatar`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `gender`      tinyint(3) UNSIGNED                                           NULL DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
    `email`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `mobile`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `status`      tinyint(4)                                                    NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
    `creator`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '创建者',
    `create_time` datetime                                                      NULL DEFAULT NULL COMMENT '创建时间',
    `updater`     bigint(20)                                                    NULL DEFAULT NULL COMMENT '更新者',
    `update_time` datetime                                                      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uk_username` (`username` ASC) USING BTREE,
    INDEX `idx_create_date` (`create_time` ASC) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '系统用户'
  ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
