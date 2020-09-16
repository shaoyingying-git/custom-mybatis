package com.itheima.test;

import com.itheima.domain.User;
import com.itheima.io.Resources;
import com.itheima.mapper.UserMapper;
import com.itheima.sqlsession.SqlSession;
import com.itheima.sqlsession.SqlSessionFactory;
import com.itheima.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 04:06
 */
public class MybatisTest {

    @Test
    public void testFindAll() throws IOException {
        InputStream is = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = builder.build(is);
        SqlSession sqlSession = build.openSession();
        UserMapper userProxy= sqlSession.getMapper(UserMapper.class);
        List<User> userList = userProxy.findAll();
        List<Object> list = sqlSession.selectList("com.itheima.mapper.UserMapper.findAll");

        for (Object obj : list) {
            System.out.println(obj);
        }

        for (User user : userList) {
            System.out.println("getMapper方式"+user);
        }

        sqlSession.close();
        is.close();


    }
}
