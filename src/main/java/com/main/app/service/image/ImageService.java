package com.main.app.service.image;

import com.main.app.domain.model.image.Image;

import java.util.List;


public interface ImageService {

    List<Image> getAllImagesByProductId(Long productId);


    Image setPrimary(Long id, Long productId);


    Image delete(Long id, Long productId);



}
