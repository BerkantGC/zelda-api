package com.gc.zelda_api.service.dungeon;

import com.gc.zelda_api.model.Dungeon;
import com.gc.zelda_api.repository.DungeonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class DungeonsServiceImp implements DungeonService {
    private final DungeonRepository dungeonRepository;

    public DungeonsServiceImp(DungeonRepository dungeonRepository) {
        this.dungeonRepository = dungeonRepository;
    }

    @Override
    public Page<Dungeon> getDungeons(PageRequest pageable) {
        return dungeonRepository.findAll(pageable);
    }

    @Override
    public Dungeon getDungeon(String id) {
        return dungeonRepository.findById(id);
    }
}
