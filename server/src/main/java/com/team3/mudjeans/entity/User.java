package com.team3.mudjeans.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@NamedQuery(name = "find_all_users", query = "select u from User u")
@NamedQuery(name = "find_user_by_email", query = "select u from User u where u.email = ?1")
@Table(name="`User`")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "permission_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Permission permission;

    public User() {}

    public User(String email, String password, Permission permission) {
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    public User(long id, String email, String password, Permission permission) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public boolean isAdmin() {
        return true;
    }

    public boolean validateEncodedPassword(String encodedPassword) {
        return password.equals(encodedPassword);
    }
}
