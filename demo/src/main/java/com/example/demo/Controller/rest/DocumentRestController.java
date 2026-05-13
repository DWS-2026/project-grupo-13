package com.example.demo.Controller.rest;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.example.demo.dto.DocumentDTO;
import com.example.demo.dto.mapper.DocumentMapper;
import com.example.demo.Repository.DocumentRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.DocumentService;
import com.example.demo.Service.UserService;

import com.example.demo.Model.User;
import com.example.demo.Model.Document;


// These methods 

@RestController
@RequestMapping("/api/v1/documents")
public class DocumentRestController {
    
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private UserService userService;


    //this method extracts the nickname used to login from the contextHolder and checks if the 
    //item trying to be modified is asociated with the logged user
    @PostMapping("/users/{id}/dni")
    public ResponseEntity<?> uploadUserDni(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException {

        try {
            ResponseEntity<?> response = userService.addDniToUser(id, file);
            redirectAttributes.addFlashAttribute("success", "DNI subido correctamente");
            return response; // ← usa la respuesta del service directamente
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al subir el DNI");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> downloadDni(@PathVariable long id) throws IOException {

        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));


        // Only the owner can download the file
        String nickname = SecurityContextHolder.getContext().getAuthentication().getName();
        User authenticatedUser = userRepository.findByNickname(nickname);

        if (doc.getUser().getId() != authenticatedUser.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Resource file = documentService.loadFile(doc.getFilePath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + doc.getOriginalName() + "\"")
                .body(file);
    }

}
