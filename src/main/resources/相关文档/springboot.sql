/*
 Navicat Premium Data Transfer

 Source Server         : localhostMysql
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : springboot

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 24/04/2018 21:02:59
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `user` VALUES ('admin', 'admin', '21232f297a57a5a743894a0e4a801fc3', NULL, 44, '21232f297a57a5a743894a0e4a801fc3', 5, '2018-05-18 09:58:06', '2018-04-18 09:57:30', 1, '2018-04-17 21:33:35', 'admin', '2018-04-18 09:58:06', 'admin', 26);
INSERT INTO `user` VALUES ('root', 'root', '63a9f0ea7bb98050796b649e85481845', NULL, NULL, '63a9f0ea7bb98050796b649e85481845', 4, '2018-05-17 21:33:44', '2018-04-18 02:48:39', 0, '2018-04-17 21:33:44', 'root', '2018-04-18 02:48:39', 'root', 11);
INSERT INTO `user` VALUES ('test1', 'test1', '698d51a19d8a121ce581499d7b701668', NULL, 12, '5a105e8b9d40e1329780d62ea2265d8a,698d51a19d8a121ce581499d7b701668', 1, '2018-05-18 09:58:54', '2018-04-18 09:59:01', 0, '2018-04-18 09:58:18', 'test1', '2018-04-18 09:59:01', 'test1', 4);
INSERT INTO `user` VALUES ('test2', 'test2', 'ad0234829205b9033196ba818f7a872b', NULL, 22, 'ad0234829205b9033196ba818f7a872b', 0, '2018-05-18 03:34:49', NULL, 0, '2018-04-18 03:34:49', 'test2', '2018-04-18 03:34:49', 'test2', 0);
INSERT INTO `user` VALUES ('test3', 'test', '8ad8757baa8564dc136c1e07507f4a98', NULL, 12, '8ad8757baa8564dc136c1e07507f4a98,8ad8757baa8564dc136c1e07507f4a98,8ad8757baa8564dc136c1e07507f4a98', 5, '2018-05-18 03:34:27', '2018-04-18 03:33:46', 1, '2018-04-18 03:30:44', 'test3', '2018-04-18 03:34:27', 'test3', 33);

SET FOREIGN_KEY_CHECKS = 1;
