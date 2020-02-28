package com.gabe.blog.service;

import com.gabe.blog.po.Blog;
import com.gabe.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogService {
    Blog saveBlog(Blog blog);

    Blog getBlog(Long id);

    Page<Blog> ListBlog(Pageable pageable);

    Page<Blog> ListBlog(Pageable pageable, BlogQuery blog);

    void deleteBlog(Long id);

    Blog updataBlog(Long id, Blog blog);
}
