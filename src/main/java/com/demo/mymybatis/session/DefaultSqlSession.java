package com.demo.mymybatis.session;

import com.demo.mymybatis.Utils;
import com.demo.mymybatis.config.Configuration;
import com.demo.mymybatis.mapper.MappedStatement;
import com.demo.mymybatis.param.BoundSql;
import com.demo.mymybatis.param.ParameterMapping;
import com.demo.mymybatis.param.ResultMapper;
import com.demo.mymybatis.proxy.MapperProxy;

import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DefaultSqlSession 是 SqlSession 的默认实现类，负责具体的数据库操作
 */
public class DefaultSqlSession implements SqlSession {
    private final Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(configuration.getDriver());
        return DriverManager.getConnection(
                configuration.getUrl(),
                configuration.getUsername(),
                configuration.getPassword()
        );
    }

    @Override
    public <T> T selectOne(String statementId, Object parameter) {
        List<T> results = selectList(statementId, parameter);
        if (results.size() == 1) {
            return results.get(0);
        } else if (results.size() > 1) {
            throw new RuntimeException("Expected one result, but found: " + results.size());
        } else {
            return null;
        }
    }

    @Override
    public <T> List<T> selectList(String statementId, Object parameter) {
        try (Connection connection = getConnection()) {
            MappedStatement mappedStatement = configuration.getMappedStatements().get(statementId);

            // 使用 BoundSql 解析 SQL
            BoundSql boundSql = BoundSql.parse(mappedStatement.getSql());
            String sql = boundSql.getParsedSql();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (parameter != null) {
                    // 参数绑定
                    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                    if (parameterMappings.size() == 1 && Utils.isSimpleType(parameter)) {
                        // 单个简单类型参数绑定
                        preparedStatement.setObject(1, parameter);
                    } else if (parameter instanceof Map) {
                        // Map 参数绑定
                        Map<String, Object> paramMap = (Map<String, Object>) parameter;
                        for (int i = 0; i < parameterMappings.size(); i++) {
                            String propertyName = parameterMappings.get(i).getProperty();
                            preparedStatement.setObject(i + 1, paramMap.get(propertyName));
                        }
                    } else {
                        // 复杂对象参数绑定（通过反射）
                        for (int i = 0; i < parameterMappings.size(); i++) {
                            String propertyName = parameterMappings.get(i).getProperty();
                            Object value = Utils.getFieldValue(parameter, propertyName);
                            preparedStatement.setObject(i + 1, value);
                        }
                    }
                }

                // 执行sql
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<T> results = new ArrayList<>();
                    while (resultSet.next()) {
                        if (mappedStatement.getResultType() != null) {
                            Class<?> resultType = Class.forName(mappedStatement.getResultType());
                            results.add((T) ResultMapper.map(resultSet, resultType));
                        } else {
                            results.add((T) resultSet.getObject(1)); // 默认处理
                        }
                    }
                    return results;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insert(String statementId, Object parameter) {
        return executeUpdate(statementId, parameter);
    }

    @Override
    public int update(String statementId, Object parameter) {
        return executeUpdate(statementId, parameter);
    }

    @Override
    public int delete(String statementId, Object parameter) {
        return executeUpdate(statementId, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        // 创建动态代理对象
        return (T) Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[]{type},
                new MapperProxy(this, type)
        );
    }

    private int executeUpdate(String statementId, Object parameter) {
        try (Connection connection = getConnection()) {
            MappedStatement mappedStatement = configuration.getMappedStatements().get(statementId);

            // 使用 BoundSql 解析 SQL
            BoundSql boundSql = BoundSql.parse(mappedStatement.getSql());
            String sql = boundSql.getParsedSql();

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (parameter != null) {
                    // 参数绑定
                    List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
                    if (parameterMappings.size() == 1 && Utils.isSimpleType(parameter)) {
                        // 单个简单类型参数绑定
                        preparedStatement.setObject(1, parameter);
                    } else if (parameter instanceof Map) {
                        // Map 参数绑定
                        Map<String, Object> paramMap = (Map<String, Object>) parameter;
                        for (int i = 0; i < parameterMappings.size(); i++) {
                            String propertyName = parameterMappings.get(i).getProperty();
                            preparedStatement.setObject(i + 1, paramMap.get(propertyName));
                        }
                    } else {
                        // 复杂对象参数绑定（通过反射）
                        for (int i = 0; i < parameterMappings.size(); i++) {
                            String propertyName = parameterMappings.get(i).getProperty();
                            Object value = Utils.getFieldValue(parameter, propertyName);
                            preparedStatement.setObject(i + 1, value);
                        }
                    }
                }
                // 执行sql
                return preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
