package com.picsart.demo.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PhotoCommentDto {
    public final String comment;
    public final String userId;

    @JsonCreator
    public PhotoCommentDto(@JsonProperty("comment") String comment,
                           @JsonProperty("userId") String userId) {
        this.comment = comment;
        this.userId = userId;
    }
}
