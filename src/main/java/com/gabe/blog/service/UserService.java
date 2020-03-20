package com.gabe.blog.service;

import com.gabe.blog.po.Tag;
import com.gabe.blog.po.User;

public interface UserService {
    User checkUser (String email, String password);
    User getUser (Long id);
    User saveUser (User user);
    User updateUser(Long id, User user);
}
