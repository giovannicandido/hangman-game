package com.dell.hangman.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dell.hangman.api.GuessResponse;

class GameServiceTest {


    @Test
    void initialize() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        Integer characterCount = gameService.initialize();
        assertThat(characterCount).isEqualTo(6);
    }

    @Test
    void guessShoutInitialize() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        Exception exception = assertThrows(RuntimeException.class, () -> gameService.guess('h'));

        assertThat(exception.getMessage()).isEqualTo("Create a new game");

    }

    @Test
    void guessShouldReturnListOfCharacter() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();

        GuessResponse response = gameService.guess('h');

        assertThat(response.getLetter()).isEqualTo('h');
        assertThat(response.getAttemptRemaining()).isEqualTo(6);
        assertThat(response.getPositions()).containsOnly(0);
        assertThat(response.isGameLost()).isFalse();
        assertThat(response.isGameWon()).isFalse();
    }

    @Test
    void gameWin() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        gameService.guess('h');
        gameService.guess('i');
        gameService.guess('d');
        gameService.guess('e');

        GuessResponse response = gameService.guess('n');

        assertThat(response.getLetter()).isEqualTo('n');
        assertThat(response.getAttemptRemaining()).isEqualTo(6);
        assertThat(response.getPositions()).containsOnly(5);
        assertThat(response.isGameLost()).isFalse();
        assertThat(response.isGameWon()).isTrue();
    }

    @Test
    void gameLost() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        gameService.guess('a');
        gameService.guess('b');
        gameService.guess('c');
        gameService.guess('f');
        gameService.guess('g');

        GuessResponse response = gameService.guess('j');

        assertThat(response.getLetter()).isEqualTo('j');
        assertThat(response.getAttemptRemaining()).isEqualTo(0);
        assertThat(response.getPositions()).isEmpty();
        assertThat(response.isGameLost()).isTrue();
        assertThat(response.isGameWon()).isFalse();
    }

    @Test
    void shouldStopInGameLost() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        gameService.guess('a');
        gameService.guess('b');
        gameService.guess('c');
        gameService.guess('f');
        gameService.guess('g');
        gameService.guess('j');
        Exception exception = assertThrows(RuntimeException.class, () -> gameService.guess('k'));

        assertThat(exception.getMessage()).isEqualTo("Game lost, create a new one");

    }

    @Test
    void shouldStopInGameWon() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        gameService.guess('h');
        gameService.guess('i');
        gameService.guess('d');
        gameService.guess('e');
        gameService.guess('n');
        Exception exception = assertThrows(RuntimeException.class, () -> gameService.guess('k'));

        assertThat(exception.getMessage()).isEqualTo("Game won, create a new one");

    }

    @Test
    void shouldIgnoreRepeatedWordsForWin() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        gameService.guess('h');
        gameService.guess('h');
        gameService.guess('h');
        gameService.guess('h');
        gameService.guess('h');
        GuessResponse response = gameService.guess('h');

        assertThat(response.getPositions()).containsOnly(0);
        assertThat(response.getAttemptRemaining()).isEqualTo(6);
        assertThat(response.getLetter()).isEqualTo('h');

        assertThat(response.isGameWon()).isFalse();
        assertThat(response.isGameLost()).isFalse();

    }

    @Test
    void shouldIgnoreRepeatedWordsToLost() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        gameService.guess('a');
        gameService.guess('a');
        gameService.guess('a');
        gameService.guess('a');
        gameService.guess('a');
        GuessResponse response = gameService.guess('a');

        assertThat(response.getPositions()).isEmpty();
        assertThat(response.getAttemptRemaining()).isEqualTo(5);
        assertThat(response.getLetter()).isEqualTo('a');

        assertThat(response.isGameWon()).isFalse();
        assertThat(response.isGameLost()).isFalse();

    }

    @Test
    void shouldIgnoreCase() {
        GameService gameService = new GameService(new FixedWordsProviderService());
        gameService.initialize();
        GuessResponse response = gameService.guess('H');

        assertThat(response.getPositions()).containsOnly(0);
        assertThat(response.getAttemptRemaining()).isEqualTo(6);
        assertThat(response.getLetter()).isEqualTo('H');

        assertThat(response.isGameWon()).isFalse();
        assertThat(response.isGameLost()).isFalse();

    }

    class FixedWordsProviderService implements WordsProviderService {

        @Override
        public List<String> getWords() {
            return List.of("hidden");
        }
    }
}
