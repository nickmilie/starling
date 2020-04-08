package com.nick.springsecurity.dao;

import com.nick.springsecurity.entity.Role;

public interface RoleDao {

    public Role findRoleByName(String theRoleName);

}
