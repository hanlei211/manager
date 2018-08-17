package com.hlc.manager.mapper;

import com.hlc.manager.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author rjyx
 * @Description
 * @Date create in 2018/8/10
 * @Modify by
 */
@Mapper
public interface UserMapper {

    User findUserByName(String name);
}
