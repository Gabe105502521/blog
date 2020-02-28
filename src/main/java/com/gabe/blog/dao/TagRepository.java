package com.gabe.blog.dao;

import com.gabe.blog.po.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagByName(String  name);


    //List<Tag> findAll(String ids);
}
