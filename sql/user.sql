/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : mp

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2019-11-12 10:41:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL COMMENT '主键id',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `status` int(255) NOT NULL DEFAULT '1' COMMENT '状态 1启用 0 停用',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
