package com.example.veggietracker.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    @NaturalId // Natural ids represent domain model unique identifiers that have a meaning in
    // the real world too
    // Hibernate provides a dedicated, efficient API for loading an entity by its
    // natural id
    // much like it offers for loading by its identifier (PK).
    @NotBlank
    private String name;

    @Column(length = 100)
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(length = 255, nullable = false)
    private Category category;

    @Column(length = 4, scale = 3, nullable = false, columnDefinition = "Decimal(4,3) default '0.250'")
    private Quantity defaultQuantity;

    @Enumerated(EnumType.STRING)
    @Column(length = 255, nullable = false, columnDefinition = "varchar(255) default 'kg'")
    private Unit defaultUnit;

    public Item(Long id, @NotBlank String name, String image, Category category, Quantity defaultQuantity,
                Unit defaultUnit) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.defaultQuantity = defaultQuantity;
        this.defaultUnit = defaultUnit;
    }

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Quantity getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(Quantity defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public Unit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(Unit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    @Override
    public String toString() {
        return "Item [id=" + id + ", name=" + name + ", image=" + image + ", category=" + category
                + ", defaultQuantity=" + defaultQuantity + ", defaultUnit=" + defaultUnit + "]";
    }
}

