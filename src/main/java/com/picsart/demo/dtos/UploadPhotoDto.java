package com.picsart.demo.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UploadPhotoDto {
    public final String description;
    public final String uploaderId;

    @JsonCreator
    public UploadPhotoDto(@JsonProperty("description") String description,
                          @JsonProperty("uploaderId") String uploaderId) {
        this.description = description;
        this.uploaderId = uploaderId;
    }
}
