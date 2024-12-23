package com.gc.zelda_api.repository;

import com.gc.zelda_api.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, Long> {
    Item findById(String id);
    Page<Item> findAllByGamesContaining(String gameID, PageRequest pageRequest);
}
