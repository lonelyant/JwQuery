package com.ant.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author: Ant
 * @Date: 2018/10/09 17:26
 * @Description: properties文件解析类
 */
public class PropertiesUtil {

    public static Properties argumentParse(String path) throws Exception {
        // 读取配置文件
        Properties props = new Properties();
        props.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(path));
        return props;
    }

    //解析config.properties
    public static Properties argumentConfigParse() {

        try {
            return argumentParse("config.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
