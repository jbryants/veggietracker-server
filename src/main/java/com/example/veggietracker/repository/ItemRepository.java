package com.example.veggietracker.repository;

import java.util.List;

import com.example.veggietracker.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findByNameStartingWith(String searchTerm);
}
