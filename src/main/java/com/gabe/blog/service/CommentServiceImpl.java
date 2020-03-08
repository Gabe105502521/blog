package com.gabe.blog.service;

import com.gabe.blog.dao.CommentRepository;
import com.gabe.blog.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> listCommentByBlogId(Long id) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(id, sort);
        return eachComment(comments);
    }

    /**
     * 循環每個頂級的評論節點
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        //不直接更動資料庫裡的資料
        for(Comment comment: comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private List<Comment> tempReplys = new ArrayList<>();

    private void combineChildren(List<Comment> comments) {
        for (Comment comment: comments) {
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply : replys) {
                //循環跌代，找出子代存放在temp裡
                recursively(reply);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if(comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for(Comment reply: replys) {
                tempReplys.add(reply);
                if(reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
