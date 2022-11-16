package com.wuzx.msg.recipient;

import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 指定用户筛选器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/10 11:38
 **/
@Component
@RequiredArgsConstructor
public class SpecifyUserRecipientFilter implements RecipientFilter {

    private final UserService userService;

    /**
     * 当前筛选器对应的筛选类型
     * @return 筛选类型对应的标识
     * @see NotifyRecipientFilterTypeEnum
     */
    @Override
    public Integer filterType() {
        return NotifyRecipientFilterTypeEnum.SPECIFY_USER.getValue();
    }

    /**
     * 接收者筛选
     * @param filterCondition 筛选条件 用户ID集合
     * @return 接收者集合
     */
    @Override
    public List<User> filter(List<Object> filterCondition) {
        List<Integer> userIds = filterCondition.stream().map(Integer.class::cast).collect(Collectors.toList());
        return userService.listByIds(userIds);
    }

    /**
     * 获取当前用户的过滤属性
     * @param sysUser 系统用户
     * @return 该用户所对应筛选条件的属性
     */
    @Override
    public Object getFilterAttr(User sysUser) {
        return sysUser.getId();
    }

    /**
     * 是否匹配当前用户
     *
     * @param filterAttr      筛选属性
     * @param filterCondition 筛选条件
     * @return boolean true: 是否匹配
     */
    @Override
    public boolean match(Object filterAttr, List<Object> filterCondition) {
        Integer userId = (Integer) filterAttr;
        return filterCondition.stream().map(Integer.class::cast).anyMatch(x -> x.equals(userId));
    }
}
