package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GatherRepository extends JpaRepository<GatherEntity, Long> {

	@Query(
			value = "SELECT " +
					"	CASE " +
					"		WHEN COUNT(g) > 0 THEN true " +
					"		ELSE false " +
					"	END " +
					"FROM " +
					"	GatherEntity g " +
					"WHERE " +
					"	g.guild.id LIKE :guildId " +
					"	AND g.name LIKE :name "
	)
	boolean nameExists(@Param("guildId") String guildId, @Param("name") String name);

	@Query(
			value = "SELECT " +
					"	CASE " +
					"		WHEN COUNT(g) > 0 THEN true " +
					"		ELSE false " +
					"	END " +
					"FROM " +
					"	GatherEntity g " +
					"WHERE " +
					"	g.guild.id LIKE :guildId " +
					"	AND g.shortName LIKE :shortName "
	)
	boolean shortNameExists(@Param("guildId") String guildId, @Param("shortName") String shortName);

	@Query(
			value = "SELECT " +
					"	CASE " +
					"		WHEN COUNT(g) > 0 THEN true " +
					"		ELSE false " +
					"	END " +
					"FROM " +
					"	GatherEntity g " +
					"WHERE " +
					"	g.channelId LIKE :channelId "
	)
	boolean channelExists(@Param("channelId") String channelId);
}
