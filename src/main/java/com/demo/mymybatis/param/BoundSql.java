package com.demo.mymybatis.param;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SQL 解析和参数绑定工具
 */
public class BoundSql {
    private String parsedSql; // 替换后的 SQL
    private List<ParameterMapping> parameterMappings; // 参数映射

    public BoundSql(String parsedSql, List<ParameterMapping> parameterMappings) {
        this.parsedSql = parsedSql;
        this.parameterMappings = parameterMappings;
    }

    public String getParsedSql() {
        return parsedSql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public static BoundSql parse(String sql) {
        List<ParameterMapping> parameterMappings = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\{(\\w+)}");
        Matcher matcher = pattern.matcher(sql);

        StringBuffer parsedSqlBuffer = new StringBuffer();
        while (matcher.find()) {
            String paramName = matcher.group(1);
            parameterMappings.add(new ParameterMapping(paramName));
            matcher.appendReplacement(parsedSqlBuffer, "?");
        }
        matcher.appendTail(parsedSqlBuffer);

        return new BoundSql(parsedSqlBuffer.toString(), parameterMappings);
    }
}
