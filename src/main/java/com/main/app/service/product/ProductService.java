package com.main.app.service.product;

import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.product.ProductAttributeAttrValueDTO;
import com.main.app.domain.model.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

//    Product changeSlugForProductId(Long id,String slug);

    String buildSlug(String title,int numberOfRepeat);

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, Long productType, Pageable pageable);

    Product getOne(Long id);

    Product save(Product product);

    Product edit(Product product, Long id);

    Product delete(Long id);

    void uploadImage(Long id, MultipartFile[] images) throws IOException;

    Product toggleActivate(Long id);

    void checkIfHasForeignKey(Long id, String entity);

    List<ProductAttributeAttrValueDTO> getAllAttributeValuesForProductId(Long productId);
}
