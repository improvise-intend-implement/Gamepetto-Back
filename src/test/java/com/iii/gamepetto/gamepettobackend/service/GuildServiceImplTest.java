package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildPrefix;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class GuildServiceImplTest {

    @Mock
    GuildRepository guildRepository;
    @Spy
    ModelMapper modelMapper;
    @InjectMocks
    GuildServiceImpl sut;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveOrUpdateShouldCallSaveMethodFromRepository() {
        //given
        given(this.guildRepository.findByGuildId(anyString())).willReturn(null);
        given(this.guildRepository.save(any(Guild.class))).willReturn(new Guild());

        //when
        this.sut.saveOrUpdate(new GuildRequest());

        //then
        then(this.guildRepository).should(times(1)).save(any(Guild.class));
    }

    @Test
    void saveOrUpdateShouldReturnAppropriateObject() {
        //given
        Guild guild = new Guild();
        guild.setId(1L);
        guild.setGuildId("testowy");
        guild.setBotPrefix("!gp");
        given(this.guildRepository.findByGuildId(any())).willReturn(guild);
        given(this.guildRepository.save(any(Guild.class))).willReturn(guild);

        //when
        GuildResponse result = this.sut.saveOrUpdate(new GuildRequest());

        //then
        assertThat(result.getId(), is(guild.getId()));
        assertThat(result.getGuildId(), is(guild.getGuildId()));
        assertThat(result.getBotPrefix(), is(guild.getBotPrefix()));
    }

    @Test
    void saveOrUpdateShouldSetBotPresentToTrueWhenServerExistsInDb() {
        //given
        Guild guild = mock(Guild.class);
        given(this.guildRepository.findByGuildId(any())).willReturn(guild);
        given(this.guildRepository.save(any(Guild.class))).willReturn(new Guild());

        //when
        this.sut.saveOrUpdate(new GuildRequest());

        //then
        then(guild).should(times(1)).setBotPresent(true);
    }

    @Test
    void updateBotPresentToFalseShouldReturnTrueWhenGuildExists() {
        //given
        given(this.guildRepository.findByGuildId(anyString())).willReturn(new Guild());

        //when
        boolean result = this.sut.updateBotPresentToFalse(anyString());

        //then
        assertThat(result, is(true));
    }

    @Test
    void updateBotPresentToFalseShouldReturnFalseWhenGuildDoesntExist() {
        //given
        given(this.guildRepository.findByGuildId(anyString())).willReturn(null);

        //when
        boolean result = this.sut.updateBotPresentToFalse(anyString());

        //then
        assertThat(result, is(false));
    }

    @Test
    void updateBotPresentToFalseShouldSetThePropertyToFalseWhenGuildExists() {
        //given
        Guild guild = mock(Guild.class);
        given(this.guildRepository.findByGuildId(anyString())).willReturn(guild);

        //when
        this.sut.updateBotPresentToFalse(anyString());

        //then
        then(guild).should(times(1)).setBotPresent(false);
    }

    @Test
    void getAllPrefixesForBotsInServersShouldReturnStringStringMapWithValuesWhenThereAreRecordsInDb() {
        //given
        String guildId1 = "1234";
        String botPrefix1 = "!gp";
        String guildId2 = "4321";
        String botPrefix2 = "$";
        GuildPrefix guild1 = new GuildPrefix(guildId1, botPrefix1);
        GuildPrefix guild2 = new GuildPrefix(guildId2, botPrefix2);
        List<GuildPrefix> guildPrefixesList = List.of(guild1, guild2);
        given(this.guildRepository.findAllByBotPresentIsTrue()).willReturn(guildPrefixesList);

        //when
        Map<String, String> result = this.sut.getAllPrefixesForBotsInServers();

        //then
        assertThat(result.isEmpty(), is(false));
        assertThat(result.containsKey(guildId1), is(true));
        assertThat(result.containsKey(guildId2), is(true));
    }

    @Test
    void getAllPrefixesForBotsInServersShouldReturnEmptyStringStringMapWhenThereAreNoRecordsInDb() {
        //given
        given(this.guildRepository.findAllByBotPresentIsTrue()).willReturn(Collections.<GuildPrefix>emptyList());

        //when
        Map<String, String> result = this.sut.getAllPrefixesForBotsInServers();

        //then
        assertThat(result.isEmpty(), is(true));
    }
}
