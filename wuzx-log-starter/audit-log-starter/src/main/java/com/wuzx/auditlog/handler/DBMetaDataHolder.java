package com.wuzx.auditlog.handler;

import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据库元信息
 */
public class DBMetaDataHolder {
    @SuppressWarnings("unchecked")
    private final Map<String, String> primaryKeys = new CaseInsensitiveMap();

    @SuppressWarnings("unchecked")
    private final Map<String, List<String>> tableColumns = new CaseInsensitiveMap();

    // 是否初始化标识
    private Boolean initialized = Boolean.FALSE;

    private final AuditLogTableCreator auditLogTableCreator;

    public DBMetaDataHolder(AuditLogTableCreator auditLogTableCreator) {
        this.auditLogTableCreator = auditLogTableCreator;
    }

    public void init(Connection connection) {
        if (Objects.equals(initialized, Boolean.FALSE)) {
            synchronized (this) {
                if (connection != null) {
                    if (Objects.equals(initialized, Boolean.FALSE)) {
                        try {
                            ResultSet resultSet = connection.getMetaData().getTables(null,
                                    connection.getMetaData().getUserName(), "%", new String[]{"TABLE"});
                            while (resultSet.next()) {
                                String tableName = resultSet.getString("TABLE_NAME");
                                buildOneSingleTableMetaData(connection, tableName);
                            }
                            resultSet.close();
                            initialized = Boolean.TRUE;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
// 分表功能暂时没有做，所以注释自动创建表的逻辑
//                    if (hasNoCurrentAuditLogTable()) {
//                        String newTableName = auditLogTableCreator.createNew(connection);
//                        if (StringUtils.isNotBlank(newTableName)) {
//                            buildOneSingleTableMetaData(connection, newTableName);
//                        }
//                    }
                }
            }
        }
    }

    /**
     * 判断表是否存在
     *
     * @return
     */
    private Boolean hasNoCurrentAuditLogTable() {
        String currentTableName = auditLogTableCreator.getCurrentTableName();
        return !primaryKeys.containsKey(currentTableName) || !tableColumns.containsKey(currentTableName);
    }

    /**
     * 构建 单个表的数据信息
     *
     * @param connection
     * @param tableName
     */
    private void buildOneSingleTableMetaData(Connection connection, String tableName) {
        primaryKeys.put(tableName, retrievePrimaryKey(connection, tableName));
        tableColumns.put(tableName, retrieveColumns(connection, tableName));
    }

    /**
     * 检索表的主键
     *
     * @param connection
     * @param table
     * @return
     */
    private String retrievePrimaryKey(Connection connection, String table) {
        String primaryKey = null;
        try {
            ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null, connection.getMetaData().getUserName(), table);
            if (resultSet.next())
                primaryKey = resultSet.getString("COLUMN_NAME");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return primaryKey;
    }

    /**
     * 检索表的整个列
     *
     * @param connection
     * @param table
     * @return
     */
    private List<String> retrieveColumns(Connection connection, String table) {
        List<String> columns = null;
        if (!tableColumns.containsKey(table)) {
            try {
                columns = new ArrayList<>();
                ResultSet resultSet = connection.getMetaData().getColumns(null, connection.getMetaData().getUserName(), table, "%");
                while (resultSet.next()) {
                    String columnName = resultSet.getString("COLUMN_NAME");
                    columns.add(columnName);
                }
                tableColumns.put(table, columns);
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            columns = tableColumns.get(table);
        }
        return columns;
    }

    Map<String, String> getPrimaryKeys() {
        return primaryKeys;
    }

    Map<String, List<String>> getTableColumns() {
        return tableColumns;
    }

    AuditLogTableCreator getAuditLogTableCreator() {
        return auditLogTableCreator;
    }
}
