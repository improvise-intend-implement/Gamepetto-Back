package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.response.GuildResponse;
import org.springframework.stereotype.Service;

@Service
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;

    public GuildServiceImpl(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    @Override
    public GuildResponse saveOrUpdate(GuildRequest guildRequest) {
        Guild guild = this.guildRepository.findByGuildId(guildRequest.getGuildId());
        if (guild == null) {
            guild = convertGuildRequestToGuild(guildRequest);
        } else {
            guild.setBotPresent(true);
        }
        guild = this.guildRepository.save(guild);
        return convertGuildToGuildResponse(guild);
    }

    private Guild convertGuildRequestToGuild(GuildRequest guildRequest) {
        Guild guild = new Guild();
        guild.setGuildId(guildRequest.getGuildId());
        guild.setName(guildRequest.getName());
        guild.setIcon(guildRequest.getIcon());
        return guild;
    }

    private GuildResponse convertGuildToGuildResponse(Guild guild) {
        GuildResponse response = new GuildResponse();
        response.setId(guild.getId());
        response.setGuildId(guild.getGuildId());
        response.setBotPrefix(guild.getBotPrefix());
        return response;
    }
}
