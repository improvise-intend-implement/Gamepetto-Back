package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import com.iii.gamepetto.gamepettobackend.model.MapEntity;
import com.iii.gamepetto.gamepettobackend.transferobject.response.MapResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

@DataJpaTest
public class MapRepositoryIntegrationTest {

	@Autowired
	MapRepository sut;
	@Autowired
	GameRepository gameRepository;
	List<GameEntity> games;
	List<MapEntity> maps;

	@BeforeEach
	void setup() {
		String gameName1 = "Wolfenstein: ET";
		String gameName2 = "Counter Strike: Global Offensive";
		GameEntity gameEntity1 = new GameEntity();
		gameEntity1.setName(gameName1);
		GameEntity gameEntity2 = new GameEntity();
		gameEntity2.setName(gameName2);
		gameEntity1 = this.gameRepository.saveAndFlush(gameEntity1);
		gameEntity2 = this.gameRepository.saveAndFlush(gameEntity2);
		this.games = List.of(gameEntity1, gameEntity2);

		MapEntity mapEntity1 = new MapEntity();
		mapEntity1.setName("Supply");
		mapEntity1.setGame(gameEntity1);
		mapEntity1 = this.sut.saveAndFlush(mapEntity1);
		MapEntity mapEntity2 = new MapEntity();
		mapEntity2.setName("Frostbite");
		mapEntity2.setGame(gameEntity1);
		mapEntity2 = this.sut.saveAndFlush(mapEntity2);
		MapEntity mapEntity3 = new MapEntity();
		mapEntity3.setName("Nuke");
		mapEntity3.setGame(gameEntity2);
		mapEntity3 = this.sut.saveAndFlush(mapEntity3);
		this.maps = List.of(mapEntity1, mapEntity2, mapEntity3);
	}

	@AfterEach
	void tearDown() {
		this.sut.deleteAll();
		this.gameRepository.deleteAll();
	}

	@Test
	void findAllByGameIdAndIdInShouldReturnExpectedList() {
		//given
		Long gameId = this.games.stream().filter(g -> g.getName().equals("Wolfenstein: ET")).findFirst().get().getId();
		Set<Long> mapsIds = this.maps.stream().filter(m -> m.getGame().getId().equals(gameId)).map(MapEntity::getId).collect(Collectors.toSet());
		Set<MapEntity> expected = maps.stream().filter(x -> x.getGame().getId().equals(gameId)).collect(Collectors.toSet());

		//when
		Set<MapEntity> result = this.sut.findAllByGameIdAndIdIn(gameId, mapsIds);

		//then
		assertThat(result, is(expected));
	}

	@Test
	void findAllByGameIdAndIdShouldReturnEmptySetWhenMapsIdsAreForDifferentGame() {
		//given
		Long gameId = this.games.stream().filter(g -> g.getName().equals("Counter Strike: Global Offensive")).findFirst().get().getId();
		Long differentGameId = this.games.stream().filter(g -> g.getName().equals("Wolfenstein: ET")).findFirst().get().getId();
		Set<Long> mapsIds = this.maps.stream().filter(m -> m.getGame().getId().equals(gameId)).map(MapEntity::getId).collect(Collectors.toSet());

		//when
		Set<MapEntity> result = this.sut.findAllByGameIdAndIdIn(differentGameId, mapsIds);

		//then
		assertThat(result, hasSize(0));
	}

	@Test
	void findAllByGameIdShouldReturnExpectedList() {
		//given
		Long gameId = this.games.stream().filter(g -> g.getName().equals("Wolfenstein: ET")).findFirst().get().getId();
		List<MapResponse> expected = this.maps.stream()
				.filter(m -> m.getGame().getId().equals(gameId))
				.map(m -> new MapResponse(m.getId(), m.getName()))
				.collect(Collectors.toList());

		//when
		List<MapResponse> result = this.sut.findAllByGameId(gameId);

		//then
		assertThat(result, samePropertyValuesAs(expected));
	}
}
