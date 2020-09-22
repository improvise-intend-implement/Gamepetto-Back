package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.repository.GameRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GameResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

	@Mock
	GameRepository gameRepository;
	@InjectMocks
	GameServiceImpl sut;

	@Test
	void findAllShouldReturnExpectedListWithItemsFromTheRepository() {
		//given
		List<GameResponse> expected = List.of(
				new GameResponse(1L, "Wolfenstein: ET"),
				new GameResponse(2L, "League of Legends")
		);
		given(this.gameRepository.findAllBy()).willReturn(expected);

		//when
		List<GameResponse> result = this.sut.findAll();

		//then
		assertThat(result, samePropertyValuesAs(expected));
	}

	@Test
	void findAllShouldReturnEmptyListWhenThereAreNoItemsInRepository() {
		//given
		given(this.gameRepository.findAllBy()).willReturn(new ArrayList<>());

		//when
		List<GameResponse> result = this.sut.findAll();

		//then
		assertThat(result, hasSize(0));
	}
}
