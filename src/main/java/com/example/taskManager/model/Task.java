package com.example.taskManager.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
@Id
@GeneratedValue(strategy =  GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String title;

private String description;

private LocalDate duDate;
private boolean completed;

@ManyToOne
@JoinColumn(name = "user_id", nullable = false)
private User user;

// Getters and Setters 

//ID
public Long getId(){return id;}
public void setId(Long id){this.id= id;}

//Title
public String getTitle(){return title;}
public void setTitle(String title){this.title = title;}

//Description
public String getDescription(){return description;}
public void setDescripttion(String description){ this.description = description;}

//DuDate
public LocalDate getDuDate(){return duDate;}
public void setDuDate(LocalDate duDate){this.duDate = duDate;}

//Users
public User getUser(){return user;}
public void setUser(User user){this.user = user;}

public boolean isCompleted() {
    return completed;
}
public void setCompleted(boolean completed) {
    this.completed = completed;
}
}