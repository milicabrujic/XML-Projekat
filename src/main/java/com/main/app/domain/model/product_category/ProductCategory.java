package com.main.app.domain.model.product_category;
import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.category.Category;
import com.main.app.domain.model.product.Product;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Embeddable
@Where(clause = "deleted = false")
@Table(name = "product_categories")
public class ProductCategory extends AbstractEntity {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Category category;

}

