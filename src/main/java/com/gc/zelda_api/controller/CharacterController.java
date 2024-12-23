package com.gc.zelda_api.controller;

import com.gc.zelda_api.model.Character;
import com.gc.zelda_api.service.character.CharacterServiceImp;
import com.gc.zelda_api.util.PageUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/characters")
public class CharacterController {


    private final CharacterServiceImp characterServiceImp;

    public CharacterController(CharacterServiceImp characterServiceImp) {
        this.characterServiceImp = characterServiceImp;
    }

    @GetMapping
    public ResponseEntity<Object> getCharacters(
            @RequestParam(required = false) String game_id,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        PageRequest request = PageRequest.of(page - 1, size);

        if (game_id != null) {
            return ResponseEntity.ok(PageUtils
                    .getJSONByPage(
                        characterServiceImp.getCharactersByGame(game_id, request),
                    "characters")
                    );
        }

        Page<Character> characters = characterServiceImp.getCharacters(request);
        return ResponseEntity.ok(PageUtils.getJSONByPage(characters, "characters"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable("id") String id) {
        return ResponseEntity.ok(characterServiceImp.getCharacter(id));
    }
}