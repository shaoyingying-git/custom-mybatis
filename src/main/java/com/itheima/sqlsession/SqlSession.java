package com.itheima.sqlsession;

import com.itheima.mapper.UserMapper;

import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 06:01
 */
public interface SqlSession {
    List<Object> selectList(String s);

    void close();

    <T>T getMapper(Class<T> mapperClass);
}
