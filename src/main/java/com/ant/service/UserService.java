package com.ant.service;

import com.ant.dao.UserMapper;
import com.ant.model.User;
import com.ant.utils.DES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: Ant
 * @Date: 2018/09/29 10:26
 * @Description:
 */
@Service("UserService")
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findIfExist(String account, String password){
        User user = userMapper.find(account);
        if (Objects.equals(user.getPassword(), password)){
            return user;
        }
        return null;
    }


}
