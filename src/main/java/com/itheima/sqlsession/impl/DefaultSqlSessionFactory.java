package com.itheima.sqlsession.impl;

import com.itheima.sqlsession.Configuration;
import com.itheima.sqlsession.SqlSession;
import com.itheima.sqlsession.SqlSessionFactory;
import com.itheima.sqlsession.utils.XMLParse;

import java.io.InputStream;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 06:00
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private InputStream is;

    public DefaultSqlSessionFactory(InputStream is) {
        this.is=is;
    }

    @Override
    public SqlSession openSession() {

        DefaultSqlSession defaultSqlSession = new DefaultSqlSession();
        Configuration configuration = XMLParse.loadConfiguration(is);
        defaultSqlSession.setConfiguration(configuration);
        return defaultSqlSession;
    }
}
