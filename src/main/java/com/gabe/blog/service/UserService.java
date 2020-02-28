package com.gabe.blog.service;

import com.gabe.blog.po.User;

public interface UserService {
    User checkUser (String email, String password);
    User saveUser (User user);
}
