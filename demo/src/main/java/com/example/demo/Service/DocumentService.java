package com.example.demo.Service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
        if (file.isEmpty()) {
            throw new IOException("El archivo no puede estar vacío");
        }

        
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());

        if (originalName.contains("..")) {
            throw new IOException("Nombre de archivo no válido");
        }

        
        String extension = "";
        if (originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        } else {
            extension = ".bin"; 
        }

        String storedName = "manual_" + documentId + extension;
        Path destination = root.resolve(storedName);

       
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return destination.toString();
    }

    public Resource loadFile(String path) throws IOException {
        Path file = Paths.get(path);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException("No se pudo leer el archivo: " + path);
        }
    }
}