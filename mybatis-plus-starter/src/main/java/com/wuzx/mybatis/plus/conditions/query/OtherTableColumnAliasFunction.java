package com.yh.mybatis.plus.conditions.query;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

/**
 * 连表查询时，从其他表获取的查询条件
 *
 * @author  wuzhixuan
 */
@FunctionalInterface
public interface OtherTableColumnAliasFunction<T> extends SFunction<T, String> {

}
