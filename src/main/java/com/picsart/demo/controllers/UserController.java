package com.picsart.demo.controllers;

import com.picsart.demo.dtos.RegistrationDto;
import com.picsart.demo.dtos.UserDto;
import com.picsart.demo.models.Photo;
import com.picsart.demo.models.User;
import com.picsart.demo.repositories.PhotoRepository;
import com.picsart.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public boolean register(@RequestBody RegistrationDto registrationDto) {
        if (userRepository.findByEmail(registrationDto.email) != null) {
            return false;
        }
        userRepository.save(new User(registrationDto.name, registrationDto.email, registrationDto.password, new ArrayList<>()));
        return true;
    }

    @RequestMapping("/{userId}")
    @ResponseBody
    public UserDto byId(@PathVariable String userId) {
        User user = userRepository.findOne(userId);
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

    @RequestMapping("/{userId}/photos")
    @ResponseBody
    public List<String> photos(@PathVariable String userId) {
        return userRepository.findOne(userId).getPhotoIds();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestParam MultipartFile file, @RequestParam String description,
                          @RequestParam String uploaderId)
            throws IOException {

        String photoGridId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), "image/png")
                .getId().toString();

        Photo photo = new Photo(description, new ArrayList<>(), photoGridId, uploaderId);
        photoRepository.insert(photo);

        User currentUser = userRepository.findOne(uploaderId);
        currentUser.addPhotoId(photo.getId());
        userRepository.save(currentUser);
    }

    @RequestMapping("/topcommenters")
    @ResponseBody
    public List<String> topCommenters() {
        return mongoTemplate.aggregate(newAggregation(
                unwind("comments"),
                project("comments.commenterId"),
                Aggregation.group("commenterId").count().as("count"),
                sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(10)
        ), Photo.class, String.class).getMappedResults();
    }

    @RequestMapping("/topuploaders")
    @ResponseBody
    public List<String> topUploaders() {
        return mongoTemplate.aggregate(newAggregation(
                unwind("photoIds"),
                group("id").count().as("count"),
                sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(10)
        ), User.class, String.class).getMappedResults();
    }
}