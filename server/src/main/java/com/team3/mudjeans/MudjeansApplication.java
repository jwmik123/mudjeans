package com.team3.mudjeans;

import com.team3.mudjeans.entity.Jean;
import com.team3.mudjeans.entity.Permission;
import com.team3.mudjeans.repository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

@SpringBootApplication
public class MudjeansApplication implements CommandLineRunner {

    private EntityRepository<Permission> permissionRepo;

    @Autowired
    public void setPermissionRepo(EntityRepository<Permission> permissionRepo) {
        this.permissionRepo = permissionRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(MudjeansApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running commandline startup");
//        addPermissions();
    }

//    private void addPermissions() {
//        Permission p = new Permission();
//        p.setPermissionName("default");
//        permissionRepo.save(p);
//
//        p = new Permission();
//        p.setPermissionName("admin");
//        permissionRepo.save(p);
//    }
}
