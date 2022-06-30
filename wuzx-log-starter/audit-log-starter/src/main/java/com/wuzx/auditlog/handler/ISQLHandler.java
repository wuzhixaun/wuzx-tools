package com.wuzx.auditlog.handler;



/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName ISQLHandler.java
 * @Description SQLHandler 接口
 * @createTime 2022年05月23日 16:43:00
 */
public interface ISQLHandler {
    /**
     * 执行之前
     */
    void preHandle();

    /**
     * 执行之后
     */
    void postHandle();
}
