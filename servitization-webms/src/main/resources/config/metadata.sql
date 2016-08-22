SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for `metadata`
-- ----------------------------
DROP TABLE IF EXISTS `metadata`;
CREATE TABLE `metadata` (
  `id`           BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `meta_key`     VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '元数据标识；eg：webbus',
  `description`  VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '描述',
  `deploy_model` VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '部署策略',
  `up_chain`     VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '上行处理链',
  `down_chain`   VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '下行处理链',
  `create_time`  DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '元数据表';

-- ----------------------------
-- Table structure for `metadata_defence`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_defence`;
CREATE TABLE `metadata_defence` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` BIGINT(20) NOT NULL DEFAULT '0'
  COMMENT '元数据标识表id',
  `proxy_id`    BIGINT(20) NOT NULL DEFAULT '0'
  COMMENT '转发表记录id',
  `time_unit`   INT(11)    NOT NULL DEFAULT '0'
  COMMENT '时间单元',
  `times`       INT(11)    NOT NULL DEFAULT '0'
  COMMENT '次数',
  `create_time` DATETIME   NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`  TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '防攻击表';

-- ----------------------------
-- Table structure for `metadata_group`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_group`;
CREATE TABLE `metadata_group` (
  `id`              BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `metadata_id`     BIGINT(20)   NOT NULL DEFAULT '0'
  COMMENT '元数据标识表id',
  `name`            VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '名字',
  `process_timeout` INT(11)      NOT NULL DEFAULT '0'
  COMMENT '处理时间 单位：秒',
  `size`            INT(11)      NOT NULL DEFAULT '0'
  COMMENT '线程池大小',
  `policy`          VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '处理池满时，新任务处理策略',
  `module_ids`      VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '模块组 逗号分割的模块id',
  `chain`           TINYINT(4)   NOT NULL DEFAULT '0'
  COMMENT '所属上下行链条 0:上行 1下行',
  `create_time`     DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '组配置表';

-- ----------------------------
-- Table structure for `metadata_module`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_module`;
CREATE TABLE `metadata_module` (
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `name`          VARCHAR(50)  NOT NULL DEFAULT ''
  COMMENT '模块名',
  `handler_name`  VARCHAR(50)  NOT NULL DEFAULT ''
  COMMENT '处理模块英文名',
  `handler_clazz` VARCHAR(200) NOT NULL DEFAULT ''
  COMMENT '处理类',
  `chain`         TINYINT(4)   NOT NULL DEFAULT '0'
  COMMENT '所属上 下行链条 0 上行 1 下行',
  `valid_status`  TINYINT(4)   NOT NULL DEFAULT '0'
  COMMENT '有效标志位 0 有效 1 无效',
  `create_time`   DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8
  COMMENT '模块表';

-- ----------------------------
-- Records of metadata_module
-- ----------------------------
INSERT INTO metadata_module VALUES
  ('1', '请求合法性检查', 'RequestServiceChainHandler', 'com.servitization.request.chain.RequestServiceChainHandler', '0',
   '0', NOW(), NOW());
INSERT INTO metadata_module VALUES
  ('2', '防攻击模块', 'defenceServiceChainHandler', 'com.servitization.defence.chain.DefenceServiceChainHandler', '0', '0',
   NOW(), NOW());
INSERT INTO metadata_module VALUES
  ('3', 'Session验证', 'sessionServiceChainHandler', 'com.servitization.session.chain.SessionServiceChainHandler', '0',
   '0', NOW(), NOW());
INSERT INTO metadata_module VALUES
  ('4', '请求解密模块', 'aESChainHandler', 'com.servitization.yoke.chain.AESChainHandler', '0', '0', NOW(), NOW());
INSERT INTO metadata_module VALUES
  ('5', '请求转发', 'serviceProxyHandler', 'com.servitization.proxy.chain.ServiceProxyChainHandler', '0', '0', NOW(),
   NOW());
INSERT INTO metadata_module VALUES
  ('6', '异常处理模块', 'errorChainHandler', 'com.servitization.error.ErrorChainHandler', '1', '0', NOW(), NOW());
INSERT INTO metadata_module VALUES
  ('7', '压缩模块', 'compressChainHandler', 'com.servitization.compress.CompressChainHandler', '1', '0', NOW(), NOW());

-- ----------------------------
-- Table structure for `metadata_node_relation`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_node_relation`;
CREATE TABLE `metadata_node_relation` (
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `metadata_id`   BIGINT(20)   NOT NULL
  COMMENT '元数据标识表id',
  `aos_node_id`   INT(11)      NOT NULL
  COMMENT '关联节点id',
  `aos_node_name` VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT '关联节点名字',
  `create_time`   DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'aos绑定关系表';

-- ----------------------------
-- Table structure for `metadata_proxy`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_proxy`;
CREATE TABLE `metadata_proxy` (
  `id`              BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `metadata_id`     BIGINT(20)   NOT NULL
  COMMENT '元数据标识表id',
  `source_url`      VARCHAR(300) NOT NULL DEFAULT ''
  COMMENT '请求地址',
  `source_method`   VARCHAR(50)  NOT NULL DEFAULT 'GET'
  COMMENT 'GET/POST',
  `is_forward`      TINYINT(4)   NOT NULL DEFAULT '0'
  COMMENT '是否转发 0 转发 1 不转发',
  `target_url`      VARCHAR(300) NOT NULL DEFAULT ''
  COMMENT '转发地址',
  `target_method`   VARCHAR(50)  NOT NULL DEFAULT 'GET'
  COMMENT 'GET/POST',
  `connect_timeout` INT(11)      NOT NULL DEFAULT '0'
  COMMENT '连接超时时间',
  `socket_timeout`  INT(11)      NOT NULL DEFAULT '0'
  COMMENT '处理超时时间',
  `threshold`       INT(11)      NOT NULL DEFAULT '0'
  COMMENT '过滤多少流量 百分比 eg 80 过滤80%流量',
  `sevice_type`     INT(11)      NOT NULL DEFAULT '0',
  `host`            VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT 'host',
  `port`            VARCHAR(100) NOT NULL DEFAULT ''
  COMMENT 'port',
  `asyn`            INT(11)      NOT NULL DEFAULT '0'
  COMMENT '创建时间',
  `create_time`     DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '转发配置表';

-- ----------------------------
-- Table structure for `metadata_publish`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_publish`;
CREATE TABLE `metadata_publish` (
  `id`                  BIGINT(20) NOT NULL AUTO_INCREMENT,
  `node_relation_id`    BIGINT(20) NOT NULL,
  `metadata_version_id` BIGINT(20) NOT NULL
  COMMENT '使用的元数据版本',
  `status`              TINYINT(4) NOT NULL
  COMMENT '发布状态 0 正在发布 1 发布成功 2 发布失败',
  `create_time`         DATETIME   NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`          TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '发布状态表';

-- ----------------------------
-- Table structure for `metadata_publish_ip`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_publish_ip`;
CREATE TABLE `metadata_publish_ip` (
  `id`          BIGINT(20)  NOT NULL AUTO_INCREMENT,
  `publish_id`  BIGINT(20)  NOT NULL DEFAULT '0'
  COMMENT '任务id',
  `ip`          VARCHAR(50) NOT NULL DEFAULT ''
  COMMENT '机器ip',
  `status`      TINYINT(1)  NOT NULL DEFAULT '0'
  COMMENT '发布状态 0:等待同步 1:正在同步 2:加载成功 3:加载失败 4:更新状态超时 5:ZK节点不存在',
  `create_time` DATETIME    NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `update_time` DATETIME    NOT NULL DEFAULT NOW()
  COMMENT '状态更新时间',
  `_timestamp`  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '发布历史表';

-- ----------------------------
-- Table structure for `metadata_session`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_session`;
CREATE TABLE `metadata_session` (
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `metadata_id` BIGINT(20)   NOT NULL DEFAULT '0'
  COMMENT '元数据标识表id',
  `proxy_id`    BIGINT(20)   NOT NULL DEFAULT '0'
  COMMENT '转发表记录id',
  `strategy`    VARCHAR(200) NOT NULL DEFAULT ''
  COMMENT '策略 枚举值',
  `create_time` DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '会话检查配置表';

-- ----------------------------
-- Table structure for `metadata_version`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_version`;
CREATE TABLE `metadata_version` (
  `id`           BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `metadata_id`  BIGINT(20)    NOT NULL DEFAULT '0'
  COMMENT '元数据标识表id',
  `metadata_xml` VARCHAR(6000) NOT NULL DEFAULT ''
  COMMENT '元数据数据',
  `description`  VARCHAR(100)  NOT NULL DEFAULT ''
  COMMENT '描述',
  `create_time`  DATETIME      NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '版本表';

-- ----------------------------
-- Table structure for `metadata_xml`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_xml`;
CREATE TABLE `metadata_xml` (
  `id`                  BIGINT(20)    NOT NULL AUTO_INCREMENT,
  `metadata_version_id` BIGINT(20)    NOT NULL DEFAULT '0'
  COMMENT '版本表id',
  `metadata_xml`        VARCHAR(6000) NOT NULL DEFAULT ''
  COMMENT '元数据数据',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '版本表';

-- ----------------------------
-- Table structure for `metadata_defence_list`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_defence_whitelist`;
CREATE TABLE `metadata_defence_whitelist` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` BIGINT(20) NOT NULL DEFAULT '0'
  COMMENT '元数据标识表id',
  `ips`         BLOB COMMENT '防攻击白名单列表',
  `create_time` DATETIME   NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`  TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '防刷白名单表';

-- ----------------------------
-- Table structure for `metadata_aes_list`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_aes_whitelist`;
CREATE TABLE `metadata_aes_whitelist` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` BIGINT(20) NOT NULL DEFAULT '0'
  COMMENT '元数据标识表id',
  `ips`         BLOB COMMENT 'AES白名单列表',
  `create_time` DATETIME   NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`  TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '解密白名单表';

ALTER TABLE metadata_proxy
  ADD COLUMN service_name VARCHAR(100) COMMENT '服务名称';
ALTER TABLE metadata_proxy
  ADD COLUMN service_version VARCHAR(100) COMMENT '服务版本';
ALTER TABLE metadata_proxy
  ADD COLUMN service_pool_name VARCHAR(100) COMMENT '服务pool名称';
ALTER TABLE metadata_proxy
  ADD COLUMN threshold_type INT(11);

-- ----------------------------
-- Table structure for `metadata_pvLog`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_pvLog`;
CREATE TABLE `metadata_pvLog` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `metadata_id` BIGINT(20) NOT NULL
  COMMENT '元数据标识表id',
  `proxy_id`    BIGINT(20) NOT NULL DEFAULT '0'
  COMMENT '转发表记录id',
  `create_time` DATETIME   NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT 'pv日志表';

-- ----------------------------
-- Table structure for `metadata_service_pool`
-- ----------------------------
DROP TABLE IF EXISTS `metadata_service_pool`;
CREATE TABLE `metadata_service_pool` (
  `id`                    BIGINT(20)   NOT NULL AUTO_INCREMENT,
  service_pool_name       VARCHAR(100) NOT NULL
  COMMENT '名称',
  url                     VARCHAR(256) NOT NULL,
  service_type            TINYINT(4)   NOT NULL
  COMMENT '服务类型',
  `metadata_id`           BIGINT(20)   NOT NULL
  COMMENT '元数据标识表id',
  `coefficient`           DOUBLE       NOT NULL DEFAULT '0'
  COMMENT '系数',
  force_close_channel     INT          NOT NULL,
  force_close_time_millis INT          NOT NULL,
  connect_timeout         INT          NOT NULL,
  create_time             VARCHAR(64)  NOT NULL
  COMMENT '创建时间',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT '服务池表';

-- update length
ALTER TABLE metadata_xml
  MODIFY COLUMN metadata_xml VARCHAR(6000);
DROP TABLE IF EXISTS `metadata_session`;
CREATE TABLE `metadata_session` (
  id             BIGINT                                 NOT NULL AUTO_INCREMENT,
  metadata_id    BIGINT DEFAULT '0'                     NOT NULL
  COMMENT '元数据标识表id',
  proxy_id       BIGINT DEFAULT '0'                     NOT NULL
  COMMENT '转发表记录id',
  strategy       VARCHAR(200)                           NOT NULL
  COMMENT '策略 枚举值',
  create_time    DATETIME                               NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  _timestamp     TIMESTAMP                                       DEFAULT CURRENT_TIMESTAMP ON
  UPDATE
  CURRENT_TIMESTAMP COMMENT 'dba要求',
  reqtype        VARCHAR(68),
  validateurl    VARCHAR(400),
  validateMethod VARCHAR(64),
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '会话检查配置表';

-- add table
DROP TABLE IF EXISTS `metadata_machine`;
CREATE TABLE `metadata_machine` (
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `metadata_id` VARCHAR(100) NOT NULL
  COMMENT '元数据主键',
  `ip`          VARCHAR(100) COMMENT '机器ip地址',
  `status`      VARCHAR(8)   NOT NULL DEFAULT '1'
  COMMENT '机器状态是否启用1启用0未启用',
  `create_time` DATETIME     NOT NULL DEFAULT NOW()
  COMMENT '创建时间',
  `_timestamp`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'dba要求',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 21
  DEFAULT CHARSET = utf8
  COMMENT = '元数数据对应机器列表';

ALTER TABLE metadata_proxy
  ADD COLUMN isconvert VARCHAR(8);