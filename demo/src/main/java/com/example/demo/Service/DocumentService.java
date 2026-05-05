package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.DocumentRepository;
import com.example.demo.Model.Document;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class DocumentService {

    private final Path root = Paths.get("uploads");

    public DocumentService() throws IOException {
        Files.createDirectories(root);
    }

    public String saveFile(MultipartFile file, Long documentId) throws IOException {

        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));

        String storedName = "manual_" + documentId + extension;

        Path destination = root.resolve(storedName);

        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return destination.toString();
    }

    public Resource loadFile(String path) throws IOException {
        Path file = Paths.get(path);
        return new UrlResource(file.toUri());
    }
}

