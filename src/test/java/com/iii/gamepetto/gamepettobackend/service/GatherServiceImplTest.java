package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.exception.GamepettoEntityNotFoundException;
import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import com.iii.gamepetto.gamepettobackend.model.GatherEntity;
import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.model.MapEntity;
import com.iii.gamepetto.gamepettobackend.repository.GameRepository;
import com.iii.gamepetto.gamepettobackend.repository.GatherRepository;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.repository.MapRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class GatherServiceImplTest {

	@Mock
	GatherRepository gatherRepository;
	@Mock
	GuildRepository guildRepository;
	@Mock
	GameRepository gameRepository;
	@Mock
	MapRepository mapRepository;
	@Spy
	ModelMapper modelMapper;
	@InjectMocks
	GatherServiceImpl sut;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		this.modelMapper.getConfiguration()
				.setMatchingStrategy(MatchingStrategies.STRICT)
				.setAmbiguityIgnored(true);
	}

	@Test
	void channelExistsShouldCallGatherRepositoryMethod() {
		//given
		//when
		this.sut.channelExists("12321");

		//then
		then(this.gatherRepository).should(times(1)).channelExists(anyString());
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void channelExistsShouldReturnSameBooleanAsRepositoryMethod(boolean exists) {
		//given
		String channelId = "12321";
		given(this.gatherRepository.channelExists(channelId)).willReturn(exists);

		//when
		boolean result = this.sut.channelExists(channelId);

		//then
		assertThat(result, is(exists));
	}

	@Test
	void shortNameExistsShouldCallGatherRepositoryMethod() {
		//given
		//when
		this.sut.shortNameExists("12321312", "shortie");

		//then
		then(this.gatherRepository).should(times(1)).shortNameExists(anyString(), anyString());
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void shortNameExistsShouldReturnSameBooleanAsRepositoryMethod(boolean exists) {
		//given
		String guildId = "12321";
		String shortName = "shortie";
		given(this.gatherRepository.shortNameExists(guildId, shortName)).willReturn(exists);

		//when
		boolean result = this.sut.shortNameExists(guildId, shortName);

		//then
		assertThat(result, is(exists));
	}

	@Test
	void nameExistsShouldCallGatherRepositoryMethod() {
		//given
		//when
		this.sut.nameExists("321231", "shortiee");

		//then
		then(this.gatherRepository).should(times(1)).nameExists(anyString(), anyString());
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true})
	void nameExistsShouldReturnSameBooleanAsRepositoryMethod(boolean exists) {
		//given
		String guildId = "123";
		String name = "name ski";
		given(this.gatherRepository.nameExists(guildId, name)).willReturn(exists);

		//when
		boolean result = this.sut.nameExists(guildId, name);

		//then
		assertThat(result, is(exists));
	}

	@Test
	void createGatherShouldThrowExceptionWhenGameEntityDoesntExist() {
		assertThrows(GamepettoEntityNotFoundException.class, () -> {
			//given
			GatherRequest gatherRequest = new GatherRequest();
			gatherRequest.setGameId(1L);
			gatherRequest.setGuildId("213231");
			given(this.guildRepository.findById(anyString())).willReturn(Optional.of(new GuildEntity()));
			given(this.gameRepository.findById(anyLong())).willReturn(Optional.empty());

			//when
			this.sut.createGather(gatherRequest);
		});
	}

	@Test
	void createGatherShouldThrowExceptionWhenGuildEntityDoesntExist() {
		assertThrows(GamepettoEntityNotFoundException.class, () -> {
			//given
			GatherRequest gatherRequest = new GatherRequest();
			gatherRequest.setGameId(1L);
			gatherRequest.setGuildId("213231");
			given(this.gameRepository.findById(anyLong())).willReturn(Optional.of(new GameEntity()));
			given(this.guildRepository.findById(anyString())).willReturn(Optional.empty());

			//when
			this.sut.createGather(gatherRequest);
		});
	}

	@Test
	void createGatherShouldThrowExceptionWhenSizeOfMapsIdsDoesntMatchSizeOfFoundMapEntities() {
		assertThrows(GamepettoEntityNotFoundException.class, () -> {
			//given
			GatherRequest gatherRequest = new GatherRequest();
			gatherRequest.setGameId(1L);
			gatherRequest.setGuildId("213231");
			gatherRequest.setMapsIds(Set.of(1L, 2L));
			MapEntity mapEntity1 = new MapEntity();
			mapEntity1.setId(1L);
			given(this.gameRepository.findById(anyLong())).willReturn(Optional.of(new GameEntity()));
			given(this.guildRepository.findById(anyString())).willReturn(Optional.empty());
			given(this.mapRepository.findAllByGameIdAndIdIn(gatherRequest.getGameId(), gatherRequest.getMapsIds())).willReturn(Set.of(mapEntity1));

			//when
			this.sut.createGather(gatherRequest);
		});
	}

	@Test
	void createGatherShouldCallGatherRepositorySaveMethod() {
		//given
		GatherRequest gatherRequest = new GatherRequest();
		gatherRequest.setGameId(1L);
		gatherRequest.setGuildId("123123");
		gatherRequest.setMapsIds(Set.of(1L, 2L));
		given(this.gameRepository.findById(gatherRequest.getGameId())).willReturn(Optional.of(new GameEntity()));
		given(this.guildRepository.findById(gatherRequest.getGuildId())).willReturn(Optional.of(new GuildEntity()));
		given(this.mapRepository.findAllByGameIdAndIdIn(gatherRequest.getGameId(), gatherRequest.getMapsIds())).willReturn(Set.of(new MapEntity(), new MapEntity()));

		//when
		this.sut.createGather(gatherRequest);

		//then
		then(this.gatherRepository).should(times(1)).save(any(GatherEntity.class));
	}

	@Test
	void createGatherShouldSetAppropriateFieldsBesidesModelMapper() {
		//given
		GatherEntity gatherEntity = mock(GatherEntity.class);
		GatherRequest gatherRequest = new GatherRequest();
		gatherRequest.setGameId(1L);
		gatherRequest.setGuildId("123123");
		gatherRequest.setMapsIds(Set.of(1L, 2L));
		given(this.modelMapper.map(gatherRequest, GatherEntity.class)).willReturn(gatherEntity);
		given(this.gameRepository.findById(gatherRequest.getGameId())).willReturn(Optional.of(new GameEntity()));
		given(this.guildRepository.findById(gatherRequest.getGuildId())).willReturn(Optional.of(new GuildEntity()));
		given(this.mapRepository.findAllByGameIdAndIdIn(gatherRequest.getGameId(), gatherRequest.getMapsIds())).willReturn(Set.of(new MapEntity(), new MapEntity()));

		//when
		this.sut.createGather(gatherRequest);

		//then
		then(gatherEntity).should(times(1)).setGuild(any(GuildEntity.class));
		then(gatherEntity).should(times(1)).setGame(any(GameEntity.class));
		then(gatherEntity).should(times(1)).setMaps(anySet());
	}
}
