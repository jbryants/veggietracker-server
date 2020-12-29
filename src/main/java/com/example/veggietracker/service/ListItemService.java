package com.example.veggietracker.service;

import java.util.ArrayList;
import java.util.Optional;

import com.example.veggietracker.dto.ListItemCreateRequest;
import com.example.veggietracker.dto.ListItemUpdateRequest;
import com.example.veggietracker.exceptions.VeggieTrackerException;
import com.example.veggietracker.model.Item;
import com.example.veggietracker.model.List;
import com.example.veggietracker.model.ListItem;
import com.example.veggietracker.repository.ItemRepository;
import com.example.veggietracker.repository.ListItemRepository;
import com.example.veggietracker.repository.ListRepository;
import com.example.veggietracker.utils.JsonNullableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class ListItemService {

    ItemRepository itemRepository;
    ListRepository listRepository;
    ListItemRepository listItemRepository;

    @Autowired
    ListItemService(ItemRepository itemRepository, ListRepository listRepository, ListItemRepository listItemRepository) {
        this.itemRepository = itemRepository;
        this.listRepository = listRepository;
        this.listItemRepository = listItemRepository;
    }

    public java.util.List<ListItem> getAllLists() {
        return listItemRepository.findAll();
    }

    public ResponseEntity<ListItem> getListItem(@PathVariable("id") Long id) {
        Optional<ListItem> optionalListItem = listItemRepository.findById(id);
        if (optionalListItem.isPresent()) {
            return new ResponseEntity<>(optionalListItem.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public java.util.List<ListItem> getListItemsByList(Long id) {
        Optional<List> optionalList = listRepository.findById(id);
        if (optionalList.isPresent()) {
            return listItemRepository.findAllByListId(optionalList.get());
        }
        return new ArrayList<>();
    }

    public java.util.List<ListItem> createNewList(java.util.List<ListItemCreateRequest> listItemsReq) {
        java.util.List<ListItem> listItemsToSave = new ArrayList<>();
        for (ListItemCreateRequest listItemReq : listItemsReq) {
            ListItem listItem = new ListItem();
            listItem.setBaseUnit(listItemReq.getBaseUnit());
            listItem.setBaseQuantity(listItemReq.getBaseQuantity());
            listItem.setBasePrice(listItemReq.getBasePrice());
            listItem.setUnit(listItemReq.getUnit());
            listItem.setTotalQuantity(listItemReq.getTotalQuantity());

            // find item using itemId then set it.
            Optional<Item> optionalItem = itemRepository.findById(listItemReq.getItemId());
            if (optionalItem.isPresent()) {
                listItem.setItemId(optionalItem.get());
            } else {
                throw new VeggieTrackerException("no item found for given id");
            }

            // find list using listId then set it.
            Optional<List> optionalList = listRepository.findById(listItemReq.getListId());
            if (optionalList.isPresent()) {
                listItem.setListId(optionalList.get());
            } else {
                throw new VeggieTrackerException("no list found for given id");
            }

            listItemsToSave.add(listItem);
        }

        return listItemRepository.saveAll(listItemsToSave);
    }

    public ListItem updateListItem(long id, ListItemUpdateRequest listItemUpdateDto) {
        Optional<ListItem> listItemOptional = listItemRepository.findById(id);
        if (!listItemOptional.isPresent()) {
            throw new VeggieTrackerException("no item found for given id");
        }

        ListItem listItem = listItemOptional.get();
        JsonNullableUtils.changeIfPresent(listItemUpdateDto.getBasePrice(), listItem::setBasePrice);
        JsonNullableUtils.changeIfPresent(listItemUpdateDto.getBaseQuantity(), listItem::setBaseQuantity);
        JsonNullableUtils.changeIfPresent(listItemUpdateDto.getTotalQuantity(), listItem::setTotalQuantity);

        return listItemRepository.save(listItem);
    }

    // single delete
//    public void deleteListItems(Long id) {
//        listItemRepository.deleteById(id);
//    }

    @Transactional
    public void deleteListItems(java.util.List<Long> ids) {
        // bulk delete
        listItemRepository.deleteListItemsWithIds(ids);
    }
}