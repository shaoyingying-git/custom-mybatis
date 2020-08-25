package com.itheima.io;

import java.io.InputStream;

/**
 * @author Chen Shaoying
 * @date 2020-08-24 05:43
 */
public class Resources {
    public static InputStream getResourceAsStream(String s) {
        InputStream is = Resources.class.getClassLoader().getResourceAsStream(s);
        return is;
    }
}
