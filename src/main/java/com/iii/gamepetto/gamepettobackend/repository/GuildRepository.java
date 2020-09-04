package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildPrefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuildRepository extends JpaRepository<GuildEntity, Long> {

    GuildEntity findByGuildId(String guildId);

    List<GuildPrefix> findAllByBotPresentIsTrue();
}
