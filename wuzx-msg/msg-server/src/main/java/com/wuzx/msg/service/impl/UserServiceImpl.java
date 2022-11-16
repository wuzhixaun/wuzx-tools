package com.wuzx.msg.service.impl;

import com.wuzx.msg.entity.User;
import com.wuzx.msg.mapper.UserMapper;
import com.wuzx.msg.qo.UserQO;
import com.wuzx.msg.service.UserService;
import com.yh.mybatis.plus.service.impl.ExtendServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:35
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ExtendServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<User> getUserList(UserQO userQO) {
        return baseMapper.getUserList(userQO);
    }

    @Override
    public List<Integer> getUserIdByRoleId(List<Integer> roleIds) {
        return baseMapper.getUserIdByRoleId(roleIds);
    }

}
