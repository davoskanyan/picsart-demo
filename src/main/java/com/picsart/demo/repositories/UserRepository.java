package com.picsart.demo.repositories;

import com.picsart.demo.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
