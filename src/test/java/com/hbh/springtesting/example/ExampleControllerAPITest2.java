package com.hbh.springtesting.example;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hbh.springtesting.example.person.Person;
import com.hbh.springtesting.example.person.PersonRepository;
import com.hbh.springtesting.example.weather.WeatherClient;
import com.hbh.springtesting.example.weather.WeatherResponse;

//testing controller ExampleController at the web layer 

@WebMvcTest(controllers = ExampleController.class)
public class ExampleControllerAPITest2 {

	@Autowired
	private MockMvc mockMvc; // given automatically by @WebMvcTest

	@MockBean
	private PersonRepository personRepository;

	@MockBean
	private WeatherClient weatherClient;

	@Test
	public void shouldReturnHelloWorld() throws Exception {
		mockMvc.perform(get("/hello")).andExpect(content().string("Hello World!"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldReturnFullName() throws Exception {
		Person peter = new Person("Peter", "Pan");
		given(personRepository.findByLastName("Pan")).willReturn(Optional.of(peter));

		mockMvc.perform(get("/hello/Pan")).andExpect(content().string("Hello Peter Pan!"))
				.andExpect(status().is2xxSuccessful());
	}

	@Test
	public void shouldReturnCurrentWeather() throws Exception {
		WeatherResponse weatherResponse = new WeatherResponse("Hamburg, 8°C raining");
		given(weatherClient.fetchWeather()).willReturn(Optional.of(weatherResponse));

		mockMvc.perform(get("/weather")).andExpect(status().is2xxSuccessful())
				.andExpect(content().string("Hamburg, 8°C raining"));
	}
}
