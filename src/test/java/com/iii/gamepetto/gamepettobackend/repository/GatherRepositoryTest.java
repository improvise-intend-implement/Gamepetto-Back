package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import com.iii.gamepetto.gamepettobackend.model.GatherEntity;
import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DataJpaTest
class GatherRepositoryTest {

	@Autowired
	GameRepository gameRepository;
	@Autowired
	GuildRepository guildRepository;
	@Autowired
	GatherRepository sut;

	@BeforeEach
	void setUp() {
		GameEntity gameEntity1 = new GameEntity();
		gameEntity1.setId(1L);
		gameEntity1.setName("Wolfenstein: ET");
		gameEntity1 = this.gameRepository.save(gameEntity1);

		GuildEntity guildEntity1 = new GuildEntity();
		guildEntity1.setId("1");
		guildEntity1.setName("Gamepetto");
		guildEntity1 = this.guildRepository.saveAndFlush(guildEntity1);

		GatherEntity gatherEntity1 = new GatherEntity();
		gatherEntity1.setId(1L);
		gatherEntity1.setAllAllowed(true);
		gatherEntity1.setCaptainRolePriority(true);
		gatherEntity1.setMapsRandom(true);
		gatherEntity1.setMapsNumber(5);
		gatherEntity1.setPlayersPerTeam(6);
		gatherEntity1.setName("first gather");
		gatherEntity1.setShortName("first");
		gatherEntity1.setGame(gameEntity1);
		gatherEntity1.setGuild(guildEntity1);
		gatherEntity1.setChannelId("1");

		List<GatherEntity> gathersToSave = List.of(gatherEntity1);

		this.sut.saveAll(gathersToSave);
		this.sut.flush();
	}

	@AfterEach
	void tearDown() {
		this.gameRepository.deleteAll();
		this.guildRepository.deleteAll();
		this.sut.deleteAll();
	}

	@Test
	void nameExistsShouldReturnFalseWhenThereIsNoGatherWithPassedNameInSpecificGuild() {
		//given
		//when
		boolean result = this.sut.nameExists("1", "dont create such name");

		//then
		assertThat(result, is(false));
	}

	@Test
	void nameExistsShouldReturnTrueWhenThereIsGatherWithPassedNameInSpecificGuild() {
		//given
		//when
		boolean result = this.sut.nameExists("1", "first gather");

		//then
		assertThat(result, is(true));
	}

	@Test
	void shortNameExistsShouldReturnFalseWhenThereIsNoGatherWithPassedShortNameInSpecificGuild() {
		//given
		//when
		boolean result = this.sut.shortNameExists("1", "nonexistent");

		//then
		assertThat(result, is(false));
	}

	@Test
	void shortNameExistsShouldReturnTrueWhenThereIsGatherWithPassedShortNameInSpecificGuild() {
		//given
		//when
		boolean result = this.sut.shortNameExists("1", "first");

		//then
		assertThat(result, is(true));
	}

	@Test
	void channelExistsShouldReturnTrueWhenThereIsAlreadyRecordWithProvidedChannelId() {
		//given
		//when
		boolean result = this.sut.channelExists("1");

		//then
		assertThat(result, is(true));
	}

	@Test
	void channelExistsShouldReturnFalseWhenThereIsNoRecordWithProvidedChannelId() {
		//given
		//when
		boolean result = this.sut.channelExists("2132123123142312312");

		//then
		assertThat(result, is(false));
	}
}
