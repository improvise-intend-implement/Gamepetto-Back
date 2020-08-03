package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;
    private final ModelMapper modelMapper;

    public GuildServiceImpl(GuildRepository guildRepository, ModelMapper modelMapper) {
        this.guildRepository = guildRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GuildResponse saveOrUpdate(GuildRequest guildRequest) {
        Guild guild = this.guildRepository.findByGuildId(guildRequest.getGuildId());
        if (guild == null) {
            guild = modelMapper.map(guildRequest, Guild.class);
        } else {
            guild.setBotPresent(true);
        }
        guild = this.guildRepository.save(guild);
        return modelMapper.map(guild, GuildResponse.class);
    }

}
