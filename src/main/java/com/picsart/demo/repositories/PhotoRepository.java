package com.picsart.demo.repositories;

import com.picsart.demo.models.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}