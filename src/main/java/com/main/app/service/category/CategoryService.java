package com.main.app.service.category;


import com.main.app.domain.dto.Entities;
import com.main.app.domain.dto.category.CategoryDTO;
import com.main.app.domain.dto.category_parent.ParentCategoryDTO;
import com.main.app.domain.model.category.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {

    Entities getAll();

    Entities getAllBySearchParam(String searchParam, Pageable pageable);

    Category getOne(Long id);

    Category save(Category category,CategoryDTO categoryDTO);

    Category edit(Category category, CategoryDTO categoryDTO ,Long id);

    Category delete(Long id);

    void uploadImage(Long id, MultipartFile[] images) throws IOException;

    List<CategoryDTO> getAllWhereNameIsParentCategory(String name);

    Category findByCategoryName(String name);

   List<ParentCategoryDTO> getAllParentCategoriesForCategoryId(Long category_id);

   List<Long> getAllSubCategories(Long category_id);

}
