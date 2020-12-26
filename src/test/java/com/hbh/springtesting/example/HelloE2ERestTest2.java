package com.hbh.springtesting.example;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.hbh.springtesting.example.person.Person;
import com.hbh.springtesting.example.person.PersonRepository;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloE2ERestTest2 {

    @Autowired
    private PersonRepository personRepository;

    @LocalServerPort
    private int port;

    @AfterEach
    public void tearDown() throws Exception {
        personRepository.deleteAll();
    }

    @Test
    public void shouldReturnHelloWorld() throws Exception {
        when()
                .get(String.format("http://localhost:%s/hello", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello World!"));
    }

    @Test
    public void shouldReturnGreeting() throws Exception {
        Person peter = new Person("Peter", "Pan");
        personRepository.save(peter);

        when()
                .get(String.format("http://localhost:%s/hello/Pan", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Hello Peter Pan!"));
    }
}

