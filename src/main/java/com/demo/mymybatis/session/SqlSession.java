package com.demo.mymybatis.session;

import com.demo.mymybatis.config.Configuration;

import java.util.List;

/**
 * SqlSession 是用于执行数据库操作的接口
 */
public interface SqlSession {
    // 基本的增删改查
    <T> T selectOne(String statementId, Object parameter);
    <T> List<T> selectList(String statementId, Object parameter);
    int insert(String statementId, Object parameter);
    int update(String statementId, Object parameter);
    int delete(String statementId, Object parameter);

    Configuration getConfiguration();

    // 动态代理获取 Mapper
    <T> T getMapper(Class<T> type);
}
