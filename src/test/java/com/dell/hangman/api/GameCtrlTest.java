package com.dell.hangman.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.dell.hangman.domain.GuessResponse;
import net.minidev.json.JSONObject;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameCtrlTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    @DisplayName("Should return the length of a random word on new game")
    void newGame() {
        Integer response = restTemplate.getForObject(getServerUrl() + "/api/new", Integer.class);
        assertThat(response).isGreaterThan(0);
    }

    @Test
    @Order(2)
    @DisplayName("Should return a guess response after new game")
    void guessWord() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonRequest = new JSONObject();
        final String LETTER = "a";
        jsonRequest.put("letter", LETTER);
        HttpEntity<String> request = new HttpEntity<>(jsonRequest.toString(), headers);

        ResponseEntity<GuessResponse> response = restTemplate
                .postForEntity(getServerUrl() + "/api/guess", request, GuessResponse.class);

        GuessResponse content = response.getBody();

        assertThat(content.getLetter().toString()).isEqualTo(LETTER);
        assertThat(content.isGameLost()).isFalse();
        assertThat(content.isGameWon()).isFalse();
    }

    private String getServerUrl() {
        return "http://localhost:" + port;
    }
}
