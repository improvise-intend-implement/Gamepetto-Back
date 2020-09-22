package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.repository.MapRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.response.MapResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MapServiceImplTest {

	@Mock
	MapRepository mapRepository;
	@InjectMocks
	MapServiceImpl sut;

	@Test
	void findAllByGameIdShouldCallMapRepositoryMethod() {
		//given
		Long gameId = 1L;

		//when
		this.sut.findAllByGameId(gameId);

		//then
		then(this.mapRepository).should(times(1)).findAllByGameId(gameId);
	}

	@Test
	void findAllByGameIdShouldReturnExpectedList() {
		//given
		Long gameId = 1L;
		List<MapResponse> expected = List.of(new MapResponse(1L, "Supply"), new MapResponse(2L, "Adlernest"));
		given(this.mapRepository.findAllByGameId(gameId))
				.willReturn(expected);

		//when
		List<MapResponse> result = this.sut.findAllByGameId(gameId);

		//then
		assertThat(result, samePropertyValuesAs(expected));
	}
}
