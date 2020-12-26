package com.hbh.springtesting.example.weather;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.hbh.springtesting.example.helper.FileLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest
public class WeatherClientIntegrationTest2 {

    @Autowired
    private WeatherClient subject;

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
    public void shouldCallWeatherService() throws Exception {

        Optional<WeatherResponse> weatherResponse = subject.fetchWeather(); //this will hit the URL defined in application.properties

        Optional<WeatherResponse> expectedResponse = Optional.of(new WeatherResponse("Rain"));
        assertThat(weatherResponse, is(expectedResponse));
    }
}
