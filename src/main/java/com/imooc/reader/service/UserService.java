package com.imooc.reader.service;

import com.imooc.reader.entity.User;

public interface UserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public User checkLogin(String username, String password);
}
