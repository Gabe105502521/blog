package com.gabe.blog.service;

import com.gabe.blog.po.Tag;
import com.gabe.blog.po.Type;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> ListTag(Pageable pageable);

    List<Tag> ListTag();

    List<Tag> ListTag(String ids);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
