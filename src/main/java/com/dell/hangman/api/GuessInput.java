package com.dell.hangman.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class GuessInput {

    @NotBlank
    @NotNull
    private String letter;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }
}
