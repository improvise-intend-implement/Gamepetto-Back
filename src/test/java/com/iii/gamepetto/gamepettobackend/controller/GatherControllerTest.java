package com.iii.gamepetto.gamepettobackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iii.gamepetto.gamepettobackend.configuration.MessageSourceConfig;
import com.iii.gamepetto.gamepettobackend.exception.RestResponseEntityExceptionHandler;
import com.iii.gamepetto.gamepettobackend.service.GatherService;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GatherControllerTest {

	final ObjectMapper objectMapper = new ObjectMapper();
	final MessageSourceConfig messageSourceConfig = new MessageSourceConfig();
	MockMvc mockMvc;
	@Mock
	GatherService gatherService;
	@InjectMocks
	GatherController sut;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		RestResponseEntityExceptionHandler exceptionHandler = new RestResponseEntityExceptionHandler(messageSourceConfig.messageSource());
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.sut, exceptionHandler).build();
	}

	@Test
	void createGatherShouldReturnCreatedStatusWhenParametersAreValid() throws Exception{
		//given
		GatherRequest request = new GatherRequest();
		request.setGuildId("123");
		request.setGameId(1L);
		request.setShortName("test");
		request.setName("Testo wy");
		request.setChannelId("22321");
		request.setPlayersPerTeam(10);
		request.setMapsNumber(5);
		request.setAllAllowed(true);
		request.setCaptainRolePriority(true);
		request.setMapsRandom(false);
		given(this.gatherService.nameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.shortNameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.channelExists(anyString())).willReturn(false);

		//when
		//then
		this.mockMvc.perform(post("/gathers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());
		then(this.gatherService).should(times(1)).createGather(any(GatherRequest.class));
	}

	@Test
	void createGatherShouldReturnBadRequestWhenAtLeastOneOfTheParametersIsInvalid() throws Exception {
		//given
		GatherRequest request = new GatherRequest();
		request.setGuildId("123");
		request.setGameId(1L);
		request.setShortName("test");
		request.setName(""); //invalid
		request.setChannelId("22321");
		request.setPlayersPerTeam(10);
		request.setMapsNumber(5);
		request.setAllAllowed(true);
		request.setCaptainRolePriority(true);
		request.setMapsRandom(false);
		given(this.gatherService.nameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.shortNameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.channelExists(anyString())).willReturn(false);

		//when
		//then
		this.mockMvc.perform(post("/gathers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
		then(this.gatherService).should(never()).createGather(any(GatherRequest.class));
	}
}
