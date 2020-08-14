package com.iii.gamepetto.gamepettobackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iii.gamepetto.gamepettobackend.service.GuildService;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GuildControllerTest {

    final ObjectMapper objectMapper = new ObjectMapper();
    MockMvc mockMvc;
    @Mock
    GuildService guildService;
    @InjectMocks
    GuildController sut;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.sut).build();
    }

    @Test
    void addGuildShouldReturnCreatedStatusWhenParameterIsValid() throws Exception {
        //given
        GuildRequest request = new GuildRequest();
        request.setGuildId("123213");
        request.setName("Testowy");
        given(this.guildService.saveOrUpdate(any(GuildRequest.class))).willReturn(new GuildResponse());

        // when
        // then
        this.mockMvc.perform(post("/guild")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        then(this.guildService).should(times(1)).saveOrUpdate(any(GuildRequest.class));
    }

    @Test
    void addGuildShouldReturnBadRequestWhenParameterIsInvalid() throws Exception {
        //given
        GuildRequest request = new GuildRequest();
        request.setGuildId("");
        request.setName("");

        //when
        //then
        this.mockMvc.perform(post("/guild")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        then(this.guildService).should(never()).saveOrUpdate(any(GuildRequest.class));
    }

    @Test
    void removeGuildShouldReturnOkWhenGuildExist() throws Exception {
        //given
        given(this.guildService.updateBotPresentToFalse(anyString())).willReturn(true);

        //when
        //then
        this.mockMvc.perform(delete("/guild/1234"))
                .andExpect(status().isOk());
        then(this.guildService).should(times(1)).updateBotPresentToFalse(anyString());
    }

    @Test
    void removeGuildShouldReturnNotFoundWhenGuildDoesntExist() throws Exception {
        //given
        given(this.guildService.updateBotPresentToFalse(anyString())).willReturn(false);

        //when
        //then
        this.mockMvc.perform(delete("/guild/1234"))
                .andExpect(status().isNotFound());
        then(this.guildService).should(times(1)).updateBotPresentToFalse(anyString());
    }

}
