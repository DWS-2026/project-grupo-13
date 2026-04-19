package com.example.demo.Controller.rest;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ImageDTO;
import com.example.demo.dto.ImageMapper;
import com.example.demo.Model.Image;
import com.example.demo.Service.ImageService;

@RestController
@RequestMapping("api/images")
public class ImageRestController {
    
    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageMapper imageMapper;

    @GetMapping("/{id}")
    public ImageDTO getImage(@PathVariable long id) {
        return imageMapper.toDTO(imageService.getImage(id));
    }

    @GetMapping("/{id}/media")
    public ResponseEntity<byte[]> getImageMedia(@PathVariable long id) {

        Image image = imageService.getImage(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG) // o PNG si lo usas
                .body(image.getData());
    }


    @PutMapping("/{id}/media")
    public ResponseEntity<Void> replaceImage(
            @PathVariable long id,
            @RequestParam MultipartFile imageFile) throws IOException {

        imageService.replaceImage(id, imageFile.getBytes());
        return ResponseEntity.noContent().build();
    }


}
