package com.wuzx.msg.recipient;

import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.qo.UserQO;
import com.wuzx.msg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 指定部门筛选器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:29
 **/

@Component
@RequiredArgsConstructor
public class SpecifyDeptRecipientFilter implements RecipientFilter {

    private final UserService userService;

    @Override
    public Integer filterType() {
        return NotifyRecipientFilterTypeEnum.SPECIFY_ORGANIZATION.getValue();
    }

    @Override
    public List<User> filter(List<Object> filterCondition) {
        List<Integer> deptIds = filterCondition.stream().map(Integer.class::cast).collect(Collectors.toList());
        // 根据部门查询用户列表
        return userService.getUserList(UserQO.builder().deptIds(deptIds).build());
    }

    @Override
    public Object getFilterAttr(User sysUser) {
        return sysUser.getDeptId();
    }

    @Override
    public boolean match(Object filterAttr, List<Object> filterCondition) {
        Integer organizationId = (Integer) filterAttr;
        return filterCondition.stream().map(Integer.class::cast).anyMatch(x -> x.equals(organizationId));
    }
}
