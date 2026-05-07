package com.example.demo.Controller.rest;

import com.example.demo.Repository.ProductRepository;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.NoSuchElementException;

import com.example.demo.Service.ProductService;
import com.example.demo.Service.ImageService;
import com.example.demo.dto.ProductBasicDTO;
import com.example.demo.dto.ProductDetailDTO;
import com.example.demo.dto.ProductBasicMapper;
import com.example.demo.dto.ProductDetailMapper;

import com.example.demo.Model.Product;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

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
    public Page<ProductBasicDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productBasicMapper::toDTO);
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

    //delete a product
    @DeleteMapping("/{id}")
    public ProductDetailDTO deleteProduct(@PathVariable int id) {

        Product product = productService.findById(id);

        productService.deleteById(id);

        return productDetailMapper.toDTO(product);

    }

    //replace a product
    @PutMapping("/{id}")
    public ProductDetailDTO replaceProduct(@PathVariable int id, @RequestBody ProductDetailDTO newProductDTO) {

        if (productService.existsById(id)) {

            Product newProduct = productDetailMapper.toDomain(newProductDTO);

            newProduct.setId(id);
            productService.save(newProduct);

            return productDetailMapper.toDTO(newProduct);

        } else {
            throw new NoSuchElementException();
        }

    }

    @PostMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<?> assignCategoryToProduct(
            @PathVariable int productId,
            @PathVariable long categoryId) {

        productService.assignCategory(productId, categoryId);
        return ResponseEntity.ok().build();
    }



    //the product is created but the category is set to null
    /*
        {
    "id": 11,
    "nombre": "Prueba",
    "precio": 100,
    "descripcion": "prueba prueba",
    "category":{
        "name": "Smartphones"
    },
    "image": null,
    "reviews": null
    }

    to add a category to the created product use the method assign category to product with the category ID and the new product ID

    
    */
}
