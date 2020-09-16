package com.dell.hangman.infrastructure.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class XMLFileWordsRepositoryTest {
    @Test
    @DisplayName("Should parse xml and return a list of words")
    void shouldParseXMLAndReturnList() {
        XMLFileWordsRepository repository = new XMLFileWordsRepository();
        List<String> words = repository.getWords();
        assertThat(words).hasSize(15);
        assertThat(words).contains("DELL", "DELIVER", "TECHNOLOGY");
    }
}
