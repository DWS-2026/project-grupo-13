package com.example.demo.dto.mapper;

import com.example.demo.Model.Document;
import com.example.demo.dto.DocumentDTO;

import org.mapstruct.Mapper;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    List<DocumentDTO> toDTOs(Collection<Document> documents);

    DocumentDTO toDTO(Document document);
   
}

