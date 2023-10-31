package com.team3.mudjeans.controller;


import com.team3.mudjeans.exceptions.PreConditionFailedException;
import com.team3.mudjeans.exceptions.ResourceNotFoundException;
import com.team3.mudjeans.entity.User;
import com.team3.mudjeans.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private EntityRepository<User> repo;

    @Autowired
    public void setRepo(EntityRepository<User> userRepository) {
        this.repo = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllusers() {
        return repo.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable long id) {
        User o = repo.findById(id);
        if (o == null) {
            throw new ResourceNotFoundException();
        }
        return o;
    }

    @PostMapping("/users")
    public ResponseEntity<User> save(@RequestBody User user) {
        User o = repo.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getId()).toUri();
        return ResponseEntity.created(location).body(o);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> update(@PathVariable long id, @RequestBody User user) {
        if (user.getId() != id) {
            throw new PreConditionFailedException();
        }

        User previous = repo.findById(id);

        if (previous == null) {
            throw new ResourceNotFoundException();
        }

        User o = repo.save(user);

        return ResponseEntity.ok(o);
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<User> replace(@PathVariable long id, @RequestBody User user) {
        if (user.getId() != id) {
            throw new PreConditionFailedException();
        }

        User previous = repo.findById(id);

        if (previous == null) {
            throw new ResourceNotFoundException();
        }

        User o = repo.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getId()).toUri();
        return ResponseEntity.created(location).body(o);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        try {
            User o = repo.findById(id);

            if (o == null) {
                throw new ResourceNotFoundException();
            }

            return ResponseEntity.ok(true);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
