package com.gc.zelda_api.repository;

import com.gc.zelda_api.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, Long> {
    boolean existsById(String id);
    Game findById(String id);
}
