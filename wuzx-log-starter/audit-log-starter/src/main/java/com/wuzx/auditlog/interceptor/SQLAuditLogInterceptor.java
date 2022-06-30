package com.wuzx.auditlog.interceptor;


import com.wuzx.auditlog.handler.AuditLogTableCreator;
import com.wuzx.auditlog.handler.DBMetaDataHolder;
import com.wuzx.auditlog.handler.ISQLHandler;
import com.wuzx.auditlog.handler.MySqlDeleteSqlAuditHandler;
import com.wuzx.auditlog.handler.MySqlInsertSQLAuditHandler;
import com.wuzx.auditlog.handler.MySqlUpdateSQLAuditHandler;
import com.wuzx.auditlog.properties.AuditProperties;
import com.wuzx.log.constant.LogConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName SQLAuditLogInterceptor.java
 * @Description 审计日志拦截器
 * @createTime 2022年05月23日 16:43:00
 */

@Intercepts({
        // 配置拦截点,拦截Executor执行器的‘update’方法,拦截的方法对应的参数(JAVA里面方法可能重载，故注意参数的类型和顺序)
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class SQLAuditLogInterceptor implements Interceptor {

    private static final Pattern pattern1 = Pattern.compile("\\?(?=\\s*[^']*\\s*,?\\s*(\\w|$))");

    private static final Pattern pattern2 = Pattern.compile("[\\s]+");

    private Boolean auditEnable;

    private DBMetaDataHolder dbMetaDataHolder;

    private Method userIdMethod;

    public SQLAuditLogInterceptor(Method userIdMethod) {
        this.userIdMethod = userIdMethod;
    }

    /**
     * 获取参数值
     *
     * @param obj
     * @return
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(
                    DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "null";
            }

        }
        return value;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (auditEnable && invocation.getArgs()[0] instanceof MappedStatement) {
            // 获取参数1：MappedStatement对象
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];

            // 获取sql 操作类型
            String sqlCommandType = mappedStatement.getSqlCommandType().name();
            if (LogConstant.OperationEnum.insert.name().equalsIgnoreCase(sqlCommandType)
                    || LogConstant.OperationEnum.update.name().equalsIgnoreCase(sqlCommandType)
                    || LogConstant.OperationEnum.delete.name().equalsIgnoreCase(sqlCommandType)) {
                ISQLHandler sqlAuditHandler = null;
                try {
                    Executor executor = (Executor) invocation.getTarget();
                    Connection connection = executor.getTransaction().getConnection();
                    dbMetaDataHolder.init(connection);
                    Object parameter = null;
                    if (invocation.getArgs().length > 1) {
                        parameter = invocation.getArgs()[1];
                    }
                    // 可以执行的sql
                    BoundSql boundSql = mappedStatement.getBoundSql(parameter);

                    // mybatis所有的配置
                    Configuration configuration = mappedStatement.getConfiguration();
                    String sql = getParameterizedSql(configuration, boundSql);
                    if (LogConstant.OperationEnum.insert.name().equalsIgnoreCase(sqlCommandType)) {
                        sqlAuditHandler = new MySqlInsertSQLAuditHandler(connection, dbMetaDataHolder, userIdMethod, sql);
                    } else if (LogConstant.OperationEnum.update.name().equalsIgnoreCase(sqlCommandType)) {
                        sqlAuditHandler = new MySqlUpdateSQLAuditHandler(connection, dbMetaDataHolder, userIdMethod, sql);
                    } else if (LogConstant.OperationEnum.delete.name().equalsIgnoreCase(sqlCommandType)) {
                        sqlAuditHandler = new MySqlDeleteSqlAuditHandler(connection, dbMetaDataHolder, userIdMethod, sql);
                    }
                    if (sqlAuditHandler != null) {
                        sqlAuditHandler.preHandle();
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                Object result = invocation.proceed();
                try {
                    if (sqlAuditHandler != null) {
                        sqlAuditHandler.postHandle();
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                return result;
            }
        }
        return invocation.proceed();
    }

    private String getParameterizedSql(Configuration configuration, BoundSql boundSql) {
        //  参数对象
        Object parameterObject = boundSql.getParameterObject();
        //  参数映射
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = pattern2.matcher(boundSql.getSql()).replaceAll(" ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = pattern1.matcher(sql).replaceFirst(Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = pattern1.matcher(sql).replaceFirst(Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = pattern1.matcher(sql).replaceFirst(Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        sql = pattern1.matcher(sql).replaceFirst("缺失");
                    }
                }
            }
        }
        return sql;
    }


    /**
     * 判断是否拦截这个类型对象（根据@Intercepts注解决定），然后决定是返回一个代理对象还是返回原对象。
     * 如果是插件要拦截的对象时才执行Plugin.wrap方法，否则的话，直接返回目标本身。
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 拦截器需要一些变量对象，而且这个对象是支持可配置的。
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        final AuditProperties auditProperties = (AuditProperties) properties;
        auditEnable = auditProperties.getEnable();
        Boolean splitEnableOption = auditProperties.getSplit();
        List<String> listenTables = auditProperties.getListenTables();

        dbMetaDataHolder = new DBMetaDataHolder(new AuditLogTableCreator(splitEnableOption, listenTables));
    }

}
