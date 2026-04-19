package com.example.demo.Service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Image;
import com.example.demo.Repository.ImageRepository;

import org.springframework.web.multipart.MultipartFile;


@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    
    public Image createImage(InputStream inputStream) throws IOException {
        byte[] bytes = inputStream.readAllBytes();
        Image image = new Image(bytes);
        return imageRepository.save(image);
    }

    
    public Image createImage(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Image image = new Image(bytes);
        return imageRepository.save(image);
    }

    
    public Image findById(long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    //FOR THE API REST
    
    public Image getImage(long id) {
        return imageRepository.findById(id).orElseThrow();
    }

    public Image createImage(byte[] data) {
        Image image = new Image(data);
        return imageRepository.save(image);
    }

    public Image replaceImage(long id, byte[] data) {
        Image image = imageRepository.findById(id).orElseThrow();
        image.setData(data);
        return imageRepository.save(image);
    }

}


