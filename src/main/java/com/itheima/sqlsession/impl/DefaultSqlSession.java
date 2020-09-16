package com.itheima.sqlsession.impl;

import com.itheima.sqlsession.Configuration;
import com.itheima.sqlsession.SqlSession;
import com.itheima.sqlsession.utils.Converter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 06:01
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public List<Object> selectList(String s) {

        //预编译
        PreparedStatement pstm = null;
        ResultSet rst = null;
        Connection conn = null;
        List<Object> list = null;
        try {
            //执行jdbc5步
            //获取链接
            conn = configuration.getConnection();
            pstm = conn.prepareStatement(configuration.getMap().get(s).getSql());
            rst = pstm.executeQuery();
            list = Converter.getList(rst, configuration.getMap().get(s).getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                pstm.close();
                rst.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        }

    }


    @Override
    public void close() {

    }

    @Override
    public <T> T getMapper(Class<T> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class[] interfaces = {clazz};
        T proxyInstance = (T) Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Class<?> returnType = method.getReturnType();
                String methodName = method.getName();
                Class<?> declaringClass = method.getDeclaringClass();
                String className = declaringClass.getName();
                String key = className + "." + methodName;

                if (returnType == List.class) {
                    List<Object> list = selectList(key);
                    return list;
                }
                return null;
            }
        });

        return proxyInstance;
    }
}
