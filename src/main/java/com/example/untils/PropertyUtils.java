package com.example.untils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public  class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
    private static Properties props;
    static{
        loadProps();
    }
    synchronized static private void loadProps(){
        props = new Properties();
        InputStream in = null;
        try {
//　　　　　　　<!--第一种，通过类加载器进行获取properties文件流-->
                    in = PropertyUtils.class.getClassLoader().getResourceAsStream("system.properties");
//　　　　　　  <!--第二种，通过类进行获取properties文件流-->
                    //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
                    props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("jdbc.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("jdbc.properties文件流关闭出现异常");
            }
        }
    }
    public static String getProperty(String key){
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
