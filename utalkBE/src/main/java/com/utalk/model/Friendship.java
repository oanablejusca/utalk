package com.utalk.model;

import java.util.Objects;

public class Friendship {

    public Friendship(){id=-1;}
    private Integer id;
    private Integer user_id1;
    private Integer user_id2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id1() {
        return user_id1;
    }

    public void setUser_id1(Integer user_id1) {
        this.user_id1 = user_id1;
    }

    public Integer getUser_id2() {
        return user_id2;
    }

    public void setUser_id2(Integer user_id2) {
        this.user_id2 = user_id2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return id.equals(that.id) &&
                Objects.equals(user_id1, that.user_id1) &&
                Objects.equals(user_id2, that.user_id2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id1, user_id2);
    }
}
