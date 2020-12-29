package com.example.veggietracker.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import com.example.veggietracker.view.ListView;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "listId", "itemId" }) })
@JsonView(ListView.BrowserClient.class)
public class ListItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "listId")
    @JsonIdentityReference(alwaysAsId = true)
    private List listId;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(name = "itemId", foreignKey = @ForeignKey)
    @JsonIdentityReference(alwaysAsId = true)
    private Item itemId;

    @Column(length = 6, scale = 3, nullable = false, columnDefinition = "Decimal(6,3) default '0.000' CONSTRAINT quantity_gte_0 CHECK (total_quantity >= 0)")
    @DecimalMin("0.000")
    private BigDecimal totalQuantity;

    @Enumerated(EnumType.STRING)
    @Column(length = 255, nullable = false, columnDefinition = "varchar(255) default 'kg'")
    private Unit unit;

    @Column(length = 4, scale = 3, nullable = false, columnDefinition = "Decimal(4,3) default '0.25'")
    private Quantity baseQuantity;

    @Enumerated(EnumType.STRING)
    @Column(length = 255, nullable = false, columnDefinition = "varchar(255) default 'kg'")
    private Unit baseUnit;

    @Column(length = 7, scale = 2, nullable = false, columnDefinition = "Decimal(7,2) default '0.00' CONSTRAINT price_gte_0 CHECK (base_price >= 0)" // db
            // level
            // constraint
            // check
    )
    @DecimalMin("0.00") // app level constraint check
    private BigDecimal basePrice;

    @Transient
    private String name;

    @Transient // don't persist to DB, just for the JSON view
    private BigDecimal totalPrice;

    public ListItem(Long id, List listId, Item itemId, @DecimalMin("0.000") BigDecimal totalQuantity,
                    @NotBlank Unit unit, @NotBlank Quantity baseQuantity, @NotBlank Unit baseUnit,
                    @DecimalMin("0.00") BigDecimal basePrice) {
        super();
        this.id = id;
        this.listId = listId;
        this.itemId = itemId;
        this.totalQuantity = totalQuantity;
        this.unit = unit;
        this.baseQuantity = baseQuantity;
        this.baseUnit = baseUnit;
        this.basePrice = basePrice;
    }

    public ListItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List getListId() {
        return listId;
    }

    public void setListId(List listId) {
        this.listId = listId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Quantity getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(Quantity baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public Unit getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(Unit baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getName() {
        return name;
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    private void updateTotalPrice() {
        this.totalPrice = this.totalQuantity.multiply(this.basePrice).divide(this.baseQuantity.getQuantity())
                .setScale(2);
        if (this.name == null) {
            this.name = this.getItemId().getName();
        }
    }
}

