package com.demo.mymybatis.session;

/**
 * SqlSessionFactory 的作用是创建 SqlSession 对象
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
