/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : ntx-test

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2020-12-15 11:48:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_rocketmq_message_log
-- ----------------------------
DROP TABLE IF EXISTS `t_rocketmq_message_log`;
CREATE TABLE `t_rocketmq_message_log` (
  `msg_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `key` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` int DEFAULT NULL COMMENT '消息状态(0：处理中 1：消费成功 2：消费失败)',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
