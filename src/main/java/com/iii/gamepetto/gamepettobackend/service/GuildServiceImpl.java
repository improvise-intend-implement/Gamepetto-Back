package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.exception.GamepettoEntityNotFoundException;
import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildPrefix;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public GuildResponse saveOrUpdate(GuildRequest guildRequest) {
        GuildEntity guildEntity = this.guildRepository.findByGuildId(guildRequest.getGuildId()).orElse(null);
        if (guildEntity == null) {
            guildEntity = this.modelMapper.map(guildRequest, GuildEntity.class);
        } else {
            guildEntity.setBotPresent(true);
            guildEntity.setBotPrefix("?");
        }
        guildEntity = this.guildRepository.save(guildEntity);
        return this.modelMapper.map(guildEntity, GuildResponse.class);
    }

    @Override
    @Transactional
    public boolean updateBotPresentToFalse(String guildId) {
        GuildEntity guildEntity = this.guildRepository.findByGuildId(guildId).orElse(null);
        if (guildEntity == null) {
            return false;
        }
        guildEntity.setBotPresent(false);
        this.guildRepository.save(guildEntity);
        return true;
    }

    @Override
    public Map<String, String> getAllPrefixesForBotsInServers() {
        List<GuildPrefix> guildPrefixList = this.guildRepository.findAllByBotPresentIsTrue();
        return guildPrefixList.stream().collect(Collectors.toMap(GuildPrefix::getGuildId, GuildPrefix::getBotPrefix));
    }

	@Override
    @Transactional
	public void updateGuildPrefix(String guildId, String botPrefix) {
		GuildEntity guildEntity = this.guildRepository.findByGuildId(guildId)
                .orElseThrow(() -> new GamepettoEntityNotFoundException("Guild entity couldn't be found", "guildId", guildId));
		guildEntity.setBotPrefix(botPrefix);
		this.guildRepository.save(guildEntity);
	}
}
