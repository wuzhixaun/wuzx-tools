-- 创建数据库
create database `log` default character set utf8mb4 collate utf8mb4_general_ci;

-- 访问日志
DROP TABLE if EXISTS `tb_log_access`;
CREATE TABLE `tb_log_access` (
     `id` int(20) NOT NULL AUTO_INCREMENT,
     `trace_id` varchar(20) DEFAULT NULL COMMENT '追踪ID',
     `user_id` int(20) NOT NULL COMMENT '用户id',
     `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
     `ip` varchar(100) DEFAULT NULL COMMENT 'ip地址',
     `user_agent` text DEFAULT NULL COMMENT '用户代理',
     `uri` varchar(256) DEFAULT NULL COMMENT '请求URI',
     `method` varchar(20) DEFAULT NULL COMMENT '操作方式',
     `req_params` text DEFAULT NULL COMMENT '请求参数',
     `req_body` text DEFAULT NULL COMMENT '请求body',
     `http_status` int(2) DEFAULT NULL COMMENT '响应状态码',
     `result` text DEFAULT NULL COMMENT '响应信息',
     `error_msg` text DEFAULT NULL COMMENT '错误消息',
     `execut_time` INT(20) DEFAULT NULL COMMENT '执行时长',
     `create_time` datetime NOT NULL COMMENT '创建时间',
     PRIMARY KEY (`id`),
     KEY `log_access_trace_id` (`trace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问日志';

-- 操作日志
DROP TABLE if EXISTS `tb_log_operation`;
CREATE TABLE `tb_log_operation` (
    `id` int(20) NOT NULL AUTO_INCREMENT,
    `trace_id` varchar(20) DEFAULT NULL COMMENT '追踪ID',
    `user_id` int(20) NOT NULL COMMENT '用户id',
    `user_name` varchar(32) DEFAULT NULL COMMENT '用户名称',
    `ip` varchar(100) DEFAULT NULL COMMENT 'ip地址',
    `user_agent` text DEFAULT NULL COMMENT '用户代理',
    `uri` varchar(256) DEFAULT NULL COMMENT '请求URI',
    `method` varchar(20) DEFAULT NULL COMMENT '操作方式',
    `params` text DEFAULT NULL COMMENT '操作提交的数据',
    `result` text DEFAULT NULL COMMENT '操作结果',
    `status` int(2) DEFAULT NULL COMMENT '操作状态',
    `type` int(2) DEFAULT NULL COMMENT '操作类型',
    `msg` text DEFAULT NULL COMMENT '日志消息',
    `execut_time` INT(20) DEFAULT NULL COMMENT '执行时长',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `log_operation_trace_id` (`trace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='访问日志';

-- 审计日志
DROP TABLE if EXISTS `tb_log_audit`;
CREATE TABLE `tb_log_audit` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `trace_id` varchar(20) DEFAULT NULL COMMENT '追踪ID',
    `table_name` varchar(50) DEFAULT NULL COMMENT '修改表名称',
    `table_id` varchar(50) DEFAULT NULL COMMENT '修改表对应的主键',
    `new_value` text DEFAULT NULL COMMENT '修改之后的值',
    `old_value` text DEFAULT NULL COMMENT '修改之前的值',
    `operation` varchar(50) DEFAULT NULL COMMENT '操作',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `user_Id` int(20) DEFAULT NULL COMMENT '操作用户id',
    `user_Name`varchar(50) DEFAULT NULL COMMENT '操作用户名称',
    PRIMARY KEY (`id`),
    KEY `audit_log_trace_id` (`trace_id`),
    KEY `audit_log_table_id` (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审计日志';