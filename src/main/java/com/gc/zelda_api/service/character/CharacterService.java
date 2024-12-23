package com.gc.zelda_api.service.character;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CharacterService {
    com.gc.zelda_api.model.Character getCharacter(String id);
    Page<com.gc.zelda_api.model.Character> getCharacters(PageRequest pageRequest);

    Page<com.gc.zelda_api.model.Character> getCharactersByGame(String gameId, PageRequest pageRequest);

}
