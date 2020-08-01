package com.iii.gamepetto.gamepettobackend.repository;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuildRepository extends JpaRepository<Guild, Long> {

    Guild findByGuildId(String guildId);

}
