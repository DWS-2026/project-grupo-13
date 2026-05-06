package com.example.demo.Controller.rest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.http.HttpHeaders;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.dto.DocumentMapper;
import com.example.demo.Repository.DocumentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.CategoryService;
import com.example.demo.Service.DocumentService;
import com.example.demo.Service.UserService;
import com.example.demo.Service.ImageService;
import com.example.demo.Service.ProductService;

import com.example.demo.Model.User;
import com.example.demo.Model.Document;


import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api/documents")
public class DocumentRestController {
    
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentMapper documentMapper;


    @PostMapping("/users/{id}/dni")
    public ResponseEntity<DocumentDTO> uploadUserDni(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Si ya tiene DNI, lo reemplazamos
        Document doc = user.getDni();
        if (doc == null) {
            doc = new Document();
            doc.setUser(user);
        }

        doc.setOriginalName(file.getOriginalFilename());
        Document saved = documentRepository.save(doc);

        String path = documentService.saveFile(file, saved.getId());
        saved.setFilePath(path);

        documentRepository.save(saved);

        user.setDni(saved);
        userRepository.save(user);

        return ResponseEntity.ok(documentMapper.toDTO(saved));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> downloadDni(@PathVariable long id) throws IOException {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        Resource file = documentService.loadFile(doc.getFilePath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + doc.getOriginalName() + "\"")
                .body(file);
    }

}
