package com.team3.mudjeans.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQuery(name = "find_all_permissions", query = "select p from Permission p")
public class Permission implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String permissionName;

    @OneToMany(mappedBy = "permission")
    private List<User> user;

    public Permission() {};

    public Permission(long id, String permissionName) {
        this.permissionName = permissionName;
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

