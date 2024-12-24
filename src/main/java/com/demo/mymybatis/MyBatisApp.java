package com.demo.mymybatis;

import com.demo.mymybatis.mapper.UserMapper;
import com.demo.mymybatis.session.SqlSession;
import com.demo.mymybatis.session.SqlSessionFactory;
import com.demo.mymybatis.session.SqlSessionFactoryBuilder;

import java.util.List;

public class MyBatisApp {
    public static void main(String[] args) {
        /*

        // 解析配置文件
        Configuration configuration = ConfigParser.parseConfig("mybatis-config.xml");
        // 输出解析结果
        System.out.println("Driver: " + configuration.getDriver());
        System.out.println("URL: " + configuration.getUrl());
        System.out.println("Username: " + configuration.getUsername());
        System.out.println("Password: " + configuration.getPassword());
        System.out.println("Mapper Locations: " + configuration.getMapperLocations());

        */

        /*

        // 解析每个 Mapper 文件
        MapperParser mapperParser = new MapperParser(configuration);
        for (String mapperLocation : configuration.getMapperLocations()) {
            mapperParser.parse(mapperLocation);
        }
        // 输出解析结果
        System.out.println("Mapped Statements:");
        configuration.getMappedStatements().forEach((id, ms) -> {
            System.out.println("ID: " + id);
            System.out.println("SQL: " + ms.getSql());
            System.out.println("Parameter Type: " + ms.getParameterType());
            System.out.println("Result Type: " + ms.getResultType());
            System.out.println();
        });

        */



        /*

        // 构建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build("mybatis-config.xml");

        // 创建 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行查询
        String statementId = "com.example.mapper.UserMapper.selectUserById";
        int userId = 1;
        Object result = sqlSession.selectOne(statementId, userId);

        // 输出结果
        System.out.println("Query Result: " + result);

        */

        // 构建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build("mybatis-config.xml");

        // 创建 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 获取动态代理的 Mapper
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

        // 调用方法执行查询
        User user = userMapper.selectUserById(1);
        System.out.println("User: " + user);

        // 查询所有用户
        List<User> users = userMapper.selectAllUsers();
        users.forEach(System.out::println);

        // 插入用户
        User newUser = new User();
        newUser.setName("赵六");
        newUser.setAge(5);
        userMapper.insertUser(newUser);
    }
}
