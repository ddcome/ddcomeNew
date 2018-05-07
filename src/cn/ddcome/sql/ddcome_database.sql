/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : ddcome_database

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-06-20 17:34:57
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `articles`
-- ----------------------------
DROP TABLE IF EXISTS `articles`;
CREATE TABLE `articles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` text,
  `file` longblob,
  `contents` longtext,
  `publish_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `browse_times` int(11) DEFAULT '0',
  `share_times` int(11) DEFAULT '0',
  `good_idea` int(11) DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articles
-- ----------------------------
INSERT INTO `articles` VALUES ('1', '123', null, null, null, '0', '0', '0', '1');
