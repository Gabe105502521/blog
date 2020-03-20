package com.gabe.blog.service;

import com.gabe.blog.dao.BlogRepository;
import com.gabe.blog.exception.NotFoundException;
import com.gabe.blog.po.Blog;
import java.util.List;

import com.gabe.blog.po.Type;
import com.gabe.blog.po.User;
import com.gabe.blog.util.MarkdownUtils;
import com.gabe.blog.util.MyBeanUtils;

import com.gabe.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;

@Service

public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null) {
            throw new NotFoundException("This blog is unavailable");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdown2Html(content));

        blogRepository.upViews(id);
        return b;
    }

    @Override
    public Page<Blog> ListBlog(Pageable pageable) {

        return blogRepository.findAll(pageable);
    }



    //分登入是哪個user的
    public Page<Blog> ListBlogByUser(Pageable pageable, BlogQuery blog, User user) {
        return blogRepository.findAll(new Specification<Blog>() {
            //root表你要查詢的對象，將之映射成Root，從這個可獲成表的一些字段、屬性值
            //CriteriaQuery 是可以放查詢條件的容器
            //criterBuilder 是設置具體某個條件的表達式 ex 相等、like
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //組合條件放此list
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getKey_word()) && blog.getKey_word() != null) {
                    predicates.add(cb.or(cb.like(root.<String>get("title"), "%" + blog.getKey_word() + "%"), cb.like(root.<String>get("content"), "%" + blog.getKey_word() + "%")));
                }
                // id不會有""
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                predicates.add(cb.equal(root.<User>get("user").get("id"), user.getId()));

                //傳的參數是條件的數組
                //他會根據我們給的條件完成自動拼接查詢的sql語句
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);

    }
    //可查關鍵字的
    @Override
    public Page<Blog> ListBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            //root表你要查詢的對象，將之映射成Root，從這個可獲成表的一些字段、屬性值
            //CriteriaQuery 是可以放查詢條件的容器
            //criterBuilder 是設置具體某個條件的表達式 ex 相等、like
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //組合條件放此list
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getKey_word()) && blog.getKey_word() != null) {
                    predicates.add(cb.or(cb.like(root.<String>get("title"), "%" + blog.getKey_word() + "%"), cb.like(root.<String>get("content"), "%" + blog.getKey_word() + "%")));
                }
                // id不會有""
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }

                //傳的參數是條件的數組
                //他會根據我們給的條件完成自動拼接查詢的sql語句
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Page<Blog> ListBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        }, pageable);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Blog updataBlog(Long id, Blog blog) {
        Blog blog1 = blogRepository.findById(id).orElse(null);
        if (blog1 == null) {
            throw new NotFoundException("This blog is not found");
        }
        //过滤掉一部分属性名
        BeanUtils.copyProperties(blog, blog1, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(blog1);
    }
}
