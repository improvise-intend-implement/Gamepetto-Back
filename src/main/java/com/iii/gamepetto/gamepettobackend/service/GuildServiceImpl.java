package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.exception.GamepettoEntityNotFoundException;
import com.iii.gamepetto.gamepettobackend.model.Guild;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildPrefix;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            guild = this.modelMapper.map(guildRequest, Guild.class);
        } else {
            guild.setBotPresent(true);
        }
        guild = this.guildRepository.save(guild);
        return this.modelMapper.map(guild, GuildResponse.class);
    }

    @Override
    public boolean updateBotPresentToFalse(String guildId) {
        Guild guild = this.guildRepository.findByGuildId(guildId);
        if (guild == null) {
            return false;
        }
        guild.setBotPresent(false);
        return true;
    }

    @Override
    public Map<String, String> getAllPrefixesForBotsInServers() {
        List<GuildPrefix> guildPrefixList = this.guildRepository.findAllByBotPresentIsTrue();
        return guildPrefixList.stream().collect(Collectors.toMap(GuildPrefix::getGuildId, GuildPrefix::getBotPrefix));
    }

	@Override
	public void updateGuildPrefix(String guildId, String botPrefix) {
		Guild guild = this.guildRepository.findByGuildId(guildId);
		if (guild == null) {
		    throw new GamepettoEntityNotFoundException("Guild entity couldn't be found", "guildId", guildId);
        }
		guild.setBotPrefix(botPrefix);
		this.guildRepository.save(guild);
	}
}
