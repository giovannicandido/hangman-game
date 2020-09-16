package com.dell.hangman.domain.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.dell.hangman.domain.GuessResponse;
import com.dell.hangman.domain.HangmanDomainException;
import com.dell.hangman.domain.repository.WordsRepository;

@Service
public class GameService {
    private static final int MAX_GUESSES = 6;

    private final WordsRepository wordsRepository;

    private String hiddenWord;
    private int numberCorrectGuesses;
    private int numberIncorrectedGuesses;
    private Set<Character> wordsAttempted;

    private List<String> words;

    public GameService(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
        words = wordsRepository.getWords();
    }

    public Integer initialize() {
        int index = new Random().nextInt(words.size());
        hiddenWord = words.get(index);
        numberIncorrectedGuesses = 0;
        numberCorrectGuesses = 0;
        wordsAttempted = new HashSet<>();
        return hiddenWord.length();
    }

    public GuessResponse guess(Character character) {
        if(!isInitialized()) {
            throw new HangmanDomainException("Create a new game");
        }
        if(isGameWon()) {
            throw new HangmanDomainException("Game won, create a new one");
        }
        if (isGameLost()) {
            throw new HangmanDomainException("Game lost, create a new one");
        }
        List<Integer> positions = verifyAssertions(character);
        if(!this.wordsAttempted.contains(character)) {
            if(positions.size()==0) { // means the word is wrong
                numberIncorrectedGuesses++;
            } else {
                numberCorrectGuesses+=positions.size();
            }
        }

        wordsAttempted.add(character);

        GuessResponse guessResponse = new GuessResponse();
        guessResponse.setLetter(character);
        guessResponse.setPositions(positions);
        guessResponse.setAttemptsRemaining(MAX_GUESSES - numberIncorrectedGuesses);
        guessResponse.setGameLost(isGameLost());
        guessResponse.setGameWon(isGameWon());
        return guessResponse;

    }

    private boolean isGameWon() {
        return numberCorrectGuesses == hiddenWord.length();
    }

    private boolean isGameLost() {
        return numberIncorrectedGuesses > MAX_GUESSES - 1;
    }

    private boolean isInitialized() {
        return hiddenWord != null;
    }

    private List<Integer> verifyAssertions(Character character) {
        List<Integer> positions = new ArrayList<>();
        int index = 0;
        for (Character c : hiddenWord.toCharArray()) {
            Character characterLowerCase1 = Character.toLowerCase(c);
            Character characterLowerCase2 = Character.toLowerCase(character);
            if (characterLowerCase1.equals(characterLowerCase2)) {
                positions.add(index);
            }
            index++;
        }
        return positions;
    }
}
