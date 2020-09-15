package com.dell.hangman.api;

import java.util.List;

public class GuessResponse {
    private Character letter;
    private List<Integer> positions;
    private int attemptsRemaining = 0;
    private boolean gameWon = false;
    private boolean gameLost = false;

    public Character getLetter() {
        return letter;
    }

    public void setLetter(Character letter) {
        this.letter = letter;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public int getAttemptsRemaining() {
        return attemptsRemaining;
    }

    public void setAttemptsRemaining(int attemptsRemaining) {
        this.attemptsRemaining = attemptsRemaining;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }
}
