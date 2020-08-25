package com.itheima.mapper;

import com.itheima.anno.Select;
import com.itheima.domain.User;

import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 03:51
 */
public interface UserMapper2 {

    @Select("select  * from user")
    List<User> findAllTwo();
}
