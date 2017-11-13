package com.picsart.demo.controllers;

import com.picsart.demo.dtos.PhotoCommentDto;
import com.picsart.demo.models.Comment;
import com.picsart.demo.models.Photo;
import com.picsart.demo.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @RequestMapping(value = "/{photoId}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] photoById(@PathVariable String photoId) throws IOException {
        Photo photo = photoRepository.findOne(photoId);

        Query query = new Query(Criteria.where("_id").is(photo.getGridFsId()));
        return gridFsTemplate.findOne(query).getInputStream().readAllBytes();
    }

    @RequestMapping(value = "/{photoId}/comments", method = RequestMethod.GET)
    @ResponseBody
    public List<PhotoCommentDto> getComments(@PathVariable String photoId) {
        Photo photo = photoRepository.findOne(photoId);
        return photo.getComments().stream()
                .map(comment -> new PhotoCommentDto(comment.getText(), comment.getCommenterId()))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{photoId}/comments", method = RequestMethod.POST)
    public void addComment(@PathVariable String photoId, @RequestBody PhotoCommentDto commentDto) {
        Photo photo = photoRepository.findOne(photoId);

        photo.addComment(new Comment(commentDto.comment, commentDto.userId));
        photoRepository.save(photo);
    }
}
