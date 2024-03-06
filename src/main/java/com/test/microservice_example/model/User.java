package com.test.microservice_example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String id1;
    private String id2;
    private String id3;

    // Add getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId1() {
        return id1;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getId2() {
        return id2;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    public String getId3() {
        return id3;
    }

}
