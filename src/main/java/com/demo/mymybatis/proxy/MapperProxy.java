package com.demo.mymybatis.proxy;

import com.demo.mymybatis.mapper.MappedStatement;
import com.demo.mymybatis.mapper.SqlCommandType;
import com.demo.mymybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * MapperProxy 是动态代理类，用于拦截方法调用并执行逻辑
 */
public class MapperProxy implements InvocationHandler {
    private final SqlSession sqlSession;
    private final Class<?> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<?> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获取方法名和接口的全限定名
        String methodName = method.getName();
        String className = mapperInterface.getName();

        // 生成 statementId
        String statementId = className + "." + methodName;

        // 根据返回类型和 MappedStatement 类型路由到不同的操作
        MappedStatement mappedStatement = sqlSession.getConfiguration().getMappedStatements().get(statementId);
        if (mappedStatement == null) {
            throw new RuntimeException("No mapped statement found for " + statementId);
        }

        SqlCommandType commandType = mappedStatement.getSqlCommandType();

        switch (commandType) {
            case SELECT:
                Class<?> returnType = method.getReturnType();
                if (returnType == List.class) {
                    return sqlSession.selectList(statementId, args == null ? null : args[0]);
                } else {
                    return sqlSession.selectOne(statementId, args == null ? null : args[0]);
                }
            case INSERT:
                return sqlSession.insert(statementId, args == null ? null : args[0]);
            case UPDATE:
                return sqlSession.update(statementId, args == null ? null : args[0]);
            case DELETE:
                return sqlSession.delete(statementId, args == null ? null : args[0]);
            default:
                throw new UnsupportedOperationException("Unknown command type: " + commandType);
        }
    }
}
