package com.utalk.controller;

import com.utalk.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import com.utalk.services.PostsService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "posts")
public class PostController {

    @Autowired
    private PostsService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getPosts() {
        return postService.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post getPostById(@PathVariable(name = "id") Integer id) {
        return postService.getById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Post createPost(@Valid @RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Post updatePost(@PathVariable("id") Integer id, @Valid @RequestBody Post post) {
        if (!id.equals(post.getId())) {
            System.out.println("Path variable 'id' differs from id found in given JSON");
        }
        return postService.update(post);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePost(@PathVariable("id") Integer id) {
        postService.delete(id);
    }


}
