package com.mall.b2bp.utils;

import java.io.InputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Sheldon通用工具包
 * @author Sheldon
 */
public class CommonUtil {

    static ObjectMapper objectMapper;
    protected static Logger logger = Logger.getLogger(CommonUtil.class);

    public static <T> T readValue(String content, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 把Json转换为json字符串
     *By : Jackson
     * @param object
     *            JavaBean对象
     * @return json字符串
     */
    	/*jackson*/
    public static String toJsonString(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /*fastjson*/
//    public static String toJsonString(Object object) {
//    	return toJSONString(object);
//    }
    /**
     * 把Json转换为json字符串，自带日期格式化功能
     *By : Fastjson
     * @param object
     *            JavaBean对象
     * @return json字符串
     */
//    public static String toJsonStringWithDateFormat(Object object) {
//    	return toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
//    }

    /**
     * 把JavaBean转换为jsonNode
     *
     * @param is
     *            InputStream对象
     * @return JsonNode
     */
    public static JsonNode readerValue(InputStream is) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.readTree(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

	// Print with a newline:
	public static void print(Object obj) {
		System.out.println(obj);
	}

	// Print a newline by itself:
	public static void print() {
		System.out.println();
	}

	// Print with no line break:
	public static void printnb(Object obj) {
		System.out.print(obj);
	}

	// The new Java SE5 printf() (from C):
	public static PrintStream printf(String format, Object... args) {
		return System.out.printf(format, args);
	}
	
    /**
     * 输出JSON格式的数据信息
     *  注 ：自带日期格式化
     * @param Object
     */
	public static void printJSON(Object object){
		System.out.println(toJsonString(object));
	}

}
