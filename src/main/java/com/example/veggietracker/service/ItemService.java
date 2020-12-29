package com.example.veggietracker.service;

import java.util.List;

import com.example.veggietracker.model.Item;
import com.example.veggietracker.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Item> getItemsByQuery(String searchTerm) {
        // Get all items by query
        return itemRepository.findByNameStartingWith(searchTerm);
    }
}
