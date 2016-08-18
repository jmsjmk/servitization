/*
Navicat MySQL Data Transfer

Source Server         : 192.168.9.10
Source Server Version : 50611
Source Host           : 192.168.9.10:3306
Source Database       : metadb

Target Server Type    : MYSQL
Target Server Version : 50611
File Encoding         : 65001

*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `metadata`
-- ----------------------------
DROP TABLE IF EXISTS `metadata`;
CREATE TABLE `metadata` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meta_key` varchar(100) NOT NULL DEFAULT '' COMMENT '元数据标识；eg：webbus',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `deploy_model` varchar(100) NOT NULL DEFAULT '' COMMENT '部署策略',
  `up_chain` varchar(100) NOT NULL DEFAULT '' COMMENT '上行处理链',
  `down_chain` varchar(100) NOT NULL DEFAULT '' COMMENT '下行处理链',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '元数据表';


-- ----------------------------
-- Table structure for `metadata_defence`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_defence`;
CREATE TABLE `metadata_defence` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元数据标识表id',
  `proxy_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '转发表记录id',
  `time_unit` int(11) NOT NULL DEFAULT '0' COMMENT '时间单元',
  `times` int(11) NOT NULL DEFAULT '0' COMMENT '次数',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '防攻击表';


-- ----------------------------
-- Table structure for `metadata_group`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_group`;
CREATE TABLE `metadata_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元数据标识表id',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '名字',
  `process_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '处理时间 单位：秒',
  `size` int(11) NOT NULL DEFAULT '0' COMMENT '线程池大小',
  `policy` varchar(100) NOT NULL DEFAULT '' COMMENT '处理池满时，新任务处理策略',
  `module_ids` varchar(100) NOT NULL DEFAULT '' COMMENT '模块组 逗号分割的模块id',
  `chain` tinyint(4) NOT NULL DEFAULT '0' COMMENT '所属上下行链条 0:上行 1下行',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT '组配置表';


-- ----------------------------
-- Table structure for `metadata_module`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_module`;
CREATE TABLE `metadata_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '模块名',
  `handler_name` varchar(50) NOT NULL DEFAULT '' COMMENT '处理模块英文名',
  `handler_clazz` varchar(200) NOT NULL DEFAULT '' COMMENT '处理类',
  `chain` tinyint(4) NOT NULL DEFAULT '0' COMMENT '所属上 下行链条 0 上行 1 下行',
  `valid_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '有效标志位 0 有效 1 无效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT '模块表';

-- ----------------------------
-- Records of metadata_module
-- ----------------------------
INSERT INTO metadata_module VALUES ('1', '请求合法性检查', 'RequestServiceChainHandler', 'com.servitization.request.chain.RequestServiceChainHandler', '0', '0', NOW(), '0000-00-00 00:00:00');
INSERT INTO metadata_module VALUES ('2', '防攻击模块', 'defenceServiceChainHandler', 'com.servitization.defence.chain.DefenceServiceChainHandler', '0', '0', NOW(), '0000-00-00 00:00:00');
INSERT INTO metadata_module VALUES ('3', 'Session验证', 'sessionServiceChainHandler', 'com.servitization.session.chain.SessionServiceChainHandler', '0', '0', NOW(), '0000-00-00 00:00:00');
INSERT INTO metadata_module VALUES ('4', '请求解密模块', 'aESChainHandler', 'com.servitization.yoke.chain.AESChainHandler', '0', '0', NOW(), '0000-00-00 00:00:00');
INSERT INTO metadata_module VALUES ('5', '请求转发', 'servicePorxyHandler', 'com.servitization.proxy.chain.ServicePorxyChainHandler', '0', '0', NOW(), '0000-00-00 00:00:00');
INSERT INTO metadata_module VALUES ('6', '异常处理模块', 'errorChainHandler', 'com.servitization.error.ErrorChainHandler', '1', '0', NOW(), '0000-00-00 00:00:00');
INSERT INTO metadata_module VALUES ('7', '压缩模块', 'compressChainHandler', 'com.servitization.compress.CompressChainHandler', '1', '0', NOW(), '0000-00-00 00:00:00');

-- ----------------------------
-- Table structure for `metadata_node_relation`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_node_relation`;
CREATE TABLE `metadata_node_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL COMMENT '元数据标识表id',
  `aos_node_id` int(11) NOT NULL COMMENT '关联节点id',
  `aos_node_name` varchar(100) NOT NULL DEFAULT '' COMMENT '关联节点名字',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'aos绑定关系表';



-- ----------------------------
-- Table structure for `metadata_proxy`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_proxy`;
CREATE TABLE `metadata_proxy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL COMMENT '元数据标识表id',
  `source_url` varchar(300) NOT NULL DEFAULT '' COMMENT '请求地址',
  `source_method` varchar(50) NOT NULL DEFAULT 'GET' COMMENT 'GET/POST',
  `is_forward` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否转发 0 转发 1 不转发',
  `target_url` varchar(300) NOT NULL DEFAULT '' COMMENT '转发地址',
  `target_method` varchar(50) NOT NULL DEFAULT 'GET' COMMENT 'GET/POST',
  `connect_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '连接超时时间',
  `socket_timeout` int(11) NOT NULL DEFAULT '0' COMMENT '处理超时时间',
  `threshold` int(11) NOT NULL DEFAULT '0' COMMENT '过滤多少流量 百分比 eg 80 过滤80%流量',
  `sevice_type` int(11) NOT NULL DEFAULT '0',
  `host` varchar(100) NOT NULL DEFAULT '' COMMENT 'host',
  `port` varchar(100) NOT NULL DEFAULT '' COMMENT 'port',
  `asyn` int(11) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '转发配置表';


-- ----------------------------
-- Table structure for `metadata_publish`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_publish`;
CREATE TABLE `metadata_publish` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `node_relation_id` bigint(20) NOT NULL,
  `metadata_version_id` bigint(20) NOT NULL COMMENT '使用的元数据版本',
  `status` tinyint(4) NOT NULL COMMENT '发布状态 0 正在发布 1 发布成功 2 发布失败',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '发布状态表';


-- ----------------------------
-- Table structure for `metadata_publish_ip`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_publish_ip`;
CREATE TABLE `metadata_publish_ip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `publish_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '任务id',
  `ip` varchar(50) NOT NULL DEFAULT '' COMMENT '机器ip',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '发布状态 0:等待同步 1:正在同步 2:加载成功 3:加载失败 4:更新状态超时 5:ZK节点不存在',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '状态更新时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '发布历史表';

-- ----------------------------
-- Table structure for `metadata_session`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_session`;
CREATE TABLE `metadata_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元数据标识表id',
  `proxy_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '转发表记录id',
  `strategy` varchar(200) NOT NULL DEFAULT '' COMMENT '策略 枚举值',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '会话检查配置表';


-- ----------------------------
-- Table structure for `metadata_version`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_version`;
CREATE TABLE `metadata_version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元数据标识表id',
  `metadata_xml` varchar(6000) NOT NULL DEFAULT '' COMMENT '元数据数据',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '版本表';

-- ----------------------------
-- Table structure for `metadata_xml`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_xml`;
CREATE TABLE `metadata_xml` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_version_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本表id',
  `metadata_xml` varchar(6000) NOT NULL DEFAULT '' COMMENT '元数据数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '版本表';

-- ----------------------------
-- Table structure for `metadata_defence_list`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_defence_whitelist`;
CREATE TABLE `metadata_defence_whitelist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元数据标识表id',
  `ips` blob COMMENT '防攻击白名单列表',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '防刷白名单表';

-- ----------------------------
-- Table structure for `metadata_aes_list`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_aes_whitelist`;
CREATE TABLE `metadata_aes_whitelist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '元数据标识表id',
  `ips` blob COMMENT 'AES白名单列表',
  `create_time` datetime NOT NULL DEFAULT '1990-01-01 00:00:00' COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '解密白名单表';

alter table metadata_proxy add column service_name varchar(100) comment '服务名称';
alter table metadata_proxy add column service_version varchar(100) comment '服务版本';
alter table metadata_proxy add column service_pool_name varchar(100) comment '服务pool名称';
alter table metadata_proxy add column threshold_type int(11) ;

-- ----------------------------
-- Table structure for `metadata_pvLog`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_pvLog`;
CREATE TABLE `metadata_pvLog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` bigint(20) NOT NULL COMMENT '元数据标识表id',
 	`proxy_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '转发表记录id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'pv日志表';

-- ----------------------------
-- Table structure for `metadata_service_pool`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_service_pool`;
CREATE TABLE `metadata_service_pool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  service_pool_name varchar(100) not null comment '名称',
  url varchar(256) not null,
  service_type tinyint(4) NOT NULL comment '服务类型',
  `metadata_id` bigint(20) NOT NULL COMMENT '元数据标识表id',
 	`coefficient` double NOT NULL DEFAULT '0' COMMENT '系数',
  force_close_channel int not null ,
  force_close_time_millis int not null,
  connect_timeout int not null,
  create_time varchar(64) not null comment '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '服务池表';

-- update length
alter table metadata_xml  modify  column metadata_xml varchar(6000);
DROP TABLE IF EXISTS `metadata_session`;
CREATE TABLE `metadata_session` (
        id bigint NOT NULL AUTO_INCREMENT,
        metadata_id bigint DEFAULT '0' NOT NULL COMMENT '元数据标识表id',
        proxy_id bigint DEFAULT '0' NOT NULL COMMENT '转发表记录id',
        strategy VARCHAR(200) NOT NULL COMMENT '策略 枚举值',
        create_time DATETIME DEFAULT '1990-01-01 00:00:00' NOT NULL COMMENT '创建时间',
        _timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON
    UPDATE
        CURRENT_TIMESTAMP COMMENT 'dba要求',
        reqtype VARCHAR(68),
        validateurl VARCHAR(400),
        validateMethod VARCHAR(64),
        PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会话检查配置表';

-- add table
DROP TABLE IF EXISTS `metadata_machine`;
CREATE TABLE `metadata_machine` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` varchar(100) NOT NULL COMMENT '元数据主键',
  `ip` varchar(100) COMMENT '机器ip地址',
  `status` varchar(8) NOT NULL DEFAULT '1' COMMENT '机器状态是否启用1启用0未启用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `_timestamp` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='元数数据对应机器列表'


alter table metadata_proxy add column isconvert varchar(8)


