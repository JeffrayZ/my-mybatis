package com.demo.mymybatis.session;

import com.demo.mymybatis.config.Configuration;

/**
 * DefaultSqlSessionFactory 是 SqlSessionFactory 的默认实现类
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
