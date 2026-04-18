package com.example.demo.dto;
import org.springframework.stereotype.Component;
import com.example.demo.Model.Product;

@Component
public class ProductBasicMapper {

    public ProductBasicDTO toDTO(Product product) {
        return new ProductBasicDTO(
            product.getId(),
            product.getNombre(),
            product.getPrecio()
        );
    }
}
