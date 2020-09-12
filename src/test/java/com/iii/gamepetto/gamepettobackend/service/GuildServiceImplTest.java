package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.exception.GamepettoEntityNotFoundException;
import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildPrefix;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        given(this.guildRepository.findById(anyString())).willReturn(null);
        given(this.guildRepository.save(any(GuildEntity.class))).willReturn(new GuildEntity());

        //when
        this.sut.saveOrUpdate(new GuildRequest());

        //then
        then(this.guildRepository).should(times(1)).save(any(GuildEntity.class));
    }

    @Test
    void saveOrUpdateShouldReturnAppropriateObject() {
        //given
        GuildEntity guildEntity = new GuildEntity();
        guildEntity.setId("testowy");
        guildEntity.setBotPrefix("!gp");
        given(this.guildRepository.findById(any())).willReturn(Optional.of(guildEntity));
        given(this.guildRepository.save(any(GuildEntity.class))).willReturn(guildEntity);

        //when
        GuildResponse result = this.sut.saveOrUpdate(new GuildRequest());

        //then
        assertThat(result.getId(), is(guildEntity.getId()));
        assertThat(result.getBotPrefix(), is(guildEntity.getBotPrefix()));
    }

    @Test
    void saveOrUpdateShouldSetBotPresentToTrueWhenServerExistsInDb() {
        //given
        GuildEntity guildEntity = mock(GuildEntity.class);
        given(this.guildRepository.findById(any())).willReturn(Optional.of(guildEntity));
        given(this.guildRepository.save(any(GuildEntity.class))).willReturn(guildEntity);

        //when
        this.sut.saveOrUpdate(new GuildRequest());

        //then
        then(guildEntity).should(times(1)).setBotPresent(true);
    }

    @Test
    void saveOrUpdateShouldSetBotPrefixToDefaultWhenServerExistsInDb() {
        //given
        String defaultPrefix = "?";
        GuildEntity guildEntity = mock(GuildEntity.class);
        given(this.guildRepository.findById(any())).willReturn(Optional.of(guildEntity));
        given(this.guildRepository.save(any(GuildEntity.class))).willReturn(guildEntity);

        //when
        this.sut.saveOrUpdate(new GuildRequest());

        //then
        then(guildEntity).should(times(1)).setBotPrefix(defaultPrefix);
    }

    @Test
    void updateBotPresentToFalseShouldReturnTrueWhenGuildExists() {
        //given
        given(this.guildRepository.findById(anyString())).willReturn(Optional.of(new GuildEntity()));

        //when
        boolean result = this.sut.updateBotPresentToFalse(anyString());

        //then
        assertThat(result, is(true));
    }

    @Test
    void updateBotPresentToFalseShouldCallSaveToTheRepository() {
        //given
        given(this.guildRepository.findById(anyString())).willReturn(Optional.of(new GuildEntity()));

        //when
        boolean result = this.sut.updateBotPresentToFalse(anyString());

        //then
        then(this.guildRepository).should(times(1)).save(any(GuildEntity.class));
    }

    @Test
    void updateBotPresentToFalseShouldReturnFalseWhenGuildDoesntExist() {
        //given
        given(this.guildRepository.findById(anyString())).willReturn(Optional.empty());

        //when
        boolean result = this.sut.updateBotPresentToFalse(anyString());

        //then
        assertThat(result, is(false));
    }

    @Test
    void updateBotPresentToFalseShouldSetThePropertyToFalseWhenGuildExists() {
        //given
        GuildEntity guildEntity = mock(GuildEntity.class);
        given(this.guildRepository.findById(anyString())).willReturn(Optional.of(guildEntity));

        //when
        this.sut.updateBotPresentToFalse(anyString());

        //then
        then(guildEntity).should(times(1)).setBotPresent(false);
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

    @Test
    void updateGuildPrefixShouldSaveGuildWhenEntityExists() {
        //given
        String guildId = "1";
        String botPrefix = "!gp";
        given(this.guildRepository.findById(guildId)).willReturn(Optional.of(new GuildEntity()));

        //when
        this.sut.updateGuildPrefix(guildId, botPrefix);

        //then
        then(this.guildRepository).should(times(1)).save(any(GuildEntity.class));
    }

    @Test
    void updateGuildPrefixShouldThrowExceptionWhenEntityDoesntExist() {
        assertThrows(GamepettoEntityNotFoundException.class, () -> {
            //given
            String guildId = "1";
            String botPrefix = "!gp";
            given(this.guildRepository.findById(anyString())).willReturn(Optional.empty());

            //when
            //then
            this.sut.updateGuildPrefix(guildId, botPrefix);
        });
    }

    @Test
    void updateGuildPrefixShouldSetBotPrefixOnFoundEntity() {
        //given
        String guildId = "1";
        String botPrefix = "!xd";
        GuildEntity guildEntity = mock(GuildEntity.class);
        given(this.guildRepository.findById(guildId)).willReturn(Optional.of(guildEntity));

        //when
        this.sut.updateGuildPrefix(guildId, botPrefix);

        //then
        then(guildEntity).should(times(1)).setBotPrefix(botPrefix);
    }
}
