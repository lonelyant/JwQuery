package com.ant.dao;

import com.ant.model.User;
import org.springframework.stereotype.Repository;

/**
 * @author: Ant
 * @Date: 2018/09/28 17:25
 * @Description:
 */
@Repository("UserMapper")
public interface UserMapper {
    void save(User user);
    void update(User user);
    User find(String account);
}
