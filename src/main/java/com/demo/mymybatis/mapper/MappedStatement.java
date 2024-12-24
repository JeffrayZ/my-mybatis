package com.demo.mymybatis.mapper;

/**
 * MappedStatement 是用于存储单条 SQL 的信息的类，包含以下内容：
 *
 * SQL 语句
 * 参数类型
 * 返回结果类型
 */
public class MappedStatement {
    private String id;              // SQL 的唯一标识（如 selectUserById）
    private String sql;             // SQL 语句
    private String parameterType;   // 参数类型
    private String resultType;      // 返回值类型
    private SqlCommandType sqlCommandType; // 确定操作类型

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(SqlCommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
