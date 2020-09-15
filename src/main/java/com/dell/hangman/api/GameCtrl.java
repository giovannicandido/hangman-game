package com.dell.hangman.api;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dell.hangman.application.service.GameService;

@RestController
public class GameCtrl {
    private final GameService gameService;

    public GameCtrl(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/guess")
    public GuessResponse guessWord(@Valid @RequestBody GuessInput guessInput) {
        return gameService.guess(guessInput.getLetter().charAt(0));
    }

    @GetMapping("/new")
    public Integer newGame() {
        return gameService.initialize();
    }
}
