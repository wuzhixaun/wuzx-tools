package com.wuzx.msg.recipient;

import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.entity.User;

import java.util.List;

/**
 * 接受者筛选器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 11:35
 **/
public interface RecipientFilter {
    /**
     * 当前筛选器对应的筛选类型
     * @see NotifyRecipientFilterTypeEnum
     * @return 筛选类型对应的标识
     */
    Integer filterType();

    /**
     * 接收者筛选
     * @param filterCondition 筛选条件
     * @return 接收者集合
     */
    List<User> filter(List<Object> filterCondition);

    /**
     * 获取当前用户的过滤属性
     * @param sysUser 系统用户
     * @return 该用户所对应筛选条件的属性
     */
    Object getFilterAttr(User sysUser);

    /**
     * 是否匹配当前用户
     * @param filterAttr 筛选属性
     * @param filterCondition 筛选条件
     * @return boolean true: 是否匹配
     */
    boolean match(Object filterAttr, List<Object> filterCondition);
}
