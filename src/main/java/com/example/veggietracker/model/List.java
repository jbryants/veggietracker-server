package com.example.veggietracker.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.example.veggietracker.view.ListView;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class List {
    @JsonView(ListView.BrowserClient.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIdentityReference(alwaysAsId = true)
    private Long id;

    @JsonView(ListView.BrowserClient.class)
    @Column(length = 255, nullable = false)
    @NotBlank
    private String name;

    @JsonView(ListView.BrowserClient.class)
    @Column(length = 255, nullable = false)
    private String shop;

    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    @JoinColumn(foreignKey = @ForeignKey(name = "owner_id"))
    private User owner;

    @JsonView(ListView.BrowserClient.class)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "listId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ListItem> listItems;

    @Column(nullable = false)
    private Date dateCreated;

    @Column(nullable = false)
    private Date dateUpdated;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = new Date();
        this.dateUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = new Date();
    }

    public List(Long id, @NotBlank String name, String shop, User owner, Set<ListItem> listItems, Date dateCreated,
                Date dateUpdated) {
        this.id = id;
        this.name = name;
        this.shop = shop;
        this.owner = owner;
        this.listItems = listItems;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
    }

    public List() {
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

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<ListItem> getListItems() {
        return listItems;
    }

    public void setListItems(Set<ListItem> listItems) {
        this.listItems = listItems;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
