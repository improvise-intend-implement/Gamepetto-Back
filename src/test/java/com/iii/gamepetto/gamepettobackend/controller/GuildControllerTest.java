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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private GuildService guildService;
    @InjectMocks
    private GuildController sut;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

    @Test
    public void addGuildShouldReturnCreatedStatusWhenParameterIsValid() throws Exception {
        //given
        GuildRequest request = new GuildRequest();
        request.setGuildId("123213");
        request.setName("Testowy");
        given(guildService.saveOrUpdate(any(GuildRequest.class))).willReturn(new GuildResponse());

        // when
        // then
        mockMvc.perform(post("/guild")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
        then(guildService).should(times(1)).saveOrUpdate(any(GuildRequest.class));
    }

    @Test
    public void addGuildShouldReturnBadRequestWhenParameterIsInvalid() throws Exception {
        //given
        GuildRequest request = new GuildRequest();
        request.setGuildId("");
        request.setName("");

        //when
        //then
        mockMvc.perform(post("/guild")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
        then(guildService).should(never()).saveOrUpdate(any(GuildRequest.class));
    }

    @Test
    public void removeGuildShouldReturnOkWhenGuildExist() throws Exception {
        //given
        given(guildService.updateBotPresentToFalse(anyString())).willReturn(true);

        //when
        //then
        mockMvc.perform(delete("/guild/1234"))
                .andExpect(status().isOk());
        then(guildService).should(times(1)).updateBotPresentToFalse(anyString());
    }

    @Test
    public void removeGuildShouldReturnNotFoundWhenGuildDoesntExist() throws Exception {
        //given
        given(guildService.updateBotPresentToFalse(anyString())).willReturn(false);

        //when
        //then
        mockMvc.perform(delete("/guild/1234"))
                .andExpect(status().isNotFound());
        then(guildService).should(times(1)).updateBotPresentToFalse(anyString());
    }

}
