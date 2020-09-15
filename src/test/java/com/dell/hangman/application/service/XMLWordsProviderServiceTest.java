package com.dell.hangman.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class XMLWordsProviderServiceTest {
    @Test
    void shouldParseXMLAndReturnList() {
        XMLWordsProviderService service = new XMLWordsProviderService();
        List<String> words = service.getWords();
        assertThat(words).hasSize(15);
        assertThat(words).contains("DELL", "DELIVER", "TECHNOLOGY");
    }
}
