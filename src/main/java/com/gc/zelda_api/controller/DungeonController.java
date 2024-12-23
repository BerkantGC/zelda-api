package com.gc.zelda_api.controller;

import com.gc.zelda_api.model.Dungeon;
import com.gc.zelda_api.service.dungeon.DungeonsServiceImp;
import com.gc.zelda_api.util.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/dungeons")
public class DungeonController {
    private final DungeonsServiceImp dungeonsServiceImp;

    public DungeonController(DungeonsServiceImp dungeonsServiceImp) {
        this.dungeonsServiceImp = dungeonsServiceImp;
    }

    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getDungeons(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit);

        Page<Dungeon> dungeons = dungeonsServiceImp.getDungeons(pageRequest);
        return ResponseEntity.ok(
                PageUtils.getJSONByPage(dungeons, "dungeons")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dungeon> getDungeonById(@PathVariable("id") String id) {
        return ResponseEntity.ok(dungeonsServiceImp.getDungeon(id));
    }
}
