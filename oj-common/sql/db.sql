SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 如果 oj-system 数据库存在，就删除，再创建
DROP DATABASE IF EXISTS `oj-system`;
CREATE DATABASE IF NOT EXISTS `oj-system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `oj-system`;

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
-- Table structure for oj_post_comment
-- ----------------------------
DROP TABLE IF EXISTS `oj_post_comment`;
CREATE TABLE `oj_post_comment`  (
                                    `id` bigint(20) NOT NULL COMMENT '主键',
                                    `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父id',
                                    `post_id` bigint(20) NULL DEFAULT NULL COMMENT '帖子id',
                                    `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
                                    `author_id` bigint(20) NULL DEFAULT NULL COMMENT '作者id',
                                    `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
                                    `del_flag` tinyint(4) NULL DEFAULT NULL COMMENT '逻辑删除（0-未删除，1-已删除）',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '帖子评论' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 150775088576757761 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子收藏' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 151196182894936065 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '帖子点赞' ROW_FORMAT = Dynamic;

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
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (138409947809300480, 'dMbMJ3hZNX-Snipaste_2024-04-09_10-17-42.png', 'zrxzrx-123615944728158208/2024-04-18/dMbMJ3hZNX-Snipaste_2024-04-09_10-17-42.png', 4791686, '123615944728158208', '2024-04-18 11:32:43', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138458509871939584, 'DNCSEl6iUp-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-18/DNCSEl6iUp-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-18 14:45:41', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138464038119800832, 'LqUopcWW4i-20220520210604_2423f.jpeg', 'zrxzrx-123615944728158208/2024-04-18/LqUopcWW4i-20220520210604_2423f.jpeg', 15532, '123615944728158208', '2024-04-18 15:07:39', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138464091458764800, 'JEKxuWX7tz-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-18/JEKxuWX7tz-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-18 15:07:52', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138729555334381568, 'pMQhbKMjN7-20220520210608_6b474.jpeg', 'zrxzrx-123615944728158208/2024-04-19/pMQhbKMjN7-20220520210608_6b474.jpeg', 24208, '123615944728158208', '2024-04-19 08:42:43', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138729567233622016, 'rDhUtKTcAA-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-19/rDhUtKTcAA-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-19 08:42:46', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138750448555085824, 'ebJc_6ARMz-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-19/ebJc_6ARMz-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-19 10:05:45', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138750459305086976, 'vS4JzK82wK-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-19/vS4JzK82wK-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-19 10:05:47', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138752372486524928, 'nAKLy30fes-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-19/nAKLy30fes-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-19 10:13:23', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (138752399086800896, '0I0D2L0XFj-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-19/0I0D2L0XFj-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-19 10:13:30', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648226258104320, 'JQlk97drAD-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/JQlk97drAD-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:33:12', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648277768351744, '0G-tjjc1xT-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/0G-tjjc1xT-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:33:24', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648376774897664, 'wafQFYvTqy-20220612164733_72d8b.jpg', 'zrxzrx-123615944728158208/2024-04-21/wafQFYvTqy-20220612164733_72d8b.jpg', 34352, '123615944728158208', '2024-04-21 21:33:48', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648520471752704, 'UE2V-UflEJ-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/UE2V-UflEJ-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:34:22', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648619927089152, 'scF-rzVk8E-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/scF-rzVk8E-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:34:46', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648907585040384, 'BewCotXzeU-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/BewCotXzeU-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:35:54', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648929017933824, 'fxEA-uUNrt-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/fxEA-uUNrt-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:35:59', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139648961498624000, 'LIpiVrNY5e-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/LIpiVrNY5e-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:36:07', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649026535501824, 'urFxUaJWSt-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/urFxUaJWSt-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:36:22', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649195188465664, '43XKa_7VOM-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/43XKa_7VOM-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:37:03', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649295587520512, 'GzPfFUeWED-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/GzPfFUeWED-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:37:27', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649457479266304, 'FyJXjLRYE2-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/FyJXjLRYE2-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:38:05', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649489343393792, 'M1F9F1HsW0-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/M1F9F1HsW0-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:38:13', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649499283894272, '8asEvBjFTX-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/8asEvBjFTX-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:38:15', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139649622256693248, 'Ys6XJ4Tkti-20220520210604_2423f.jpeg', 'zrxzrx-123615944728158208/2024-04-21/Ys6XJ4Tkti-20220520210604_2423f.jpeg', 15532, '123615944728158208', '2024-04-21 21:38:45', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139650029934653440, 'qVLlwRgJ7A-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/qVLlwRgJ7A-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:40:22', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139651333763411968, 'EdRK5Ps_WM-20220801204303_cef03.thumb.400_0.jpeg', 'zrxzrx-123615944728158208/2024-04-21/EdRK5Ps_WM-20220801204303_cef03.thumb.400_0.jpeg', 50818, '123615944728158208', '2024-04-21 21:45:33', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139651774903529472, 'QlN4xdv7K7-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/QlN4xdv7K7-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:47:18', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139653488972967936, 'c8Z-3oBMB1-20220612164733_72d8b.jpg', 'zrxzrx-123615944728158208/2024-04-21/c8Z-3oBMB1-20220612164733_72d8b.jpg', 34352, '123615944728158208', '2024-04-21 21:54:06', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139654027072811008, 'gslR7IpaLW-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/gslR7IpaLW-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 21:56:15', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139656958853558272, '-kjn1FPII_-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/-kjn1FPII_-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 22:07:54', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139665598989774848, 'Dqc8Db9v4q-你的名字动漫壁纸_彼岸壁纸.jpg', 'zrxzrx-123615944728158208/2024-04-21/Dqc8Db9v4q-你的名字动漫壁纸_彼岸壁纸.jpg', 1100030, '123615944728158208', '2024-04-21 22:42:14', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139665783660785664, 'HrMQ9L7zXP-报纸墙 可爱马尾辫少女粉色长发动漫壁纸_彼岸壁纸.jpg', 'zrxzrx-123615944728158208/2024-04-21/HrMQ9L7zXP-报纸墙 可爱马尾辫少女粉色长发动漫壁纸_彼岸壁纸.jpg', 1625386, '123615944728158208', '2024-04-21 22:42:58', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139665898144313344, 'lHrFioqYuD-《你的名字 》立花泷 宫水三叶桌面壁纸_彼岸壁纸.jpg', 'zrxzrx-123615944728158208/2024-04-21/lHrFioqYuD-《你的名字 》立花泷 宫水三叶桌面壁纸_彼岸壁纸.jpg', 487288, '123615944728158208', '2024-04-21 22:43:25', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139667920356683776, 'ODEai8Y__G-报纸墙 可爱马尾辫少女粉色长发动漫壁纸_彼岸壁纸.jpg', 'zrxzrx-123615944728158208/2024-04-21/ODEai8Y__G-报纸墙 可爱马尾辫少女粉色长发动漫壁纸_彼岸壁纸.jpg', 1625386, '123615944728158208', '2024-04-21 22:51:27', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (139667980372979712, 'zh27l-STcH-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-21/zh27l-STcH-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-21 22:51:41', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (140386219839197184, 'gT-khenkds-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-23/gT-khenkds-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-23 22:25:43', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (140386233034477568, 'v7E8abpmwO-你的名字动漫壁纸_彼岸壁纸.jpg', 'zrxzrx-123615944728158208/2024-04-23/v7E8abpmwO-你的名字动漫壁纸_彼岸壁纸.jpg', 1100030, '123615944728158208', '2024-04-23 22:25:46', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (140386233906892800, 'wGbHrPqS0w-你的名字动漫壁纸_彼岸壁纸.jpg', 'zrxzrx-123615944728158208/2024-04-23/wGbHrPqS0w-你的名字动漫壁纸_彼岸壁纸.jpg', 1100030, '123615944728158208', '2024-04-23 22:25:46', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (140386242610073600, 'LdO6p7AzXT-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-23/LdO6p7AzXT-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-23 22:25:48', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (140387950828523520, '_6jBennMOo-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-23/_6jBennMOo-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-23 22:32:36', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (141305746168532992, 'tI3REATa-u-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-04-26/tI3REATa-u-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-04-26 11:19:35', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (141310083246682112, 'Hw03mO8KhL-20220520210604_2423f.jpeg', 'admin333-135186678792712192/2024-04-26/Hw03mO8KhL-20220520210604_2423f.jpeg', 15532, '135186678792712192', '2024-04-26 11:36:49', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (141518276442050560, 'b6q7YxVUAP-20220520210608_6b474.jpeg', 'admin333-135186678792712192/2024-04-27/b6q7YxVUAP-20220520210608_6b474.jpeg', 24208, '135186678792712192', '2024-04-27 01:24:06', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (141740359113506816, 'ZoraVSUNOK-20220709150824_97667.thumb.400_0.jpg', 'admin333-135186678792712192/2024-04-27/ZoraVSUNOK-20220709150824_97667.thumb.400_0.jpg', 53461, '135186678792712192', '2024-04-27 16:06:35', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (144617952636567552, 'tQtPBNPyni-20220709150824_97667.thumb.400_0.jpg', 'zrxzrx-123615944728158208/2024-05-05/tQtPBNPyni-20220709150824_97667.thumb.400_0.jpg', 53461, '123615944728158208', '2024-05-05 14:41:07', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (147813268387889152, '_Y_0nc4wHM-OIP-C.jpg', 'zrxzrx-123615944728158208/2024-05-14/_Y_0nc4wHM-OIP-C.jpg', 14879, '123615944728158208', '2024-05-14 10:18:09', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (147813338562789376, '1eS_m06Rwc-R-C.jpg', 'zrxzrx-123615944728158208/2024-05-14/1eS_m06Rwc-R-C.jpg', 561287, '123615944728158208', '2024-05-14 10:18:26', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (147815310816804864, 'cFdSrOdKoa-harmonyNext.jpg', 'zrxzrx-123615944728158208/2024-05-14/cFdSrOdKoa-harmonyNext.jpg', 14854, '123615944728158208', '2024-05-14 10:26:16', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (147866248306778112, 'SvSTvxsAhA-harmonyNext.jpg', 'zrxzrx-123615944728158208/2024-05-14/SvSTvxsAhA-harmonyNext.jpg', 14854, '123615944728158208', '2024-05-14 13:48:41', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (148008604385509376, '6Rg-4xlS5K-祁同伟.jpg', 'admin333-135186678792712192/2024-05-14/6Rg-4xlS5K-祁同伟.jpg', 80698, '135186678792712192', '2024-05-14 23:14:21', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (148008933671927808, '0Qt2P1-jvd-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 'admin123-124716087158337536/2024-05-14/0Qt2P1-jvd-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 17100, '124716087158337536', '2024-05-14 23:15:40', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (148011162592186368, 'jy6kIu-xq2-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 'admin123-124716087158337536/2024-05-14/jy6kIu-xq2-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 17100, '124716087158337536', '2024-05-14 23:24:31', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (148011228803469312, 'BLvrgjDmAO-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 'admin123-124716087158337536/2024-05-14/BLvrgjDmAO-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 17100, '124716087158337536', '2024-05-14 23:24:47', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (148013992296157184, '6NatnuYO1r-沙瑞金.jpg', 'admin-1067246875800000001/2024-05-14/6NatnuYO1r-沙瑞金.jpg', 32881, '1067246875800000001', '2024-05-14 23:35:46', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (148015123818713088, 'ajoiaLvb6q-马老师.jpg', 'zrxzrx-123615944728158208/2024-05-14/ajoiaLvb6q-马老师.jpg', 14312, '123615944728158208', '2024-05-14 23:40:16', NULL, NULL, 0);
INSERT INTO `sys_file` VALUES (149726602648678400, 'W3CtjgMinc-马老师.jpg', 'wuhudasima-149726522734604288/2024-05-19/W3CtjgMinc-马老师.jpg', 14312, '149726522734604288', '2024-05-19 17:01:04', NULL, NULL, 0);

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

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'super-admin', 0, '超级管理员', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (33554788141000117, 'admin', 1, '管理员', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (33615406334000157, 'user', 2, '普通用户', NULL, 123615944728158208, '2024-04-25 14:37:46', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (34724366549000174, 'tourist', 3, '游客', NULL, 123615944728158208, '2024-05-08 10:40:27', NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (34724813704000117, 'second-admin', 1, '二级管理员', NULL, 123615944728158208, '2024-05-08 10:47:54', NULL, NULL, 1);

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
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1, 'super-admin', '2024-03-08 16:28:25');

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

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (141517931158556673, 33554788141000117, 124716087158337536, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_user` VALUES (141518310273306624, 1, 123615944728158208, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_user` VALUES (141518310273306625, 33554788141000117, 123615944728158208, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_user` VALUES (142031551902605312, 33615406334000157, 142031551613198336, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_user` VALUES (145024916684275712, 33615406334000157, 135186678792712192, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_user` VALUES (145647908846870528, 33554788141000117, 1067246875800000001, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_user` VALUES (149726522805907456, 33615406334000157, 149726522734604288, NULL, NULL, NULL, NULL);

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

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (123615944728158208, '芜湖大司马', 'zrxzrx', '54d5a89000c9c46ed3d07c5ad765e5c6', 'PxXvNDTy', '大司马', 'http://127.0.0.1:9005/oj-bucket/zrxzrx-123615944728158208/2024-05-14/ajoiaLvb6q-马老师.jpg', 0, '这波是肉蛋葱鸡', '1972814429@qq.com', '18154660615', 0, NULL, '2024-02-01 11:19:50', 123615944728158208, '2024-05-14 23:46:34');
INSERT INTO `sys_user` VALUES (124716087158337536, '省委高育良', 'admin123', 'f18f561a0d1a4d084607055693fe899e', 'msb28Yoe', '高育良', 'http://127.0.0.1:9005/oj-bucket/admin123-124716087158337536/2024-05-14/BLvrgjDmAO-v2-cfe3fda988aba24ca19d0467e20114cb_720w.jpg', 1, '工作时要称职务！', '1972814429@qq.com', '15726109130', 0, NULL, '2024-05-10 21:52:20', 124716087158337536, '2024-05-14 23:33:18');
INSERT INTO `sys_user` VALUES (135186678792712192, '省公安厅厅长祁同伟', 'admin333', '3a97f5aea471c52d060d5481982057de', 'Vr7uaJMY', '祁同伟', 'http://127.0.0.1:9005/oj-bucket/admin333-135186678792712192/2024-05-14/6Rg-4xlS5K-祁同伟.jpg', 0, '改变我的不是知识，是权利', '1972814429@qq.com', '18154660615', 0, NULL, '2024-05-10 21:52:23', 135186678792712192, '2024-05-14 23:37:07');
INSERT INTO `sys_user` VALUES (149726522734604288, '芜湖大司马', 'wuhudasima', '37a694037fc040c379852de6112e3881', 'd3biczmy', '韩金轮', 'http://127.0.0.1:9005/oj-bucket/wuhudasima-149726522734604288/2024-05-19/W3CtjgMinc-马老师.jpg', NULL, '对面打野在哪里？如果你答不上来，那对不起，我不能认可你🫠', '15726109130@qq.com', '15726109130', 0, NULL, NULL, 149726522734604288, '2024-05-19 17:02:54');
INSERT INTO `sys_user` VALUES (1067246875800000001, '省委沙瑞金', 'admin', '54d5a89000c9c46ed3d07c5ad765e5c6', 'PxXvNDTy', '沙瑞金', 'http://127.0.0.1:9005/oj-bucket/admin-1067246875800000001/2024-05-14/6NatnuYO1r-沙瑞金.jpg', 0, '我不想干的事，别人也干不成', '1972814429@qq.com', '13612345678', 0, 1067246875800000001, '2024-03-08 15:21:37', 1067246875800000001, '2024-05-14 23:36:00');

SET FOREIGN_KEY_CHECKS = 1;
