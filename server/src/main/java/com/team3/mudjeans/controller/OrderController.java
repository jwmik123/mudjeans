package com.team3.mudjeans.controller;


import com.team3.mudjeans.exceptions.PreConditionFailedException;
import com.team3.mudjeans.exceptions.ResourceNotFoundException;
import com.team3.mudjeans.entity.JeanOrder;
import com.team3.mudjeans.repository.EntityRepository;
import com.team3.mudjeans.services.ExcelService;
import com.team3.mudjeans.services.StorageService;
import com.team3.mudjeans.entity.Jean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    private EntityRepository<JeanOrder> repo;
    private EntityRepository<Jean> jeanRepo;

    @Autowired
    public void setRepo(EntityRepository<JeanOrder> orderRepository) {
        this.repo = orderRepository;
    }

    @Autowired
    public void setJeanRepo(EntityRepository<Jean> jeanRepo) {
        this.jeanRepo = jeanRepo;
    }

    private ExcelService excel;

    @Autowired
    public void setExcel(ExcelService excel) {
        this.excel = excel;
    }

    private StorageService storage;

    @Autowired
    public void setStorage(StorageService storage) {
        this.storage = storage;
    }

    @GetMapping("/orders")
    public List<JeanOrder> getAllOrders() {
        return repo.findAll();
    }

    @GetMapping("/orders/{id}")
    public JeanOrder findOrderById(@PathVariable long id) {
        JeanOrder o = repo.findById(id);
        if (o == null) {
            throw new ResourceNotFoundException();
        }
        return o;
    }

    @PostMapping("/orders")
    public ResponseEntity<JeanOrder> save(@RequestBody JeanOrder jeanOrder) {
        JeanOrder o = repo.save(jeanOrder);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getOrderNumber()).toUri();
        return ResponseEntity.created(location).body(o);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<JeanOrder> update(@PathVariable long id, @RequestBody JeanOrder jeanOrder) {
        if (jeanOrder.getOrderNumber() != id) {
            throw new PreConditionFailedException();
        }

        JeanOrder previous = repo.findById(id);

        if (previous == null) {
            throw new ResourceNotFoundException();
        }

        JeanOrder o = repo.save(jeanOrder);

        return ResponseEntity.ok(o);
    }

    @PostMapping("/orders/{id}")
    public ResponseEntity<JeanOrder> replace(@PathVariable long id, @RequestBody JeanOrder jeanOrder) {
        if (jeanOrder.getOrderNumber() != id) {
            throw new PreConditionFailedException();
        }

        JeanOrder previous = repo.findById(id);

        if (previous == null) {
            throw new ResourceNotFoundException();
        }

        JeanOrder o = repo.save(jeanOrder);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(o.getOrderNumber()).toUri();
        return ResponseEntity.created(location).body(o);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable long id) {
        try {
            JeanOrder o = repo.findById(id);

            if (o == null) {
                throw new ResourceNotFoundException();
            }

            return ResponseEntity.ok(true);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/orders/upload")
    public ResponseEntity<Map<String, List<Jean>>> upload(@RequestParam("file") MultipartFile file) {
        String filename = storage.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(filename)
                .toUriString();

        Map<String, List<Jean>> map = excel.handleFileData(storage.loadFile(filename));

        map.values().forEach(e -> e.forEach(i -> jeanRepo.save(i)));

        return ResponseEntity.ok(map);
    }

    @GetMapping("/downloadFile/{filename}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {

        Resource resource = storage.downloadFile(filename);

        try {
            return ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (IOException e) {
            throw new ResourceNotFoundException("Resource not found!", e);
        }
    }
}
