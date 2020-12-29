package com.example.veggietracker.service;

import com.example.veggietracker.dto.ListCreateRequest;
import com.example.veggietracker.model.List;
import com.example.veggietracker.model.User;
import com.example.veggietracker.repository.ListRepository;
import com.example.veggietracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ListService {
    private final ListRepository listRepository;
    private final UserRepository userRepository;

    @Autowired
    public ListService(ListRepository listRepository, UserRepository userRepository) {
        this.listRepository = listRepository;
	this.userRepository = userRepository;
    }

    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    public java.util.List<List> getLists() {
        //return listRepository.findAll(Sort.by("dateUpdated").descending());
        User user = getAuthenticatedUser();
        return listRepository.findByOwnerOrderByDateUpdatedDesc(user);
    }

    public List createList(ListCreateRequest listReq) {
        List list = new List();
        // map from dto to entity then insert
        list.setName(listReq.getName());
        list.setShop(listReq.getShop());
        User user = getAuthenticatedUser();
        list.setOwner(user);
        list.setListItems(new HashSet<>());
        return listRepository.save(list);
    }


    public ResponseEntity<List> getList(Long id) {
        Optional<List> optionalList = listRepository.findById(id);
        if (optionalList.isPresent()) {
            return new ResponseEntity<>(optionalList.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public void deleteList(Long id) {
        listRepository.deleteById(id);
    }
}
