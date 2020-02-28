package com.gabe.blog.service;

import com.gabe.blog.dao.TypeRepository;
import com.gabe.blog.exception.NotFoundException;
import com.gabe.blog.po.Tag;
import com.gabe.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);

    }

    @Override
    public List<Type> ListType() {
        return typeRepository.findAll();
    }

    @Override
    public Page<Type> ListType(@PageableDefault(size = 5, direction = Sort.Direction.DESC)Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepository.findById(id).orElse(null);
        if (type1 == null) {
            throw new NotFoundException("The type is not exist");
        }
        BeanUtils.copyProperties(type, type1);
        return typeRepository.save(type1);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
