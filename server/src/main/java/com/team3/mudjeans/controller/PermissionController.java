package com.team3.mudjeans.controller;


import com.team3.mudjeans.exceptions.PreConditionFailedException;
import com.team3.mudjeans.exceptions.ResourceNotFoundException;
import com.team3.mudjeans.entity.Permission;
import com.team3.mudjeans.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PermissionController {

    private EntityRepository<Permission> repo;

    @Autowired
    public void setRepo(EntityRepository<Permission> permissionRepository) {
        this.repo = permissionRepository;
    }

    @GetMapping("/permissions")
    public List<Permission> getAllpermissions() {
        return repo.findAll();
    }

    @GetMapping("/permissions/{id}")
    public Permission findOrderById(@PathVariable long id) {
        Permission o = repo.findById(id);
        if (o == null) {
            throw new ResourceNotFoundException();
        }
        return o;
    }

    @PostMapping("/permissions")
    public ResponseEntity<Permission> save(@RequestBody Permission permission) {
        Permission o = repo.save(permission);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getId()).toUri();
        return ResponseEntity.created(location).body(o);
    }

    @PutMapping("/permissions/{id}")
    public ResponseEntity<Permission> update(@PathVariable long id, @RequestBody Permission permission) {
        if (permission.getId() != id) {
            throw new PreConditionFailedException();
        }

        Permission previous = repo.findById(id);

        if (previous == null) {
            throw new ResourceNotFoundException();
        }

        Permission o = repo.save(permission);

        return ResponseEntity.ok(o);
    }

    @PostMapping("/permissions/{id}")
    public ResponseEntity<Permission> replace(@PathVariable long id, @RequestBody Permission permission) {
        if (permission.getId() != id) {
            throw new PreConditionFailedException();
        }

        Permission previous = repo.findById(id);

        if (previous == null) {
            throw new ResourceNotFoundException();
        }

        Permission o = repo.save(permission);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getId()).toUri();
        return ResponseEntity.created(location).body(o);
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        try {
            Permission o = repo.findById(id);

            if (o == null) {
                throw new ResourceNotFoundException();
            }

            return ResponseEntity.ok(true);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
