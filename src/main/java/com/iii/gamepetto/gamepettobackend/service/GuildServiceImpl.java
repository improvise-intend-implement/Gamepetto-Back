package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.request.GuildRequest;
import org.springframework.stereotype.Service;

@Service
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;

    public GuildServiceImpl(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    @Override
    public void saveOrUpdate(GuildRequest guildRequest) {
        Guild guild = this.guildRepository.findByGuildId(guildRequest.getGuildId());
        if (guild == null) {
            guild = new Guild();
            guild.setGuildId(guildRequest.getGuildId());
            guild.setName(guildRequest.getName());
            guild.setIcon(guildRequest.getIcon());
        } else {
            guild.setBotPresent(true);
        }
        this.guildRepository.save(guild);
    }
}
