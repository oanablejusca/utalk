package com.utalk.model;

import java.time.LocalDate;

public class Post {
    public Post() {
        id = -1;
    }

    private Integer id;
    private Integer profile_id;
    private String content;
    private LocalDate posted_on;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPosted_on() {
        return posted_on;
    }

    public void setPosted_on(LocalDate posted_on) {
        this.posted_on = posted_on;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Post) {
            Post post = (Post) object;
            return
                    ModelUtils.checkIfNullEquals(this.getId(), post.getProfile_id()) &&
                            ModelUtils.checkIfNullEquals(this.getProfile_id(), post.getProfile_id()) &&
                            ModelUtils.checkIfNullEquals(this.getPosted_on(), post.getPosted_on()) &&
                            ModelUtils.checkIfNullEquals(this.getContent(), post.getContent());
        }
        return false;
    }
}
