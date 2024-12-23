package com.gc.zelda_api.repository;

import com.gc.zelda_api.model.Dungeon;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DungeonRepository extends MongoRepository<Dungeon, Long> {
    Dungeon findById(String id);
}
