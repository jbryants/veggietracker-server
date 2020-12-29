package com.example.veggietracker.repository;

import com.example.veggietracker.model.ListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListItemRepository extends JpaRepository<ListItem, Long> {
    List<ListItem> findAllByListId(com.example.veggietracker.model.List listId);

    @Modifying
    @Query("delete from ListItem l where l.id in ?1")
    void deleteListItemsWithIds(List<Long> ids);
}
