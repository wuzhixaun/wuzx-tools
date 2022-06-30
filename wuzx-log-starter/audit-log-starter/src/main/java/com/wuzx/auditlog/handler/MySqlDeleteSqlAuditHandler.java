package com.wuzx.auditlog.handler;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLLimit;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.wuzx.log.constant.LogConstant;
import com.wuzx.log.model.entity.AuditLog;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySqlDeleteSqlAuditHandler extends AbstractSQLHandler {

    private String querySql;


    private List<AuditLog> auditLogsBeforeDelete;
    /**
     * 列
     */
    private final Map<String, Object> columnMap = new HashMap<>();

    private Boolean preHandled = Boolean.FALSE;

    public MySqlDeleteSqlAuditHandler(Connection connection, DBMetaDataHolder dbMetaDataHolder, Method clerkIdMethod, String sql) {
        super(connection, dbMetaDataHolder, clerkIdMethod, sql);
    }

    @Override
    protected SQLStatement parseSQLStatement(SQLStatementParser statementParser) {
        return statementParser.parseDeleteStatement();
    }

    @Override
    protected SQLTableSource getMajorTableSource(SQLStatement statement) {
        if (statement instanceof MySqlDeleteStatement) {
            return ((MySqlDeleteStatement) statement).getFrom() != null ? ((MySqlDeleteStatement) statement).getFrom() : ((MySqlDeleteStatement) statement).getTableSource();
        } else {
            return null;
        }
    }

    private List<String> buildTableSourceAliases(SQLTableSource tableSource) {
        List<String> tables = new ArrayList<>();
        if (tableSource instanceof SQLExprTableSource) {
            SQLExpr expr = ((SQLExprTableSource) tableSource).getExpr();
            if (expr instanceof SQLPropertyExpr) {
                tables.add(SQLUtils.toMySqlString(((SQLPropertyExpr) expr).getOwner()));
            } else if (expr instanceof SQLIdentifierExpr) {
                tables.add(getTableToAliasMap().get(((SQLIdentifierExpr) expr).getName()));
            }
        } else if (tableSource instanceof SQLJoinTableSource) {
            tables.addAll(buildTableSourceAliases(((SQLJoinTableSource) tableSource).getLeft()));
            tables.addAll(buildTableSourceAliases(((SQLJoinTableSource) tableSource).getRight()));
        }
        return tables;
    }


    @Override
    public void preHandle() {
        if (getSqlStatement() instanceof MySqlDeleteStatement) {
            MySqlDeleteStatement deleteStatement = (MySqlDeleteStatement) getSqlStatement();
            SQLTableSource affectTableSource = deleteStatement.getTableSource() != null ? deleteStatement.getTableSource() : deleteStatement.getFrom();
            List<String> affectAliasList = buildTableSourceAliases(affectTableSource);
            SQLTableSource from = deleteStatement.getFrom() != null ? deleteStatement.getFrom() : deleteStatement.getTableSource();
            SQLExpr where = deleteStatement.getWhere();
            SQLOrderBy orderBy = deleteStatement.getOrderBy();
            SQLLimit limit = deleteStatement.getLimit();
            MySqlSelectQueryBlock selectQueryBlock = new MySqlSelectQueryBlock();
            for (String alias : affectAliasList) {
                selectQueryBlock.getSelectList().add(new SQLSelectItem(SQLUtils.toSQLExpr(
                        String.format("%s.%s", alias, getDbMetaDataHolder().getPrimaryKeys().get(getAliasToTableMap().get(alias))))));
                for (String columnName : getDbMetaDataHolder().getTableColumns().get(getAliasToTableMap().get(alias))) {
                    selectQueryBlock.getSelectList().add(new SQLSelectItem(SQLUtils.toSQLExpr(
                            String.format("%s.%s", alias, columnName))));
                }
            }
            selectQueryBlock.setFrom(from);
            selectQueryBlock.setWhere(where);
            selectQueryBlock.setOrderBy(orderBy);
            selectQueryBlock.setLimit(limit);
            querySql = trimSQLWhitespaces(SQLUtils.toMySqlString(selectQueryBlock));
            auditLogsBeforeDelete = getCurrentDataForTables();
            preHandled = Boolean.TRUE;
        }
    }

    @Override
    public void postHandle() {
        if (preHandled) {
            saveAuditLog(auditLogsBeforeDelete);
        }
    }


    @SuppressWarnings("unchecked")
    private List<AuditLog> getCurrentDataForTables() {
        List<AuditLog> cols = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement(querySql);
            ResultSet resultSet = statement.executeQuery();
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {

                for (int i = 1; i < columnCount + 1; i++) {
                    final String columnName = resultSet.getMetaData().getColumnName(i);
                    columnMap.put(columnName, resultSet.getObject(i));
                }
                cols.add(buildLog(resultSet.getMetaData().getTableName(1), resultSet.getObject(1), LogConstant.OperationEnum.delete,  columnMap, null));
            }
            resultSet.close();
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
        return cols;
    }

}
