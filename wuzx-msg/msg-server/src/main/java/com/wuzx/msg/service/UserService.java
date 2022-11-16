package com.wuzx.msg.service;

import com.wuzx.msg.entity.User;
import com.wuzx.msg.qo.UserQO;
import com.yh.mybatis.plus.service.ExtendService;

import java.util.List;

/**
 * 用户
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 14:34
 **/
public interface UserService extends ExtendService<User> {


    /**
     * 根据条件获取用户id
     * @param userQO
     * @return
     */
    List<User> getUserList(UserQO userQO);


    /**
     * 根据角色roleId获取用户id
     * @param roleIds
     * @return
     */
    List<Integer> getUserIdByRoleId(List<Integer> roleIds);
}
