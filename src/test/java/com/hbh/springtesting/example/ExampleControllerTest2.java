package com.hbh.springtesting.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.hbh.springtesting.example.person.Person;
import com.hbh.springtesting.example.person.PersonRepository;
import com.hbh.springtesting.example.weather.WeatherClient;
import com.hbh.springtesting.example.weather.WeatherResponse;

// chh testing some implementation of ExampleController testing
//@ExtendWith(MockitoExtension.class) not needed
public class ExampleControllerTest2 {
	
	private ExampleController exampleCtl;
	// need an instance of ExampleController to test it
	
	//need mocks of personRepository, weatherClient which are defined in other packages
	// this could be written by other persons so need to mock them
	
    @Mock
    private PersonRepository personRepository;

    @Mock
    private WeatherClient weatherClient;
	
    //Attention @Before is for Junit4!
    @BeforeEach
    public void setUp() throws Exception {
    	initMocks(this);
    	exampleCtl = new ExampleController(personRepository, weatherClient);
    	
    }
// no need for @ springboot related annotation because we are just testing the methods, like a
// regular class
	@Test
	public void shouldReturnHelloWorld() throws Exception {
		assertEquals(exampleCtl.hello(), "Hello World!");
	}

    @Test
    public void shouldReturnFullNameOfAPerson() throws Exception {
        Person peter = new Person("Peter", "Pan");
        given(personRepository.findByLastName("Pan")).willReturn(Optional.of(peter)); //Given
        // set the response of the Mock
        String greeting = exampleCtl.hello("Pan"); //When
        assertEquals(greeting, "Hello Peter Pan!"); //Then
    }
    
    @Test
    public void shouldTellIfPersonIsUnknown() throws Exception {
        given(personRepository.findByLastName(anyString())).willReturn(Optional.empty());

        String greeting = exampleCtl.hello("Pan");
        assertEquals(greeting, "Who is this 'Pan' you're talking about?");
    }

    @Test
    public void shouldReturnWeatherClientResult() throws Exception {
        WeatherResponse weatherResponse = new WeatherResponse("Hamburg, 8°C raining");
        given(weatherClient.fetchWeather()).willReturn(Optional.of(weatherResponse));

        String weather = exampleCtl.weather();

        assertEquals(weather, "Hamburg, 8°C raining");
    }

    @Test
    public void shouldReturnErrorMessageIfWeatherClientIsUnavailable() throws Exception {
        given(weatherClient.fetchWeather()).willReturn(Optional.empty());

        String weather = exampleCtl.weather();

        assertEquals(weather, "Sorry, I couldn't fetch the weather for you :(");
    }
}

