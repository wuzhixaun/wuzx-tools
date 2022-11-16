package com.wuzx.msg.recipient;

import cn.hutool.core.collection.CollectionUtil;
import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.qo.UserQO;
import com.wuzx.msg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:24
 **/

@Component
@RequiredArgsConstructor
public class SpecifyRoleRecipientFilter implements RecipientFilter{


    private UserService userService;

    @Override
    public Integer filterType() {
        return NotifyRecipientFilterTypeEnum.SPECIFY_ROLE.getValue();
    }

    @Override
    public List<User> filter(List<Object> filterCondition) {
        List<Integer> roleCodes = filterCondition.stream().map(Integer.class::cast).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(roleCodes)) {
            return Collections.emptyList();
        }
        // 查询对应角色的用户ids
        List<Integer> userIds = userService.getUserIdByRoleId(roleCodes);
        if (CollectionUtil.isEmpty(userIds)) {
            return Collections.emptyList();
        }

        // 根据用户id查询用户信息
        return userService.getUserList(UserQO.builder().userIds(userIds).build());
    }

    @Override
    public Object getFilterAttr(User sysUser) {
        return null;
    }

    @Override
    public boolean match(Object filterAttr, List<Object> filterCondition) {
        if (!(filterAttr instanceof List)) {
            return false;
        }
        List<String> roleCodes = (List<String>) filterAttr;
        if (CollectionUtil.isEmpty(roleCodes)) {
            return false;
        }
        for (Object roleCode : roleCodes) {
            boolean matched = filterCondition.stream().map(String.class::cast).anyMatch(x -> x.equals(roleCode));
            if (matched) {
                return true;
            }
        }
        return false;
    }
}
