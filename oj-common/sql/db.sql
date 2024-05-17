SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 如果 sb-oj数据库存在，就删除，再创建
DROP DATABASE IF EXISTS `sb-oj`;
CREATE DATABASE IF NOT EXISTS `sb-oj` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- ----------------------------
-- Table structure for oj_post
-- ----------------------------
DROP TABLE IF EXISTS `oj_post`;
CREATE TABLE `oj_post`  (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
                            `zone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '帖子分区',
                            `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
                            `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
                            `view_num` int(11) NULL DEFAULT 0 COMMENT '观看数',
                            `thumb_num` int(11) NULL DEFAULT 0 COMMENT '点赞数',
                            `favour_num` int(11) NULL DEFAULT 0 COMMENT '收藏数',
                            `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建用户 id',
                            `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
                            `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_flag` tinyint(4) NULL DEFAULT 0 COMMENT '是否删除',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `idx_userId`(`creator` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148360047005061121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '帖子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_post_favour
-- ----------------------------
DROP TABLE IF EXISTS `oj_post_favour`;
CREATE TABLE `oj_post_favour`  (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `post_id` bigint(20) NOT NULL COMMENT '帖子 id',
                                   `user_id` bigint(20) NOT NULL COMMENT '创建用户 id',
                                   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   PRIMARY KEY (`id`) USING BTREE,
                                   INDEX `idx_postId`(`post_id` ASC) USING BTREE,
                                   INDEX `idx_userId`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148515379371143169 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子收藏' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_post_thumb
-- ----------------------------
DROP TABLE IF EXISTS `oj_post_thumb`;
CREATE TABLE `oj_post_thumb`  (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `post_id` bigint(20) NOT NULL COMMENT '帖子 id',
                                  `user_id` bigint(20) NOT NULL COMMENT '创建用户 id',
                                  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `idx_postId`(`post_id` ASC) USING BTREE,
                                  INDEX `idx_userId`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148515182586982401 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子点赞' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_problem
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem`;
CREATE TABLE `oj_problem`  (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
                               `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
                               `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
                               `difficulty` tinyint(4) NULL DEFAULT NULL COMMENT '题目难度，0简单，1中等，2困难',
                               `ans_language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '题目答案语言',
                               `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目答案',
                               `submit_num` int(11) NULL DEFAULT 0 COMMENT '题目提交数',
                               `accepted_num` int(11) NULL DEFAULT 0 COMMENT '题目通过数',
                               `judge_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '判题用例（json 数组）',
                               `judge_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '判题配置（json 对象）',
                               `thumb_num` int(11) NULL DEFAULT 0 COMMENT '点赞数',
                               `favour_num` int(11) NULL DEFAULT 0 COMMENT '收藏数',
                               `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人（id）',
                               `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `updater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人（id）',
                               `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除，0未删除，1删除',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1770353538222379110 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_problem_comment
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_comment`;
CREATE TABLE `oj_problem_comment`  (
                                       `id` bigint(20) NOT NULL COMMENT '主键',
                                       `tags` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
                                       `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父id',
                                       `problem_id` bigint(20) NULL DEFAULT NULL COMMENT '题目id',
                                       `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
                                       `author_id` bigint(20) NULL DEFAULT NULL COMMENT '作者id',
                                       `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                       `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除（0-未删除，1-已删除）',
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目评论' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oj_problem_submit
-- ----------------------------
DROP TABLE IF EXISTS `oj_problem_submit`;
CREATE TABLE `oj_problem_submit`  (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                      `language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编程语言',
                                      `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户代码',
                                      `judge_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '判题信息（json 对象）',
                                      `code_status` int(11) NULL DEFAULT 0 COMMENT '代码执行状态：0：Accepted，1：Wrong Answer，2：Compile Error，3：out of Memory，4：Time Limit Exceeded，5：Runtime Error，6：System Error',
                                      `question_id` bigint(20) NOT NULL COMMENT '题目 id',
                                      `user_id` bigint(20) NOT NULL COMMENT '创建用户 id',
                                      `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                      `is_delete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
                                      `status` tinyint(4) NOT NULL COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
                                      `output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
                                      PRIMARY KEY (`id`) USING BTREE,
                                      INDEX `idx_questionId`(`question_id` ASC) USING BTREE,
                                      INDEX `idx_userId`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1770361965266980867 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目提交' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
                             `id` bigint(20) NOT NULL COMMENT '主键',
                             `file_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
                             `url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访问路径',
                             `file_size` bigint(20) NOT NULL COMMENT '文件大小',
                             `creator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `updater` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除（0-未删除，1-已删除）',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '云文件信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
                             `id` bigint(20) NOT NULL COMMENT 'id',
                             `role_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色标识',
                             `sort` int(11) NULL DEFAULT NULL COMMENT '排序（当前角色创建角色不可以创建大于自己最大权限的角色）',
                             `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
                             `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
                             `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除（0-正常，1-已删除）',
                             PRIMARY KEY (`id`) USING BTREE,
                             INDEX `idx_dept_id`(`dept_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

INSERT INTO `sb-oj`.`sys_role` (`id`, `role_code`, `sort`, `remark`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `del_flag`) VALUES (1, 'super-admin', 0, '超级管理员', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sb-oj`.`sys_role` (`id`, `role_code`, `sort`, `remark`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `del_flag`) VALUES (33554788141000117, 'admin', 1, '管理员', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sb-oj`.`sys_role` (`id`, `role_code`, `sort`, `remark`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `del_flag`) VALUES (33615406334000157, 'user', 2, '普通用户', NULL, 123615944728158208, '2024-04-25 14:37:46', NULL, NULL, 0);
INSERT INTO `sb-oj`.`sys_role` (`id`, `role_code`, `sort`, `remark`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `del_flag`) VALUES (34724366549000174, 'tourist', 3, '游客', NULL, 123615944728158208, '2024-05-08 10:40:27', NULL, NULL, 0);
INSERT INTO `sb-oj`.`sys_role` (`id`, `role_code`, `sort`, `remark`, `dept_id`, `creator`, `create_time`, `updater`, `update_time`, `del_flag`) VALUES (34724813704000117, 'second-admin', 1, '二级管理员', NULL, 123615944728158208, '2024-05-08 10:47:54', NULL, NULL, 1);


-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
                                        `id` bigint(20) NOT NULL COMMENT 'id',
                                        `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
                                        `permission_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单项ID',
                                        `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        INDEX `idx_role_id`(`role_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色数据权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
                                  `id` bigint(20) NOT NULL COMMENT 'id',
                                  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
                                  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
                                  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
                                  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
                                  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  INDEX `idx_role_id`(`role_id` ASC) USING BTREE,
                                  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色用户关系' ROW_FORMAT = Dynamic;

INSERT INTO `sb-oj`.`sys_role_user` (`id`, `role_id`, `user_id`, `creator`, `create_time`, `updater`, `update_time`) VALUES (141517931158556673, 33554788141000117, 124716087158337536, NULL, NULL, NULL, NULL);
INSERT INTO `sb-oj`.`sys_role_user` (`id`, `role_id`, `user_id`, `creator`, `create_time`, `updater`, `update_time`) VALUES (141518310273306624, 1, 123615944728158208, NULL, NULL, NULL, NULL);
INSERT INTO `sb-oj`.`sys_role_user` (`id`, `role_id`, `user_id`, `creator`, `create_time`, `updater`, `update_time`) VALUES (141518310273306625, 33554788141000117, 123615944728158208, NULL, NULL, NULL, NULL);
INSERT INTO `sb-oj`.`sys_role_user` (`id`, `role_id`, `user_id`, `creator`, `create_time`, `updater`, `update_time`) VALUES (142031551902605312, 33615406334000157, 142031551613198336, NULL, NULL, NULL, NULL);
INSERT INTO `sb-oj`.`sys_role_user` (`id`, `role_id`, `user_id`, `creator`, `create_time`, `updater`, `update_time`) VALUES (145024916684275712, 33615406334000157, 135186678792712192, NULL, NULL, NULL, NULL);
INSERT INTO `sb-oj`.`sys_role_user` (`id`, `role_id`, `user_id`, `creator`, `create_time`, `updater`, `update_time`) VALUES (145647908846870528, 33554788141000117, 1067246875800000001, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
                             `id` bigint(20) NOT NULL COMMENT 'id',
                             `nick_name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
                             `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
                             `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
                             `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐值',
                             `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
                             `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
                             `gender` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
                             `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人简介',
                             `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
                             `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
                             `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：正常   1：停用',
                             `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
                             `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                             `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
                             `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             UNIQUE INDEX `uk_username`(`username` ASC) USING BTREE,
                             INDEX `idx_create_date`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

INSERT INTO `sb-oj`.`sys_user` (`id`, `nick_name`, `username`, `password`, `salt`, `real_name`, `avatar`, `gender`, `introduce`, `email`, `mobile`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (123615944728158208, '芜湖大司马', 'zrxzrx', '54d5a89000c9c46ed3d07c5ad765e5c6', 'PxXvNDTy', '大司马', 'http://127.0.0.1:9005/oj-bucket/zrxzrx-123615944728158208/2024-05-14/ajoiaLvb6q-马老师.jpg', 0, '这波是肉蛋葱鸡', '1972814429@qq.com', '18154660615', 0, NULL, '2024-02-01 11:19:50', 123615944728158208, '2024-05-14 23:46:34');
INSERT INTO `sb-oj`.`sys_user` (`id`, `nick_name`, `username`, `password`, `salt`, `real_name`, `avatar`, `gender`, `introduce`, `email`, `mobile`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (124716087158337536, '省委高育良', 'admin123', 'f18f561a0d1a4d084607055693fe899e', 'msb28Yoe', '高育良', 'http://127.0.0.1:9005/oj-bucket/admin123-124716087158337536/2024-05-14/BLvrgjDmAO-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 1, '工作时要称职务！', '1972814429@qq.com', '15726109130', 0, NULL, '2024-05-10 21:52:20', 124716087158337536, '2024-05-14 23:33:18');
INSERT INTO `sb-oj`.`sys_user` (`id`, `nick_name`, `username`, `password`, `salt`, `real_name`, `avatar`, `gender`, `introduce`, `email`, `mobile`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (135186678792712192, '省公安厅厅长祁同伟', 'admin333', '3a97f5aea471c52d060d5481982057de', 'Vr7uaJMY', '祁同伟', 'http://127.0.0.1:9005/oj-bucket/admin333-135186678792712192/2024-05-14/6Rg-4xlS5K-祁同伟.jpg', 0, '改变我的不是知识，是权利', '1972814429@qq.com', '18154660615', 0, NULL, '2024-05-10 21:52:23', 135186678792712192, '2024-05-14 23:37:07');
INSERT INTO `sb-oj`.`sys_user` (`id`, `nick_name`, `username`, `password`, `salt`, `real_name`, `avatar`, `gender`, `introduce`, `email`, `mobile`, `status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1067246875800000001, '省委沙瑞金', 'admin', '54d5a89000c9c46ed3d07c5ad765e5c6', 'PxXvNDTy', '沙瑞金', 'http://127.0.0.1:9005/oj-bucket/admin-1067246875800000001/2024-05-14/6NatnuYO1r-沙瑞金.jpg', 0, '我不想干的事，别人也干不成', '1972814429@qq.com', '13612345678', 0, 1067246875800000001, '2024-03-08 15:21:37', 1067246875800000001, '2024-05-14 23:36:00');

SET FOREIGN_KEY_CHECKS = 1;
