package com.nick.springsecurity.service;

import com.nick.springsecurity.entity.User;
import com.nick.springsecurity.user.CrmUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(CrmUser crmUser);
}
