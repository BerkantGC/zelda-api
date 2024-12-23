package com.gc.zelda_api.service.game;

import com.gc.zelda_api.model.Game;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GameService {
    CompletableFuture<List<Game>> getGames();

    void insertGames(List<Game> games);

    CompletableFuture<Game> addFavorite(Game game);
}
