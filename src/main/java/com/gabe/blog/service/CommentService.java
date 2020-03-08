package com.gabe.blog.service;

import com.gabe.blog.po.Comment;
import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);
    Comment saveComment(Comment comment);
}
