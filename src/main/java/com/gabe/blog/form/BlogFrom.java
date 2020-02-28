package com.gabe.blog.form;

import com.gabe.blog.po.Tag;
import com.gabe.blog.po.Type;

import java.util.ArrayList;
import java.util.List;

public class BlogFrom {

    private String title;
    private String firstPicture;
    private String content;
    private boolean published;
    private Long typeId;
    private String tagIds;

    public BlogFrom() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
