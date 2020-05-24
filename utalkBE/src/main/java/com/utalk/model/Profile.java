package com.utalk.model;


import java.time.LocalDate;

public class Profile {

    public Profile(){
        id=-1;
    }

    private Integer id;
    private String name;
    private String photo;
    private String occupation;
    private String location;
    private LocalDate birthdate;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object instanceof Profile) {
            Profile profile = (Profile) object;
            return ModelUtils.checkIfNullEquals(this.getId(), profile.getId()) &&
                    ModelUtils.checkIfNullEquals(this.getPhoto(), profile.getPhoto()) &&
                    ModelUtils.checkIfNullEquals(this.getOccupation(), profile.getOccupation()) &&
                    ModelUtils.checkIfNullEquals(this.getLocation(), profile.getLocation()) &&
                    ModelUtils.checkIfNullEquals(this.getBirthdate(), profile.getBirthdate());
        }
        return false;
    }
}
