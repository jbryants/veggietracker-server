package com.example.veggietracker.controller;

import java.util.List;

import com.example.veggietracker.model.Item;
import com.example.veggietracker.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public List<Item> getAllItems() {
        // get all items
        return itemService.getAllItems();
    }

    @GetMapping
    public List<Item> getItemsByQuery(@RequestParam("search") String searchTerm) {
        // Get all items by query
        return itemService.getItemsByQuery(searchTerm);
    }
}
