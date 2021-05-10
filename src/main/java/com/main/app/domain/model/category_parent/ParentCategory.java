package com.main.app.domain.model.category_parent;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.category.Category;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class ParentCategory extends AbstractEntity {

    @ManyToOne
    private Category parentCategory;

    @ManyToOne
    private  Category category;
}
