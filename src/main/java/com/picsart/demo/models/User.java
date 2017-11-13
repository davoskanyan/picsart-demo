package com.picsart.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private List<String> photoIds;

    public User(String name,
                String email,
                String password,
                List<String> photoIds) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.photoIds = photoIds;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getPhotoIds() {
        return photoIds;
    }

    public void addPhotoId(String photoId) {
        this.photoIds.add(photoId);
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%s, name='%s', email='%s', password='%s']",
                id, name, email, password);
    }
}
