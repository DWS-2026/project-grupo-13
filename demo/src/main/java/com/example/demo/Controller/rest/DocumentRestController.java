package com.example.demo.Controller.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.example.demo.Service.DocumentService;
import com.example.demo.Service.UserService;

import com.example.demo.Model.Document;



@RestController
@RequestMapping("/api/v1/documents")
public class DocumentRestController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;


    @PostMapping("/users/{id}/dni")
    public ResponseEntity<?> uploadUserDni(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws IOException {

        try {
            ResponseEntity<?> response = userService.addDniToUser(id, file);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/users/{id}/dni")
    public ResponseEntity<Resource> downloadDni(@PathVariable long id) throws IOException {

        Document doc = userService.getDocumentIfAuthorised(id);

        Resource file = documentService.loadFile(doc.getFilePath());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + doc.getOriginalName() + "\"")
                .body(file);
    }

}
