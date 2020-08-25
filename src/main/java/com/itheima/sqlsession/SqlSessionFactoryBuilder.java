package com.itheima.sqlsession;

import com.itheima.sqlsession.impl.DefaultSqlSessionFactory;

import java.io.InputStream;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 05:57
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream is) {
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(is);
        return sqlSessionFactory;
    }
}
