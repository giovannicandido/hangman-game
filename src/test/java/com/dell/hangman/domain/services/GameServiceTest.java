package com.dell.hangman.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dell.hangman.domain.GuessResponse;
import com.dell.hangman.domain.repository.WordsRepository;

class GameServiceTest {

    @Test
    @DisplayName("Should initialize")
    void shouldInitialize() {
        GameService gameService = new GameService(new FixedWordsRepository());
        Integer characterCount = gameService.initialize();
        assertThat(characterCount).isEqualTo(6);
    }

    @Test
    @DisplayName("Should ask for initialize in guess response")
    void guessShouldAskForInitialize() {
        GameService gameService = new GameService(new FixedWordsRepository());
        Exception exception = assertThrows(RuntimeException.class, () -> gameService.guess('h'));

        assertThat(exception.getMessage()).isEqualTo("Create a new game");

    }

    @Test
    @DisplayName("Should return a list of correct positions on guess")
    void guessShouldReturnListOfCorrectPositions() {
        GameService gameService = new GameService(new FixedWordsRepository());
        gameService.initialize();

        GuessResponse response = gameService.guess('h');

        assertThat(response.getLetter()).isEqualTo('h');
        assertThat(response.getAttemptsRemaining()).isEqualTo(6);
        assertThat(response.getPositions()).containsOnly(0);
        assertThat(response.isGameLost()).isFalse();
        assertThat(response.isGameWon()).isFalse();
    }

    @Test
    @DisplayName("Should flag game as won")
    void gameWin() {
        GameService gameService = new GameService(new FixedWordsRepository());
        gameService.initialize();
        gameService.guess('h');
        gameService.guess('i');
        gameService.guess('d');
        gameService.guess('e');

        GuessResponse response = gameService.guess('n');

        assertThat(response.getLetter()).isEqualTo('n');
        assertThat(response.getAttemptsRemaining()).isEqualTo(6);
        assertThat(response.getPositions()).containsOnly(5);
        assertThat(response.isGameLost()).isFalse();
        assertThat(response.isGameWon()).isTrue();
    }

    @Test
    @DisplayName("Should flag game as lost")
    void gameLost() {
        GameService gameService = new GameService(new FixedWordsRepository());
        gameService.initialize();
        gameService.guess('a');
        gameService.guess('b');
        gameService.guess('c');
        gameService.guess('f');
        gameService.guess('g');

        GuessResponse response = gameService.guess('j');

        assertThat(response.getLetter()).isEqualTo('j');
        assertThat(response.getAttemptsRemaining()).isEqualTo(0);
        assertThat(response.getPositions()).isEmpty();
        assertThat(response.isGameLost()).isTrue();
        assertThat(response.isGameWon()).isFalse();
    }

    @Test
    @DisplayName("Should return exception on game lost when another guess is tried")
    void shouldReturnExceptionInGameLost() {
        GameService gameService = new GameService(new FixedWordsRepository());
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
    @DisplayName("Should return exception on game win when another guess is tried")
    void shouldReturnExeptionInGameWon() {
        GameService gameService = new GameService(new FixedWordsRepository());
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
    @DisplayName("Should ignore repeated word when already right")
    void shouldIgnoreRepeatedWordsForRight() {
        GameService gameService = new GameService(new FixedWordsRepository());
        gameService.initialize();
        gameService.guess('h');
        gameService.guess('h');
        gameService.guess('h');
        gameService.guess('h');
        gameService.guess('h');
        GuessResponse response = gameService.guess('h');

        assertThat(response.getPositions()).containsOnly(0);
        assertThat(response.getAttemptsRemaining()).isEqualTo(6);
        assertThat(response.getLetter()).isEqualTo('h');

        assertThat(response.isGameWon()).isFalse();
        assertThat(response.isGameLost()).isFalse();

    }

    @Test
    @DisplayName("Should ignore repeated word when already wrong")
    void shouldIgnoreRepeatedWordsForWrong() {
        GameService gameService = new GameService(new FixedWordsRepository());
        gameService.initialize();
        gameService.guess('a');
        gameService.guess('a');
        gameService.guess('a');
        gameService.guess('a');
        gameService.guess('a');
        GuessResponse response = gameService.guess('a');

        assertThat(response.getPositions()).isEmpty();
        assertThat(response.getAttemptsRemaining()).isEqualTo(5);
        assertThat(response.getLetter()).isEqualTo('a');

        assertThat(response.isGameWon()).isFalse();
        assertThat(response.isGameLost()).isFalse();

    }

    @Test
    @DisplayName("Should ignore case of letter")
    void shouldIgnoreCase() {
        GameService gameService = new GameService(new FixedWordsRepository());
        gameService.initialize();
        GuessResponse response = gameService.guess('H');

        assertThat(response.getPositions()).containsOnly(0);
        assertThat(response.getAttemptsRemaining()).isEqualTo(6);
        assertThat(response.getLetter()).isEqualTo('H');

        assertThat(response.isGameWon()).isFalse();
        assertThat(response.isGameLost()).isFalse();

    }

    static class FixedWordsRepository implements WordsRepository {

        @Override
        public List<String> getWords() {
            return List.of("hidden");
        }
    }
}
