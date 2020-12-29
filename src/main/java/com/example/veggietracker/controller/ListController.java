package com.example.veggietracker.controller;

import com.example.veggietracker.dto.ListCreateRequest;
import com.example.veggietracker.model.List;
import com.example.veggietracker.service.ListService;
import com.example.veggietracker.view.ListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("api/v1/lists")
public class ListController {

    private final ListService listService;

    @Autowired
    public ListController(ListService listService) {
        this.listService = listService;
    }

    @JsonView(ListView.BrowserClient.class)
    @GetMapping
    public java.util.List<List> getLists() {
        // get all lists
        return listService.getLists();
    }

    @JsonView(ListView.BrowserClient.class)
    @PostMapping
    public ResponseEntity<List> createList(@RequestBody() ListCreateRequest listReq) {
        // create a new list
        List savedList = listService.createList(listReq);
        return new ResponseEntity<>(savedList, HttpStatus.CREATED);
    }

    @JsonView(ListView.BrowserClient.class)
    @GetMapping("/{id}")
    public ResponseEntity<List> getList(@PathVariable("id") Long id) {
        // get single list details
        return listService.getList(id);
    }

    // delete a list
    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Long id) {
        listService.deleteList(id);
    }
}
