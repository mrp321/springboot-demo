/*
 Navicat Premium Data Transfer

 Source Server         : localhostMysql
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : springboot-test

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 03/05/2018 19:19:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pwd_rec
-- ----------------------------
DROP TABLE IF EXISTS `pwd_rec`;
CREATE TABLE `pwd_rec`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '用户id',
  `pwd` varchar(32) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '密码',
  `createTime` datetime(0) NOT NULL COMMENT '创建日期',
  `createUser` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '创建者',
  `updateTime` datetime(0) NOT NULL COMMENT '更新日期',
  `updateUser` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL COMMENT '更新者',
  `version` int(10) NOT NULL COMMENT '版本号(每次更新,+1)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `sex` int(1) DEFAULT NULL COMMENT '性别(0:男;1:女;2:其他)',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `pwdRec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码履历',
  `pwdErrCout` int(1) NOT NULL COMMENT '密码错误次数',
  `pwdExpiredDate` datetime(0) NOT NULL COMMENT '密码有效期限',
  `lastLoginDate` datetime(0) DEFAULT NULL COMMENT '最近一次登陆日期',
  `isLock` int(1) NOT NULL DEFAULT 0 COMMENT '账号是否被锁定(0:未锁定;1:已锁定)',
  `createTime` datetime(0) NOT NULL COMMENT '创建日期',
  `createUser` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `updateTime` datetime(0) NOT NULL COMMENT '更新日期',
  `updateUser` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新者',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '版本号(每次更新表都会加1)',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL, '21232f297a57a5a743894a0e4a801fc3', 0, '2018-06-02 10:29:49', '2018-05-03 10:37:50', 0, '2018-05-03 10:29:49', 'admin', '2018-05-03 10:37:50', 'admin', 1);
INSERT INTO `user` VALUES ('root', 'root', '63a9f0ea7bb98050796b649e85481845', NULL, NULL, '63a9f0ea7bb98050796b649e85481845', 5, '2018-06-02 10:29:37', '2018-05-03 10:39:19', 1, '2018-05-03 10:29:36', 'root', '2018-05-03 10:39:19', 'root', 6);
INSERT INTO `user` VALUES ('user1', 'user1', '24c9e15e52afc47c225b757e7bee1f9d', NULL, NULL, '24c9e15e52afc47c225b757e7bee1f9d', 0, '2018-06-02 10:30:37', NULL, 0, '2018-05-03 10:30:36', 'user1', '2018-05-03 10:30:36', 'user1', 0);
INSERT INTO `user` VALUES ('user2', 'user2', '7e58d63b60197ceb55a1c487989a3720', NULL, NULL, '7e58d63b60197ceb55a1c487989a3720', 0, '2018-04-01 10:30:44', '2018-03-01 10:37:44', 0, '2018-03-01 10:30:44', 'user2', '2018-03-01 10:37:44', 'user2', 1);
INSERT INTO `user` VALUES ('user3', 'user3', '92877af70a45fd6a2ed7fe81e1236b78', NULL, NULL, '92877af70a45fd6a2ed7fe81e1236b78', 0, '2018-06-02 10:30:52', NULL, 0, '2018-05-03 10:30:51', 'user3', '2018-05-03 10:30:51', 'user3', 0);

-- ----------------------------
-- Table structure for user_copy1
-- ----------------------------
DROP TABLE IF EXISTS `user_copy1`;
CREATE TABLE `user_copy1`  (
  `userId` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `sex` int(1) DEFAULT NULL COMMENT '性别(0:男;1:女;2:其他)',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `pwdRec` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码履历',
  `pwdErrCout` int(1) NOT NULL COMMENT '密码错误次数',
  `pwdExpiredDate` datetime(0) NOT NULL COMMENT '密码有效期限',
  `lastLoginDate` datetime(0) DEFAULT NULL COMMENT '最近一次登陆日期',
  `isLock` int(1) NOT NULL DEFAULT 0 COMMENT '账号是否被锁定(0:未锁定;1:已锁定)',
  `createTime` datetime(0) NOT NULL COMMENT '创建日期',
  `createUser` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `updateTime` datetime(0) NOT NULL COMMENT '更新日期',
  `updateUser` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新者',
  `version` int(10) NOT NULL DEFAULT 0 COMMENT '版本号(每次更新表都会加1)',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_copy1
-- ----------------------------
INSERT INTO `user_copy1` VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, NULL, '21232f297a57a5a743894a0e4a801fc3', 0, '2018-06-02 10:29:49', '2018-05-03 10:37:50', 0, '2018-05-03 10:29:49', 'admin', '2018-05-03 10:37:50', 'admin', 1);
INSERT INTO `user_copy1` VALUES ('root', 'root', '63a9f0ea7bb98050796b649e85481845', NULL, NULL, '63a9f0ea7bb98050796b649e85481845', 5, '2018-06-02 10:29:37', '2018-05-03 10:39:19', 1, '2018-05-03 10:29:36', 'root', '2018-05-03 10:39:19', 'root', 6);
INSERT INTO `user_copy1` VALUES ('user1', 'user1', '24c9e15e52afc47c225b757e7bee1f9d', NULL, NULL, '24c9e15e52afc47c225b757e7bee1f9d', 0, '2018-06-02 10:30:37', NULL, 0, '2018-05-03 10:30:36', 'user1', '2018-05-03 10:30:36', 'user1', 0);
INSERT INTO `user_copy1` VALUES ('user2', 'user2', '7e58d63b60197ceb55a1c487989a3720', NULL, NULL, '7e58d63b60197ceb55a1c487989a3720', 0, '2018-04-01 10:30:44', '2018-03-01 10:37:44', 0, '2018-03-01 10:30:44', 'user2', '2018-03-01 10:37:44', 'user2', 1);
INSERT INTO `user_copy1` VALUES ('user3', 'user3', '92877af70a45fd6a2ed7fe81e1236b78', NULL, NULL, '92877af70a45fd6a2ed7fe81e1236b78', 0, '2018-06-02 10:30:52', NULL, 0, '2018-05-03 10:30:51', 'user3', '2018-05-03 10:30:51', 'user3', 0);

SET FOREIGN_KEY_CHECKS = 1;
