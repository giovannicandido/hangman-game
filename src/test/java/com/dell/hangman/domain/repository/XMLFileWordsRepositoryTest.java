package com.dell.hangman.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dell.hangman.insfrastructure.repository.XMLFileWordsRepository;

class XMLFileWordsRepositoryTest {
    @Test
    void shouldParseXMLAndReturnList() {
        XMLFileWordsRepository repository = new XMLFileWordsRepository();
        List<String> words = repository.getWords();
        assertThat(words).hasSize(15);
        assertThat(words).contains("DELL", "DELIVER", "TECHNOLOGY");
    }
}
