package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildPrefix;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.isNull;

@DataJpaTest
class GuildRepositoryIntegrationTest {

	@Autowired
	GuildRepository sut;

	@BeforeEach
	void setup() {
		Long id1 = 1L;
		String guildId1 = "1";
		String name1 = "GamepettoS";
		Guild guild1 = new Guild();
		guild1.setId(id1);
		guild1.setGuildId(guildId1);
		guild1.setName(name1);

		Long id2 = 2L;
		String guildId2 = "2";
		String name2 = "Test2";
		boolean isBotPresent2 = false;
		Guild guild2 = new Guild();
		guild2.setId(id2);
		guild2.setGuildId(guildId2);
		guild2.setName(name2);
		guild2.setBotPresent(isBotPresent2);

		Long id3 = 3L;
		String guildId3 = "3";
		String name3 = "Test3";
		Guild guild3 = new Guild();
		guild3.setId(id3);
		guild3.setGuildId(guildId3);
		guild3.setName(name3);

		List<Guild> guildsToSave = List.of(guild1, guild2, guild3);

		this.sut.saveAll(guildsToSave);
	}

	@AfterEach
	void tearDown() {
		this.sut.deleteAll();
	}

	@Test
	void findByGuildIdShouldReturnGuildWhenExistsInDb() {
		//given
		//when
		Guild result = this.sut.findByGuildId("1");

		//then
		Assertions.assertNotNull(result);
		assertThat(result.getId(), not(nullValue()));
		assertThat(result.getGuildId(), not(emptyOrNullString()));
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

}
