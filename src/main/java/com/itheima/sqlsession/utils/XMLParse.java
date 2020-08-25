package com.itheima.sqlsession.utils;

import com.itheima.anno.Select;
import com.itheima.io.Resources;
import com.itheima.sqlsession.Configuration;
import com.itheima.sqlsession.Mapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 09:05
 */
public class XMLParse {
    public static Configuration loadConfiguration(InputStream is) {


        try {
            //把配置信息转换成document
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            Configuration configuration = new Configuration();
            //解析主配置
            resolveMasterConfiguration(document, configuration);
            //解析映射配置
            resolveMappingProfile(document, configuration);

            return configuration;
        } catch (DocumentException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    //解析主配置
    private static void resolveMasterConfiguration(Document document, Configuration configuration) {
        List<Element> elements = document.selectNodes("//property");
        for (Element element : elements) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            if (name.equals("driver")) {
                configuration.setDriver(value);
            }

            if (name.equals("url")) {
                configuration.setUrl(value);
            }

            if (name.equals("username")) {
                configuration.setUsername(value);
            }

            if (name.equals("password")) {
                configuration.setPassword(value);
            }
        }
    }

    //解析映射配置
    public static void resolveMappingProfile(Document document,Configuration configuration) {
        try {
            //映射配置把信息全部封装进map
            HashMap<String, Mapper> map = new HashMap<>();
            List<Element> mappers = document.selectNodes("//mapper");
            for (Element element : mappers) {
                String resource = element.attributeValue("resource");

                //resource不为空，则走配置文件方式
                if (resource != null) {
                    XMLFileParsing(map, resource);

                    //resource 为空，走注解方式
                } else {
                    annotationParsing(map, element);

                }

            }

           configuration.setMap(map);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private  static void XMLFileParsing(HashMap<String, Mapper> map, String path) throws DocumentException {
        //把映射配置文件转换成documet对象
        InputStream in = Resources.getResourceAsStream(path);
        Document document1 = new SAXReader().read(in);

        //获取namepace
        Element rootElement = document1.getRootElement();
        String namespace = rootElement.attributeValue("namespace");

        //获取select标签信息
        List<Element> selectElement = document1.selectNodes("//select");
        //遍历所有的selec标签，把sql，resultType封装进Mapper，并且把每一个Mapper存储进map集合
        for (Element select : selectElement) {
            Mapper mapper = new Mapper();
            String id = select.attributeValue("id");
            String resultType = select.attributeValue("resultType");
            String sql = select.getTextTrim();

            mapper.setSql(sql);
            mapper.setResultType(resultType);

            String key = namespace + "." + id;
            map.put(key, mapper);

        }
    }

    private static void annotationParsing(HashMap<String, Mapper> map, Element element) throws ClassNotFoundException {
        //获取接口权限定名
        String className = element.attributeValue("class");

        //反射获取接口所有方法
        Class<?> aClass = Class.forName(className);
        Method[] methods = aClass.getDeclaredMethods();


        //遍历方法获取注解，得到方法名，sql，返回值类型
        for (Method method : methods) {
            //定义mapper用来封装sql，返回值类型
            Mapper mapper = new Mapper();
            //获取方法名
            String methodName = method.getName();

            //判断方法是否有select注解
            boolean flag = method.isAnnotationPresent(Select.class);
            //select为true，则代表有select注解
            if (flag) {
                //获取select注解中的sql
                Select annotation = method.getAnnotation(Select.class);
                String sql = annotation.value();


                //获取返回值类型
                Type gtype = method.getGenericReturnType();
                //判断返回值类型是否带泛型
                if (gtype instanceof ParameterizedType) {
                    //强转为参数化泛型
                    ParameterizedType ptype = (ParameterizedType) gtype;
                    //获取参数泛型的泛型
                    Type[] types = ptype.getActualTypeArguments();
                    Class typeClass = (Class) types[0];
                    //获取返回值类型全限定名
                    String resultType = typeClass.getName();
                    //封装mapper
                    mapper.setSql(sql);
                    mapper.setResultType(resultType);
                    //将mapper存储进map集合
                    String key = className + "." + methodName;
                    map.put(key, mapper);
                }

            }

        }
    }



}
