package com.hbh.springtesting.example.weather;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class WeatherClientTest2 {
// Here we mock the actual call to the weather service; we don't even start a fake server like with wiremock
    private WeatherClient subject;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() throws Exception {
        initMocks(this);
        subject = new WeatherClient(restTemplate, "http://localhost:8089", "someAppId");
    }

    @Test
    public void shouldCallWeatherService() throws Exception {
        WeatherResponse expectedResponse = new WeatherResponse("light rain");
        given(restTemplate.getForObject("http://localhost:8089/someAppId/53.5511,9.9937", WeatherResponse.class))
                .willReturn(expectedResponse);

        Optional<WeatherResponse> actualResponse = subject.fetchWeather();

        assertThat(actualResponse, is(Optional.of(expectedResponse)));
    }

    @Test
    public void shouldReturnEmptyOptionalIfWeatherServiceIsUnavailable() throws Exception {
        given(restTemplate.getForObject("http://localhost:8089/someAppId/53.5511,9.9937", WeatherResponse.class))
                .willThrow(new RestClientException("something went wrong"));

        Optional<WeatherResponse> actualResponse = subject.fetchWeather();

        assertThat(actualResponse, is(Optional.empty()));

    }

}