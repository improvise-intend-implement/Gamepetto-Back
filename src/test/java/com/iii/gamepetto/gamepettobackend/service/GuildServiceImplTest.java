package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

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
    private GuildRepository guildRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private GuildServiceImpl guildService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveOrUpdateShouldCallSaveMethodFromRepository() {
        //given
        given(this.guildRepository.findByGuildId(anyString())).willReturn(null);
        given(this.guildRepository.save(any(Guild.class))).willReturn(new Guild());

        //when
        this.guildService.saveOrUpdate(new GuildRequest());

        //then
        then(this.guildRepository).should(times(1)).save(any(Guild.class));
    }

    @Test
    public void saveOrUpdateShouldReturnAppropriateObject() {
        //given
        Guild guild = new Guild();
        guild.setId(1L);
        guild.setGuildId("testowy");
        guild.setBotPrefix("!gp");
        given(this.guildRepository.findByGuildId(any())).willReturn(guild);
        given(this.guildRepository.save(any(Guild.class))).willReturn(guild);

        //when
        GuildResponse result = this.guildService.saveOrUpdate(new GuildRequest());

        //then
        assertThat(result.getId(), is(guild.getId()));
        assertThat(result.getGuildId(), is(guild.getGuildId()));
        assertThat(result.getBotPrefix(), is(guild.getBotPrefix()));
    }

    @Test
    public void saveOrUpdateShouldSetBotPresentToTrueWhenServerExistsInDb() {
        //given
        Guild guild = mock(Guild.class);
        given(this.guildRepository.findByGuildId(any())).willReturn(guild);
        given(this.guildRepository.save(any(Guild.class))).willReturn(new Guild());

        //when
        this.guildService.saveOrUpdate(new GuildRequest());

        //then
        then(guild).should(times(1)).setBotPresent(true);
    }

    @Test
    public void updateBotPresentToFalseShouldReturnTrueWhenGuildExists() {
        //given
        given(this.guildRepository.findByGuildId(anyString())).willReturn(new Guild());

        //when
        boolean result = this.guildService.updateBotPresentToFalse(anyString());

        //then
        assertThat(result, is(true));
    }

    @Test
    public void updateBotPresentToFalseShouldReturnFalseWhenGuildDoesntExist() {
        //given
        given(this.guildRepository.findByGuildId(anyString())).willReturn(null);

        //when
        boolean result = this.guildService.updateBotPresentToFalse(anyString());

        //then
        assertThat(result, is(false));
    }

    @Test
    public void updateBotPresentToFalseShouldSetThePropertyToFalseWhenGuildExists() {
        //given
        Guild guild = mock(Guild.class);
        given(this.guildRepository.findByGuildId(anyString())).willReturn(guild);

        //when
        this.guildService.updateBotPresentToFalse(anyString());

        //then
        then(guild).should(times(1)).setBotPresent(false);
    }
}
