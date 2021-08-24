package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.User;
import com.imooc.reader.mapper.UserMapper;
import com.imooc.reader.service.UserService;
import com.imooc.reader.service.exception.BussinessException;
import com.imooc.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    public User checkLogin(String username, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BussinessException("U02", "用户不存在");
        }
        String md5 = MD5Utils.md5Digest(password, user.getSalt());
        if (!md5.equals(user.getPassword())) {
            throw new BussinessException("U03", "输入密码有误");
        }
        return user;
    }
}

