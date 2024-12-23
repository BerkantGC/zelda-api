package com.gc.zelda_api.repository;

import com.gc.zelda_api.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character, Long> {
    Character findById(String id);
    Page<Character> findAllByAppearancesContaining(String id, PageRequest pageRequest);
}
