package com.picsart.demo.models;

public class Comment {
    private String text;
    private String commenterId;

    public Comment(String text, String commenterId) {
        this.text = text;
        this.commenterId = commenterId;
    }

    public String getText() {
        return text;
    }

    public String getCommenterId() {
        return commenterId;
    }
}
