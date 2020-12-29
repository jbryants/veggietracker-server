package com.example.veggietracker.controller;

import javax.validation.Valid;

import com.example.veggietracker.dto.ListItemCreateRequest;
import com.example.veggietracker.dto.ListItemUpdateRequest;
import com.example.veggietracker.model.ListItem;
import com.example.veggietracker.service.ListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/listitems")
public class ListItemController {

    private final ListItemService listItemService;

    @Autowired
    public ListItemController(ListItemService listItemService) {
        this.listItemService = listItemService;
    }

//    @GetMapping
//    public java.util.List<ListItem> getAllLists() {
//        return listItemService.getAllLists();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ListItem> getListItem(@PathVariable("id") Long id) {
        return listItemService.getListItem(id);
    }

    @GetMapping
    public List<ListItem> getListItemsByList(@RequestParam("listId") Long id) {
        return listItemService.getListItemsByList(id);
    }

    @PostMapping
    public ResponseEntity<List<ListItem>> createNewList(@RequestBody() List<ListItemCreateRequest> listItemsRequest) {
        try {
            java.util.List<ListItem> listItems = listItemService.createNewList(listItemsRequest);
            return new ResponseEntity<>(listItems, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<ListItem> updateListItem(@PathVariable("id") long id,
                                                   @Valid @RequestBody ListItemUpdateRequest listItemUpdateDto) {
        try {
            ListItem updatedListItem = listItemService.updateListItem(id, listItemUpdateDto);
            return new ResponseEntity<>(updatedListItem, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteListItem(@PathVariable Long id) {
//        listItemService.deleteListItem(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @DeleteMapping
    public ResponseEntity<Object> deleteListItems(@RequestBody() List<Long> ids) {
        listItemService.deleteListItems(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
