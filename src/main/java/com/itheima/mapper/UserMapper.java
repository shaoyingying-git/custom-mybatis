package com.itheima.mapper;

import com.itheima.domain.User;

import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 03:51
 */
public interface UserMapper {

    List<User> findAll();
}
