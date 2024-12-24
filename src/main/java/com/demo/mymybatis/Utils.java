package com.demo.mymybatis;

import java.lang.reflect.Field;

public class Utils {

    /**
     * 获取指定对象的指定属性值
     */
    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> paramClass = object.getClass();
        Field field = paramClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 判断是否为简单类型的方法
     */
    public static boolean isSimpleType(Object parameter) {
        return parameter instanceof Integer || parameter instanceof Long ||
                parameter instanceof Double || parameter instanceof Float ||
                parameter instanceof Boolean || parameter instanceof String ||
                parameter instanceof Short || parameter instanceof Byte ||
                parameter.getClass().isPrimitive(); // 基本类型如 int、boolean 等
    }
}
