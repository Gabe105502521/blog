package com.gabe.blog.service;

import com.gabe.blog.po.Type;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

public interface TypeService  {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> ListType();

    Page<Type> ListType(Pageable pageable);

    Type updateType(Long id, Type type);

    void deleteType(Long id);
}
