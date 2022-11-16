package com.wuzx.msg.recipient;

import com.wuzx.msg.constant.NotifyRecipientFilterTypeEnum;
import com.wuzx.msg.entity.User;
import com.wuzx.msg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 所有用户筛选器
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:19
 **/

@Component
@RequiredArgsConstructor
public class AllUserRecipientFilter implements RecipientFilter {

    private final UserService userService;

    @Override
    public Integer filterType() {
        return NotifyRecipientFilterTypeEnum.ALL.getValue();
    }

    @Override
    public List<User> filter(List<Object> filterCondition) {
        return userService.list();
    }

    @Override
    public Object getFilterAttr(User sysUser) {
        return null;
    }

    @Override
    public boolean match(Object filterAttr, List<Object> filterCondition) {
        return true;
    }
}
