package com.example.demo.Controller.rest;

import java.net.URI;
import java.sql.SQLException;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.Service.ProductService;
import com.example.demo.Service.ImageService;
import com.example.demo.dto.ProductBasicDTO;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.ProductBasicMapper;
import com.example.demo.dto.ProductDetailMapper;

import com.example.demo.Model.Product;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    
    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductBasicMapper productBasicMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;


    //show all products in the DB
    @GetMapping("/")
    public List<ProductBasicDTO> getProducts() {
        return productBasicMapper.toDTOs(productService.findAll());
    }
    
    //show one detailed product
    @GetMapping("/{id}")
    public ProductDetailDTO getproduct(@PathVariable int id) {
        return productDetailMapper.toDTO(productService.findById(id));
    }

    //create a new product
    @PostMapping("/")
    public ResponseEntity<ProductDetailDTO> createProduct (@RequestBody ProductDetailDTO productDetailDTO) {

        Product product = productDetailMapper.toDomain(productDetailDTO);

        product = productService.createProduct(product);

        productDetailDTO = productDetailMapper.toDTO(product);

        URI location = fromCurrentRequest().path("/{id}").buildAndExpand(productDetailDTO.id()).toUri();

        return ResponseEntity.created(location).body(productDetailDTO);
    }

}
