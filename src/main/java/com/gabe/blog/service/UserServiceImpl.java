package com.gabe.blog.service;

import com.gabe.blog.dao.UserRepository;
import com.gabe.blog.exception.NotFoundException;
import com.gabe.blog.po.Tag;
import com.gabe.blog.po.User;
import com.gabe.blog.util.MD5Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String email, String password) {
        //User user = userRepository.findByEmail(email);
        User user = userRepository.findByEmailAndPassword(email, MD5Utils.code(password));
        return user;
    }

    @Override
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }


    @Override
    public User saveUser(User user) {
        user.setPassword(MD5Utils.code(user.getPassword()));
        //System.out.println(user.getPassword());
        User user1 = userRepository.save(user);
        return user1;
    }

    @Override
    public User updateUser(Long id, User user) {
        User u = userRepository.findById(id).orElse(null);
        if(u == null) {
            throw  new NotFoundException("The tag is not exist");
        }
        BeanUtils.copyProperties(user, u);
        return userRepository.save(u);
    }


}
