package com.example.veggietracker.repository;

import com.example.veggietracker.model.List;
import com.example.veggietracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRepository extends JpaRepository<List, Long> {
    public java.util.List<List> findByOwnerOrderByDateUpdatedDesc(User owner);
}