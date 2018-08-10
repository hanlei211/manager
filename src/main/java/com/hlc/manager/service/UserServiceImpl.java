package com.hlc.manager.service;

import com.hlc.manager.entity.User;
import com.hlc.manager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/10
 * @Modify by
 */
@Service
public class UserServiceImpl  implements UserService {
    @Autowired
    UserMapper  userMapper;
    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }
}
