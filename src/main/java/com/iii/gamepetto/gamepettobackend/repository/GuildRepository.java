package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildPrefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {

    Guild findByGuildId(String guildId);

    List<GuildPrefix> findAllByBotPresentIsTrue();
}
