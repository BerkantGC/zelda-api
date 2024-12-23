package com.gc.zelda_api.service.dungeon;

import com.gc.zelda_api.model.Dungeon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;

public interface DungeonService {
    Page<Dungeon> getDungeons(PageRequest pageable);

    Dungeon getDungeon(String id);
}
