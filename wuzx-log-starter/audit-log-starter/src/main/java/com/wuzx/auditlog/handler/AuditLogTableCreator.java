package com.wuzx.auditlog.handler;

import cn.hutool.core.collection.CollectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.List;

public class AuditLogTableCreator {

    private final static String sqlTemplate = "CREATE TABLE %s (" +
            "`id` bigint(20) NOT NULL AUTO_INCREMENT," +
            "`trace_id` varchar(20) DEFAULT NULL COMMENT '追踪ID'," +
            "`table_name` varchar(50) DEFAULT NULL COMMENT '修改表名称'," +
            "`table_id` varchar(50) DEFAULT NULL COMMENT '修改表对应的主键'," +
            "`new_value` text DEFAULT NULL COMMENT '修改之后的值'," +
            "`old_value` text DEFAULT NULL COMMENT '修改之前的值'," +
            "`operation` varchar(50) DEFAULT NULL COMMENT '操作'," +
            "`create_time` datetime NOT NULL COMMENT '创建时间'," +
            "`user_Id` int(20) DEFAULT NULL COMMENT '操作用户id'," +
            "PRIMARY KEY (`id`)," +
            "KEY `audit_log_trace_id` (`trace_id`)," +
            "KEY `audit_log_table_id` (`table_id`)," +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审计日志'";

    /**
     * 默认表名
     */
    private String defaultTableName;

    /**
     * 表名前缀
     */
    private String preTableName;


    /**
     * 当前表名
     */
    private String currentValidTableName;

    /**
     * 是否开启按月份切分表
     */
    private  Boolean splitEnable;

    /**
     * 需要监听的表
     */
    private List<String> listenTables;


    public AuditLogTableCreator(Boolean splitEnable, List<String> listenTables) {

        this.splitEnable = splitEnable;
        this.listenTables = listenTables;
        this.currentValidTableName = getCurrentTableName();
    }

    /**
     * 获取当前的表名，table_日期
     * @return
     */
    public String getCurrentTableName() {
        if (splitEnable) {
            Calendar calendar = Calendar.getInstance();
            String calendarMonth;
            if (calendar.get(Calendar.MONTH) < 9) {
                calendarMonth = "0" + (calendar.get(Calendar.MONTH) + 1);
            } else {
                calendarMonth = String.valueOf((calendar.get(Calendar.MONTH) + 1));
            }
            return preTableName + calendar.get(Calendar.YEAR) + calendarMonth;
        } else {
            return defaultTableName;
        }

    }

    /**
     * 创建新表
     * @param connection
     * @return
     */
    public String createNew(Connection connection) {
        try {
            String tableNameByNowDate = getCurrentTableName();
            Statement statement = connection.createStatement();
            statement.execute(String.format(sqlTemplate, tableNameByNowDate));
            statement.close();
            currentValidTableName = tableNameByNowDate;
            return tableNameByNowDate;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCurrentValidTableName() {
        return currentValidTableName;
    }

    public Boolean getSplitEnable() {
        return splitEnable;
    }

    public List<String> getListenTables() {
        return listenTables;
    }


    /**
     * 判断是否需要审计
     * @param table
     * @return
     */
    public boolean isAudit(String table) {
        if (CollectionUtil.isEmpty(listenTables)) {
            return true;
        }
        return listenTables.contains(table);
    }
}
