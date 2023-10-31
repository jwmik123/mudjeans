package com.team3.mudjeans.controller;

import com.team3.mudjeans.entity.Jean;
import com.team3.mudjeans.entity.JeanOrder;
import com.team3.mudjeans.repository.EntityRepository;
import com.team3.mudjeans.repository.JPAJeanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class JeanController {
    private EntityRepository<Jean> jeanRepo;

    @Autowired
    public void setJeanRepo(EntityRepository<Jean> jeanRepo) {
        this.jeanRepo = jeanRepo;
    }

    @GetMapping("/jean")
    public ResponseEntity<Map<String, List<Jean>>> getAll() {
        List<Jean> jeans = jeanRepo.findAll();
        Map<String, List<Jean>> map = jeans.stream().collect(Collectors.groupingBy(Jean::getDescription));
        return ResponseEntity.ok(map);
    }

    @GetMapping("/jeans")
    public ResponseEntity<List<Jean>> getAllAsList() {
        return ResponseEntity.ok(jeanRepo.findAll());
    }
}
