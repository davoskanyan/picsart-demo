package com.picsart.demo.models;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Photo {
    @Id
    private String id;

    private String description;
    private List<Comment> comments;
    private String gridFsId;
    private String userId;

    public Photo(String description, List<Comment> comments, String gridFsId, String userId) {
        this.description = description;
        this.comments = comments;
        this.gridFsId = gridFsId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public String getGridFsId() {
        return gridFsId;
    }

    public String getUserId() {
        return userId;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
