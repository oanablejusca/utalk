package com.utalk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import com.utalk.model.Profile;
import com.utalk.services.ProfileService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Profile> getProfiles() {
        return profileService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile getProfileById(@PathVariable(name = "id") Integer id) {
        return profileService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Profile createProfile(@Valid @RequestBody Profile profile) {
        return profileService.create(profile);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Profile updateProfile(@PathVariable("id") Integer id, @Valid @RequestBody Profile profile) {
        if (!id.equals(profile.getId())) {
            System.out.println("Path variable 'id' differs from id found in given JSON");
        }
        return profileService.update(profile);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProfile(@PathVariable("id") Integer id) {
        profileService.delete(id);
    }

}

