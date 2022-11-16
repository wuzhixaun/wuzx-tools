package com.wuzx.msg.recipient;

import com.wuzx.msg.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 11:36
 **/
@Slf4j
@Component
public class RecipientHandler {

    private final Map<Integer, RecipientFilter> recipientFilterMap = new LinkedHashMap<>();

    public RecipientHandler(List<RecipientFilter> recipientFilterList) {
        for (RecipientFilter recipientFilter : recipientFilterList) {
            this.recipientFilterMap.put(recipientFilter.filterType(), recipientFilter);
        }
    }


    public List<User> query(Integer filterType, List<Object> filterCondition) {
        RecipientFilter recipientFilter = recipientFilterMap.get(filterType);
        if (recipientFilter == null) {
            log.error("Unknown recipient filter：[{}]，filterCondition：{}", filterType, filterCondition);
            return new ArrayList<>();
        }
        return recipientFilter.filter(filterCondition);
    }


    /**
     * 判断当前是否匹配
     * @param recipientFilterType 筛选类型
     * @param filterAttr 筛选属性
     * @param recipientFilterCondition 筛选条件
     * @return boolean true：匹配
     */
    public boolean match(Integer recipientFilterType, Object filterAttr, List<Object> recipientFilterCondition) {
        RecipientFilter recipientFilter = recipientFilterMap.get(recipientFilterType);
        return recipientFilter != null && recipientFilter.match(filterAttr, recipientFilterCondition);
    }


    /**
     * 获取当前用户的各个筛选器对应的属性
     * @param sysUser 系统用户
     * @return 属性Map key：filterType value: attr
     */
    public Map<Integer, Object> getFilterAttrs(User sysUser) {
        Map<Integer, Object> map = new HashMap<>(recipientFilterMap.size());
        for (RecipientFilter recipientFilter : recipientFilterMap.values()) {
            Object obj = recipientFilter.getFilterAttr(sysUser);
            map.put(recipientFilter.filterType(), obj);
        }
        return map;
    }


}
