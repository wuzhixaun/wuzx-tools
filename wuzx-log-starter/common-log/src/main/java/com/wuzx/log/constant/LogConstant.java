package com.wuzx.log.constant;

/**
 * @author Hccake
 * @version 1.0
 * @date 2020/5/25 17:39
 */
/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName AccessLogFilter.java
 * @Description 日志常量类
 * @createTime 2022年06月06日 06:06:00
 */
public final class LogConstant {

    private LogConstant() {
    }

    /**
     * 跟踪ID，用于一次请求或执行方法时，产生的各种日志间的数据关联
     */
    public static final String TRACE_ID = "traceId";

    /**
     * log
     */
    public static final String EVENT_LOG = "log";

    /**
     * saveLop ip
     */
    public static final String SAVE_LOG_IP = "saveLogIp";

    /**
     * 数据库操作枚举
     */
    public enum OperationEnum {
        insert, update, delete
    }

}
