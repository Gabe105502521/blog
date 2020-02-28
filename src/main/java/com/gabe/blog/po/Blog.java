package com.gabe.blog.po;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.List;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity //這樣這個類才有具備和數據庫對應生成的能力
@Table(name = "t_blog")  //不這樣指定他就會生成叫Blog的table
public class Blog {

    @Id
    @GeneratedValue  //mysql自動生成
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private Integer views;
    //private boolean shareStatement;
    //private boolean commentable;
    private boolean published;
    //private boolean recommend;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*JPA使用@OneToMany和@ManyToOne来标识一对多的双向关联。一端(type)使用@OneToMany,多端(blog)使用@ManyToOne。
    在JPA规范中，一对多的双向关系由多端(blog)来维护。就是说多端(blog)为关系维护端，负责关系的增删改查。一端(type)则为关系被维护端，不能维护关系。
    一端(type)使用@OneToMany注释的mappedBy="blog"属性表明Author是关系被维护端。*/
    //如果沒設置這個，就會對type一無所知
    @ManyToOne
    private Type type;

    @ManyToMany
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private User user = new User();

    @OneToMany(mappedBy = "blog")
    private List<Comment> comment = new ArrayList<>();


    @Transient
    private Long typeId;

    @Transient
    private String tagIds;

    public Blog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
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


    public void init() {
        this.tagIds = tagsToIds(this.getTags());
        //this.tagIds = this.getTags();
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", views=" + views +
                ", published=" + published +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comment=" + comment +
                '}';
    }
}
