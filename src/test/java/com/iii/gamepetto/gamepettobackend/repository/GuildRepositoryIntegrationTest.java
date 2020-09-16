package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildPrefix;
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
import static org.hamcrest.core.Is.is;

@DataJpaTest
class GuildRepositoryIntegrationTest {

	@Autowired
	GuildRepository sut;

	@BeforeEach
	void setup() {
		String guildId1 = "1";
		String name1 = "GamepettoS";
		GuildEntity guildEntity1 = new GuildEntity();
		guildEntity1.setId(guildId1);
		guildEntity1.setName(name1);

		String guildId2 = "2";
		String name2 = "Test2";
		boolean isBotPresent2 = false;
		GuildEntity guildEntity2 = new GuildEntity();
		guildEntity2.setId(guildId2);
		guildEntity2.setName(name2);
		guildEntity2.setBotPresent(isBotPresent2);

		String guildId3 = "3";
		String name3 = "Test3";
		GuildEntity guildEntity3 = new GuildEntity();
		guildEntity3.setId(guildId3);
		guildEntity3.setName(name3);

		List<GuildEntity> guildsToSave = List.of(guildEntity1, guildEntity2, guildEntity3);

		this.sut.saveAll(guildsToSave);
		this.sut.flush();
	}

	@AfterEach
	void tearDown() {
		this.sut.deleteAll();
	}

	@Test
	void findByIdShouldReturnGuildWhenExistsInDb() {
		//given
		//when
		GuildEntity result = this.sut.findById("1").orElse(null);

		//then
		Assertions.assertNotNull(result);
		assertThat(result.getId(), not(nullValue()));
		assertThat(result.getName(), not(emptyOrNullString()));
	}

	@Test
	void findAllByBotPresentIsTrueShouldReturnGuildPrefixListWithoutBotPresentFalseValues() {
		//given
		//guilds saved in setup()

		//when
		List<GuildPrefix> result = this.sut.findAllByBotPresentIsTrue();

		//then
		assertThat(result.size(), is(2));
	}

	@Test
	void addedGuildHasDefaultPrefixSet() {
		//given
		String defaultPrefix = "?";

		//when
		String result = this.sut.findById("1").map(GuildEntity::getBotPrefix).orElse(null);

		//then
		assertThat(result, is(defaultPrefix));
	}

}
