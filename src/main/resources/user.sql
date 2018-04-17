/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.21-log : Database - springboot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`springboot` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `springboot`;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `userId` varchar(10) NOT NULL COMMENT '用户id',
  `name` varchar(10) DEFAULT NULL COMMENT '用户名',
  `pwd` varchar(32) NOT NULL COMMENT '密码',
  `sex` int(1) DEFAULT NULL COMMENT '性别(0:男;1:女;2:其他)',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `pwdRec` varchar(255) NOT NULL COMMENT '密码履历',
  `pwdErrCout` int(1) NOT NULL COMMENT '密码错误次数',
  `pwdExpiredDate` datetime NOT NULL COMMENT '密码有效期限',
  `lastLoginDate` datetime DEFAULT NULL COMMENT '最近一次登陆日期',
  `isLock` int(1) NOT NULL DEFAULT '0' COMMENT '账号是否被锁定(0:未锁定;1:已锁定)',
  `createTime` datetime NOT NULL COMMENT '创建日期',
  `createUser` varchar(10) NOT NULL COMMENT '创建者',
  `updateTime` datetime NOT NULL COMMENT '更新日期',
  `updateUser` varchar(10) NOT NULL COMMENT '更新者',
  `version` int(10) NOT NULL DEFAULT '0' COMMENT '版本号(每次更新表都会加1)',
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`userId`,`name`,`pwd`,`sex`,`age`,`pwdRec`,`pwdErrCout`,`pwdExpiredDate`,`lastLoginDate`,`isLock`,`createTime`,`createUser`,`updateTime`,`updateUser`,`version`) values ('admin','admin','21232f297a57a5a743894a0e4a801fc3',NULL,445,'21232f297a57a5a743894a0e4a801fc3',0,'2018-05-18 03:44:42','2018-04-18 03:44:20',0,'2018-04-17 21:33:35','admin','2018-04-18 03:44:42','admin',17),('root','root','63a9f0ea7bb98050796b649e85481845',NULL,NULL,'63a9f0ea7bb98050796b649e85481845',4,'2018-05-17 21:33:44','2018-04-18 02:48:39',0,'2018-04-17 21:33:44','root','2018-04-18 02:48:39','root',11),('test2','test2','ad0234829205b9033196ba818f7a872b',NULL,22,'ad0234829205b9033196ba818f7a872b',0,'2018-05-18 03:34:49',NULL,0,'2018-04-18 03:34:49','test2','2018-04-18 03:34:49','test2',0),('test3','test','8ad8757baa8564dc136c1e07507f4a98',NULL,12,'8ad8757baa8564dc136c1e07507f4a98,8ad8757baa8564dc136c1e07507f4a98,8ad8757baa8564dc136c1e07507f4a98',5,'2018-05-18 03:34:27','2018-04-18 03:33:46',1,'2018-04-18 03:30:44','test3','2018-04-18 03:34:27','test3',33);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
