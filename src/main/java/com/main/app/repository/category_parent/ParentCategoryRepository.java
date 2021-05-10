package com.main.app.repository.category_parent;

import com.main.app.domain.model.category_parent.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentCategoryRepository extends JpaRepository<ParentCategory, Long> {

    List<ParentCategory> findAllByCategoryId(Long category_id);

    List<ParentCategory> findAllByParentCategoryId(Long parent_category_id);

    List<ParentCategory> findAllByCategoryName(String category_name);

    List<ParentCategory> findAllByParentCategoryName(String parent_category_name);

}
