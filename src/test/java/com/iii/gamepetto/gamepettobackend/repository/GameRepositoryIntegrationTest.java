package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GameResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
class GameRepositoryIntegrationTest {

	@Autowired
	GameRepository sut;
	List<GameEntity> games;

	@BeforeEach
	void setup() {
		GameEntity gameEntity1 = new GameEntity();
		gameEntity1.setName("Wolfenstein: ET");

		GameEntity gameEntity2 = new GameEntity();
		gameEntity2.setName("Counter Strike: Global Offensive");

		List<GameEntity> gamesToSave = List.of(gameEntity1, gameEntity2);

		this.games = this.sut.saveAll(gamesToSave);
		this.sut.flush();
	}

	@AfterEach
	void tearDown() {
		this.sut.deleteAll();
	}

	@Test
	void findByIdShouldReturnGameWhenExistsInDb() {
		//given
		//when
		GameEntity result = this.sut.findById(this.games.get(0).getId()).orElse(null);

		//then
		Assertions.assertNotNull(result);
		assertThat(result.getId(), not(nullValue()));
		assertThat(result.getName(), not(emptyOrNullString()));
	}

	@Test
	void findAllShouldReturnExpectedList() {
		//given
		List<GameResponse> expected = this.games.stream()
				.map(g -> new GameResponse(g.getId(), g.getName()))
				.collect(Collectors.toList());

		//when
		List<GameResponse> result = this.sut.findAllBy();

		//then
		assertThat(result, samePropertyValuesAs(expected));
	}

	@Test
	void findAllShouldReturnEmptyListWhenRepositoryIsEmpty() {
		//given
		this.sut.deleteAll();

		//when
		List<GameResponse> result = this.sut.findAllBy();

		//then
		assertThat(result, hasSize(0));
	}
}
