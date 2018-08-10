package com.hlc.manager.service;

import com.hlc.manager.entity.User;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/10
 * @Modify by
 */
public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User record);

    User findUserByName(String name);
}
