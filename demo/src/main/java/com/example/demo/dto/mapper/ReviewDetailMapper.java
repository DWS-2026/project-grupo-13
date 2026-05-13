package com.example.demo.dto.mapper;

import com.example.demo.Model.Review;
import com.example.demo.dto.ReviewDetailDTO;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ReviewDetailMapper {
    
    ReviewDetailDTO toDTO (Review review);

    List<ReviewDetailDTO> toDTOs (Collection<Review> reviews);

}
