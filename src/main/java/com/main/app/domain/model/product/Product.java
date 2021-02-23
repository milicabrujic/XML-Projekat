package com.main.app.domain.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.app.domain.model.AbstractEntity;
import com.main.app.domain.model.attribute.Attribute;
import com.main.app.domain.model.attribute_value.AttributeValue;
import com.main.app.domain.model.brand.Brand;
import com.main.app.domain.model.category.Category;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Where(clause = "deleted = false")
public class Product extends AbstractEntity {

    @NotBlank
    private String name;

    @JsonIgnore
    @ManyToOne
    private Category productCategory;

    @NotBlank
    private String description;

    @JsonIgnore
    @ManyToOne
    private Brand brand;

    private String primaryImageUrl;

    private Double price;

    private boolean active;

    private boolean newAdded;

    private boolean onSale;

    @JsonIgnore
    @ManyToMany
    @Transient
    private Set<Attribute> attributes;

    @JsonIgnore
    @ManyToMany
    @Transient
    private Set<AttributeValue> attributeValues;

    private Long discount;


    private Long productPosition;

    private Long discountProductPosition;




}