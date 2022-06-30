package com.wuzx.auditlog.handler;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.model.entity.AuditLog;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlInsertSQLAuditHandler extends AbstractSQLHandler {

    private String table;

    /**
     * 列
     */
    private final Map<String, Object> columnMap = new HashMap<>();

    private Boolean preHandled = Boolean.FALSE;

    public MySqlInsertSQLAuditHandler(Connection connection, DBMetaDataHolder dbMetaDataHolder, Method clerkIdMethod, String insertSQL) {
        super(connection, dbMetaDataHolder, clerkIdMethod, insertSQL);
    }

    @Override
    protected SQLStatement parseSQLStatement(SQLStatementParser statementParser) {
        return statementParser.parseInsert();
    }

    @Override
    protected SQLTableSource getMajorTableSource(SQLStatement statement) {
        if (statement instanceof MySqlInsertStatement) {
            return ((MySqlInsertStatement) statement).getTableSource();
        }
        return null;
    }

    @Override
    public void preHandle() {
        if (getSqlStatement() instanceof MySqlInsertStatement) {
            MySqlInsertStatement sqlInsertStatement = (MySqlInsertStatement) getSqlStatement();
            if (sqlInsertStatement.getColumns().size() > 0) {
                SQLExpr sqlExpr = sqlInsertStatement.getColumns().get(0);
                String[] aliasAndColumn = separateAliasAndColumn(SQLUtils.toMySqlString(sqlExpr));
                if (aliasAndColumn[0] != null) {
                    table = getAliasToTableMap().get(aliasAndColumn[0]);
                } else if (getTables().size() == 1) {
                    table = getTables().get(0);
                } else {
                    table = determineTableForColumn(aliasAndColumn[1]);
                }
                for (int i = 0; i < sqlInsertStatement.getColumns().size(); i++) {
                    SQLExpr columnExpr = sqlInsertStatement.getColumns().get(i);
                    columnMap.put(separateAliasAndColumn(SQLUtils.toMySqlString(columnExpr))[1], null);
                }
            }
            preHandled = Boolean.TRUE;
        }
    }

    @Override
    public void postHandle() {
        if (preHandled) {
            List<AuditLog> auditLogs = new ArrayList<>();
            Statement statement = null;
            try {
                statement = getConnection().createStatement();
                ResultSet limitResultSet = statement.executeQuery("SELECT rowno - 1, rowcon FROM (SELECT @rowno := @rowno + 1 AS rowno, t2.rowcon AS rowcon, ID FROM " + table + " r, (SELECT @rowno := 0) t, (SELECT ROW_COUNT() AS rowcon) t2 order by r.id asc) b WHERE b.ID = (SELECT LAST_INSERT_ID())");
                if (limitResultSet.next()) {
                    Integer limit_1 = limitResultSet.getInt(1);
                    Integer limit_2 = limitResultSet.getInt(2);

                    StringBuilder sb = new StringBuilder();
                    sb.append(getDbMetaDataHolder().getPrimaryKeys().get(table));
                    for (String column : columnMap.keySet()) {
                        sb.append(", ");
                        sb.append(column);
                    }

                    ResultSet resultSet = statement.executeQuery(String.format("select %s from %s where id>=(select id from %s order by id asc limit %s,1) limit %s", sb, table, table, limit_1, limit_2));
                    int columnCount = resultSet.getMetaData().getColumnCount();
                    while (resultSet.next()) {
                        for (int i = 1; i < columnCount + 1; i++) {
                            columnMap.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
                        }
                        auditLogs.add(buildLog(table, resultSet.getObject(1), LogConstant.OperationEnum.insert, null, columnMap));
                    }
                    resultSet.close();
                }
                limitResultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            saveAuditLog(auditLogs);
        }
    }

}
