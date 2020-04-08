package com.nick.springsecurity.dao;

import com.nick.springsecurity.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User user);
}
