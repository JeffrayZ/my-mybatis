package com.demo.mymybatis.param;

import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * 为了支持多列映射为对象（如 User 对象），需要添加结果映射逻辑
 */
public class ResultMapper {
    public static <T> T map(ResultSet resultSet, Class<T> type) throws Exception {
        T instance = type.getDeclaredConstructor().newInstance();
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = resultSet.getObject(field.getName());
            field.set(instance, value);
        }
        return instance;
    }
}
