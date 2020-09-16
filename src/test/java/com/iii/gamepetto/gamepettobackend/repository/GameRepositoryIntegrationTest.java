package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

@DataJpaTest
class GameRepositoryIntegrationTest {

	@Autowired
	GameRepository sut;

	@BeforeEach
	void setup() {
		GameEntity gameEntity1 = new GameEntity();
		gameEntity1.setId(1L);
		gameEntity1.setName("Wolfenstein: ET");

		GameEntity gameEntity2 = new GameEntity();
		gameEntity2.setId(2L);
		gameEntity2.setName("Counter Strike: Global Offensive");

		List<GameEntity> gamesToSave = List.of(gameEntity1, gameEntity2);

		this.sut.saveAll(gamesToSave);
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
		GameEntity result = this.sut.findById(1L).orElse(null);

		//then
		Assertions.assertNotNull(result);
		assertThat(result.getId(), not(nullValue()));
		assertThat(result.getName(), not(emptyOrNullString()));
	}
}
