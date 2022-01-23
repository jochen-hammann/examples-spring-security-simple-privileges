package com.example.spring.security.simpleprivileges.hello;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
        // TestRestTemplate requires a webEnvironment !!!
class HelloControllerIT
{
    // ============================== [Fields] ==============================

    // -------------------- [Private Fields] --------------------

    @Autowired
    private TestRestTemplate restTemplate;

    // ============================== [Unit Tests] ==============================

    // -------------------- [Test Helper Classes] --------------------

    // -------------------- [Test Helper Methods] --------------------

    private ResponseEntity<String> makeRestCall(String url, String username, String password)
    {
        return restTemplate.withBasicAuth(username, password).getForEntity(url, String.class);
    }

    // -------------------- [Test Initialization] --------------------

    // -------------------- [Tests] --------------------

    @Test
    void getTest()
    {
        ResponseEntity<String> response = makeRestCall("/hello", "user1", "user1Pass");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getOneTest()
    {
        ResponseEntity<String> response = makeRestCall("/hello/4711", "user2", "user2Pass");
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
