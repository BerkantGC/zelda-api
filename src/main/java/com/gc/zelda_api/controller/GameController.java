package com.gc.zelda_api.controller;

import com.gc.zelda_api.model.Game;
import com.gc.zelda_api.service.game.GameServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameServiceImp gameService;

    public GameController(GameServiceImp gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames() throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(gameService.getGames().get());
    }

    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertGames(@RequestBody List<Game> games) {
        gameService.insertGames(games);
        return ResponseEntity.ok(new HashMap<>());
    }

    @PostMapping("/add-favorite")
    public ResponseEntity<Game> addFavoriteGames(@RequestBody Game game) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok( gameService.addFavorite(game).get());
    }
}
