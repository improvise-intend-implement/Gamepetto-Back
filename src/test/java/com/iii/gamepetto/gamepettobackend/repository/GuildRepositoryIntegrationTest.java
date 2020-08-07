package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@DataJpaTest
public class GuildRepositoryIntegrationTest {

	@Autowired
	private GuildRepository sut;

	@Test
	public void findByGuildIdShouldReturnGuildWhenExistsInDb() {
		//given
		Long id = 1L;
		String guildId = "12345";
		String name = "GamepettoS";
		Guild guild = new Guild();
		guild.setId(id);
		guild.setGuildId(guildId);
		guild.setName(name);
		sut.save(guild);

		//when
		Guild result = sut.findByGuildId(guildId);

		//then
		Assertions.assertNotNull(result);
		assertThat(result.getId(), is(id));
		assertThat(result.getGuildId(), is(guildId));
		assertThat(result.getName(), is(name));
	}

}
