package com.main.app.domain.model.variation;

import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.product.Product;
import com.main.app.domain.model.variation_attribute_value_id.VariationAttributeValue;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Variation extends AbstractEntity {

    @NotBlank
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double price;

    private boolean active;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "variation")
    private List<VariationAttributeValue> attributeValues;

    private String primaryImageUrl;



}