package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.repository.GameRepository;
import com.iii.gamepetto.gamepettobackend.repository.GatherRepository;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

class GatherServiceImplTest {

	@Mock
	GatherRepository gatherRepository;
	@Mock
	GuildRepository guildRepository;
	@Mock
	GameRepository gameRepository;
	@Spy
	ModelMapper modelMapper;
	@InjectMocks
	GatherServiceImpl sut;

	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void channelExistsShouldCallGatherRepositoryMethod() {
		//given
		//when
		this.sut.channelExists("12321");

		//then
		then(this.gatherRepository).should(times(1)).channelExists(anyString());
	}

	@Test
	void channelExistsShouldReturnFalseWhenRepositoryMethodReturnsFalse() {
		//given
		boolean exists = false;
		String channelId = "12321";
		given(this.gatherRepository.channelExists(channelId)).willReturn(exists);

		//when
		boolean result = this.sut.channelExists(channelId);

		//then
		assertThat(result, is(exists));
	}

	@Test
	void channelExistsShouldReturnTrueWhenRepositoryMethodReturnsTrue() {
		//given
		boolean exists = true;
		String channelId = "123";
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

	@Test
	void shortNameExistsShouldReturnFalseWhenRepositoryMethodReturnsFalse() {
		//given
		boolean exists = false;
		String guildId = "12321";
		String shortName = "shortie";
		given(this.gatherRepository.shortNameExists(guildId, shortName)).willReturn(exists);

		//when
		boolean result = this.sut.shortNameExists(guildId, shortName);

		//then
		assertThat(result, is(exists));
	}

	@Test
	void shortNameExistsShouldReturnTrueWhenRepositoryMethodReturnsTrue() {
		//given
		boolean exists = true;
		String guildId = "12321";
		String shortName = "shortie";
		given(this.gatherRepository.shortNameExists(guildId, shortName)).willReturn(exists);

		//when
		boolean result = this.sut.shortNameExists(guildId, shortName);

		//then
		assertThat(result, is(exists));
	}

	//TODO: more tests, check if one can parameterize tests
}
