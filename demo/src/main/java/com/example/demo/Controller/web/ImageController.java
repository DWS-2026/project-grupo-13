package com.example.demo.Controller.web;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.Image;
import com.example.demo.Service.ImageService;


@RestController
public class ImageController {
    
    @Autowired
    private ImageService imageService;

    
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image img = imageService.findById(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(img.getData());
    }

}
