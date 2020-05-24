package com.utalk.model;

public class User {

    private String username;
    private String password;
    private Integer profile_id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object instanceof User) {
            User user= (User) object;
            return ModelUtils.checkIfNullEquals(this.getUsername(), user.getUsername()) &&
                    ModelUtils.checkIfNullEquals(this.getPassword(), user.getPassword()) &&
                    ModelUtils.checkIfNullEquals(this.getProfile_id(), user.getProfile_id());
        }
        return false;
    }
}