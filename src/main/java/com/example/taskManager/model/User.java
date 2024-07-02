package com.example.taskManager.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private java.util.Set<Role> roles = new HashSet<>();
    // Getters and setters

    //Id
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    //UserName
    public String getUsername(){return username;}
    public void setUsernname(String usernamer){this.username = usernamer;}

    //Password
    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    //Roles
    public Set<Role> getRoles(){return roles;}
    public void setRoles(java.util.Set<Role> roles ) {this.roles = roles;}
}
