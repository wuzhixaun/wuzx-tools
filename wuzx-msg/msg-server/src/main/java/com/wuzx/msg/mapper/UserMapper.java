package com.wuzx.msg.mapper;

import com.wuzx.msg.entity.User;
import com.wuzx.msg.qo.UserQO;
import com.yh.mybatis.plus.conditions.query.LambdaQueryWrapperX;
import com.yh.mybatis.plus.mapper.ExtendMapper;
import com.yh.mybatis.plus.toolkit.WrappersX;

import java.util.List;

/**
 * 用户‘
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 11:16
 **/
public interface UserMapper extends ExtendMapper<User> {

    default List<User> getUserList(UserQO userQO) {

        final LambdaQueryWrapperX<User> query = WrappersX.lambdaQueryX(User.class)
                .inIfPresent(User::getDeptId, userQO.getDeptIds())
                .inIfPresent(User::getId, userQO.getUserIds())
                .eqIfPresent(User::getStatus, 0); // 生效
        return selectList(query);
    }

    List<Integer> getUserIdByRoleId(List<Integer> roleIds);
}
