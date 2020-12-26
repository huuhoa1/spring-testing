package com.hbh.springtesting.example.weather;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hbh.springtesting.example.helper.FileLoader;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherAcceptanceTest2 {

    @LocalServerPort
    private int port;

    WireMockServer wireMockServer;
    
    @BeforeEach
    public void setup () throws IOException {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        setupStub();
    }
 
    @AfterEach
    public void teardown () {
        wireMockServer.stop();
    }

    public void setupStub() throws IOException {
    	wireMockServer.stubFor(get(urlPathEqualTo("/d1fa757ce6e36e1767e16a3dd7bba01b/53.5511,9.9937"))
                .willReturn(aResponse()
                        .withBody(FileLoader.read("classpath:weatherApiResponse.json"))
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));
    }
    
    @Test
    public void shouldReturnYesterdaysWeather() throws Exception {
    	

        when()
                .get(String.format("http://localhost:%s/weather", port))
                .then()
                .statusCode(is(200))
                .body(containsString("Rain"));
    }
}
