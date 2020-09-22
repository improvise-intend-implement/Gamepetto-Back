package com.iii.gamepetto.gamepettobackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iii.gamepetto.gamepettobackend.configuration.MessageSourceConfig;
import com.iii.gamepetto.gamepettobackend.exception.RestResponseEntityExceptionHandler;
import com.iii.gamepetto.gamepettobackend.service.GameService;
import com.iii.gamepetto.gamepettobackend.service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GameControllerTest {

	final ObjectMapper objectMapper = new ObjectMapper();
	final MessageSourceConfig messageSourceConfig = new MessageSourceConfig();
	MockMvc mockMvc;
	@Mock
	GameService gameService;
	@Mock
	MapService mapService;
	@InjectMocks
	GameController sut;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		RestResponseEntityExceptionHandler exceptionHandler = new RestResponseEntityExceptionHandler(messageSourceConfig.messageSource());
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.sut, exceptionHandler).build();
	}

	@Test
	void getAllGamesShouldReturnOkStatus() throws Exception {
		//given
		//when
		//then
		this.mockMvc.perform(get("/games"))
				.andExpect(status().isOk());
	}

	@Test
	void getAllGamesShouldCallGameServiceMethod() throws Exception {
		//given
		//when
		this.mockMvc.perform(get("/games"));

		//then
		then(this.gameService).should(times(1)).findAll();
	}

	@Test
	void getMapsShouldReturnOkStatus() throws Exception {
		//given
		//when
		//then
		this.mockMvc.perform(get("/games/1/maps"))
				.andExpect(status().isOk());
	}

	@Test
	void getMapsShouldCallMapServiceMethod() throws Exception {
		//given
		//when
		this.mockMvc.perform(get("/games/1/maps"));

		//then
		then(this.mapService).should(times(1)).findAllByGameId(anyLong());
	}
}
