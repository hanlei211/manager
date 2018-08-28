package com.hlc.manager.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hlc.manager.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/10
 * @Modify by
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User findUserByName(String name);

    List<User> getUsers(String name);
}
