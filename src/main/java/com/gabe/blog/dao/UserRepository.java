package com.gabe.blog.dao;

import com.gabe.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);
}
