package com.gc.zelda_api.service.game;

import com.gc.zelda_api.model.Game;
import com.gc.zelda_api.model.User;
import com.gc.zelda_api.repository.GameRepository;
import com.gc.zelda_api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class GameServiceImp implements GameService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameServiceImp(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Async
    @Cacheable("Games")
    public CompletableFuture<List<Game>> getGames(){
        return CompletableFuture.supplyAsync(gameRepository::findAll);
    }

    @Override
    @Async
    public void insertGames(List<Game> games) {
        CompletableFuture.supplyAsync(() -> gameRepository.saveAll(games));
    }

    @Override
    @Async
    public CompletableFuture<Game> addFavorite(Game game) {
        game = gameRepository.findById(game.getId());
        if(game == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Game not found"
            );

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Game finalGame = game;
        return CompletableFuture.supplyAsync(() ->{
            List<Game> favorites = user.getFavoriteGames();

            if(favorites == null)
            {
                favorites = List.of(finalGame);
                user.setFavoriteGames(favorites);
            } else if(!favorites.contains(finalGame)){
                favorites.add(finalGame);
            }else{
                return null;
            }

            userRepository.save(user);
            return finalGame;
        });

    }
}
