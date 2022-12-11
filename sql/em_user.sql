/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : em_user

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 10/12/2022 19:50:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `cinema_ID` bigint NULL DEFAULT NULL COMMENT '电影院ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员名字',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员登录账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员登录密码',
  `power` int NULL DEFAULT NULL COMMENT '权限等级 1000表示最高管理员 1表示电影院管理员',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1, 0, 'root', '15868662937', '123', 100, '2022-11-29 22:05:27', '2022-11-29 22:05:27', 0);
INSERT INTO `administrator` VALUES (2, 1, 'admin', 'admin', 'admin', 1, '2022-12-02 19:09:53', '2022-12-02 19:09:53', 0);

-- ----------------------------
-- Table structure for oauths
-- ----------------------------
DROP TABLE IF EXISTS `oauths`;
CREATE TABLE `oauths`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `user_ID` bigint NOT NULL COMMENT '对应user表中的用户ID',
  `oauth_type` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方登陆类型 weibo、qq、wechat 等',
  `oauth_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '第三方 uid 、openid 等',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `is_delete` int NOT NULL DEFAULT 0 COMMENT '是否删除 0不删除 1删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '第三方信息用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauths
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID,递增',
  `phone` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` char(22) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码最长22个字',
  `nick_name` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称最长16个字',
  `power` int NULL DEFAULT NULL COMMENT '用户权限等级 1表示普通用户 2表示Vip用户',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '用户状态 1表示正常用户,0表示封号',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `is_delete` int NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10001, '15868662937', '123', '普通用户1', 1, 'https://img2.woyaogexing.com/2022/11/27/3a1a204db0059162d081d35287508941.jpg', '1', '2022-11-28 11:05:29', '2022-11-29 12:56:02', 0);
INSERT INTO `user` VALUES (10002, '15868662900', '123', '普通用户2', 1, 'https://img2.woyaogexing.com/2022/11/27/3a1a204db0059162d081d35287508941.jpg', '1', '2022-11-28 12:36:56', '2022-11-29 12:55:59', 0);
INSERT INTO `user` VALUES (10003, '15868662901', '123', 'VIP用户', 2, 'https://img2.woyaogexing.com/2022/11/27/3a1a204db0059162d081d35287508941.jpg', '1', '2022-11-29 11:24:23', '2022-11-29 11:26:06', 0);
INSERT INTO `user` VALUES (10004, '15868662902', '123', '店铺管理员', 2, 'https://img2.woyaogexing.com/2022/11/27/3a1a204db0059162d081d35287508941.jpg', '1', '2022-11-29 11:25:41', '2022-11-29 12:56:08', 0);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `user_ID` int NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表的拓展信息字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 10001, '1415777603@qq.com', '男', '2022-11-28 11:05:12', '2022-11-28 11:05:16', 0);
INSERT INTO `user_info` VALUES (2, 10002, '1415777603', '男', '2022-11-28 12:39:47', '2022-11-28 12:39:47', 0);

SET FOREIGN_KEY_CHECKS = 1;
