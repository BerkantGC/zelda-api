package com.gc.zelda_api.service.character;

import com.gc.zelda_api.model.Character;
import com.gc.zelda_api.repository.CharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CharacterServiceImp implements CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterServiceImp(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    public Character getCharacter(String id) {
        return characterRepository.findById(id);
    }

    public Page<Character> getCharacters(PageRequest pageRequest) {
        return characterRepository.findAll(pageRequest);
    }

    @Override
    public Page<Character> getCharactersByGame(String gameId, PageRequest pageRequest) {
        return characterRepository
                .findAllByAppearancesContaining(gameId, pageRequest);
    }

}
