package com.itheima.sqlsession.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-25 13:29
 */
public class Converter {

    public static List<Object> getList(ResultSet rst, String className) {
        try {
            Class aClass = Class.forName(className);

            List<Object> list = new ArrayList<>();
            while (rst.next()) {
                Object e = aClass.newInstance();
                ResultSetMetaData metaData = rst.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object value = rst.getObject(columnName);

                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, aClass);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(e, value);
                }
                list.add(e);
            }

            return list;


        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        }


    }
}
