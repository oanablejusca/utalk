package com.utalk.controller;

import com.utalk.model.Friendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import com.utalk.services.FriendshipService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "friendship")
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Friendship> getFriendships() {
        return friendshipService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Friendship getFriendshipById(@PathVariable(name = "id") Integer id) {
        return friendshipService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Friendship createFriendship(@Valid @RequestBody Friendship friendship) {
        return friendshipService.create(friendship);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Friendship updateFriendship(@PathVariable("id") Integer id, @Valid @RequestBody Friendship friendship) {
        if (!id.equals(friendship.getId())) {
            System.out.println("Path variable 'id' differs from id found in given JSON");
        }
        return friendshipService.update(friendship);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteFriendship(@PathVariable("id") Integer id) {
        friendshipService.delete(id);
    }


}
