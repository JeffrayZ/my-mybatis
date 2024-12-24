package com.demo.mymybatis.mapper;

import com.demo.mymybatis.User;

import java.util.List;

/**
 * 与 UserMapper.xml 对应
 */
public interface UserMapper {
    User selectUserById(int id);

    List<User> selectAllUsers();

    void insertUser(User user);
}
