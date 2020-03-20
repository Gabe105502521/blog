package com.gabe.blog.vo;

public class BlogQuery {
    private String key_word;
    private Long TypeId;

    public BlogQuery() {
    }

    public String getKey_word() {
        return key_word;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public Long getTypeId() {
        return TypeId;
    }

    public void setTypeId(Long typeId) {
        TypeId = typeId;
    }
}
