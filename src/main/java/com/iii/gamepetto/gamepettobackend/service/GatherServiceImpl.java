package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.exception.GamepettoEntityNotFoundException;
import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import com.iii.gamepetto.gamepettobackend.model.GatherEntity;
import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.repository.GameRepository;
import com.iii.gamepetto.gamepettobackend.repository.GatherRepository;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GatherServiceImpl implements GatherService {

	private final GatherRepository gatherRepository;
	private final GuildRepository guildRepository;
	private final GameRepository gameRepository;
	private final ModelMapper modelMapper;

	public GatherServiceImpl(GatherRepository gatherRepository, GuildRepository guildRepository, GameRepository gameRepository, ModelMapper modelMapper) {
		this.gatherRepository = gatherRepository;
		this.guildRepository = guildRepository;
		this.gameRepository = gameRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	@Transactional
	public void createGather(GatherRequest gatherRequest) {
		GatherEntity gatherEntity = this.modelMapper.map(gatherRequest, GatherEntity.class);
		GameEntity game = this.gameRepository.findById(gatherRequest.getGameId())
				.orElseThrow(() -> new GamepettoEntityNotFoundException("Game entity couldn't be found", "gameId", gatherRequest.getGameId()));
		GuildEntity guild = this.guildRepository.findById(gatherRequest.getGuildId())
				.orElseThrow(() -> new GamepettoEntityNotFoundException("Guild entity couldn't be found", "guildId", gatherRequest.getGuildId()));
		gatherEntity.setGame(game);
		gatherEntity.setGuild(guild);
		this.gatherRepository.save(gatherEntity);
	}

	@Override
	public boolean nameExists(String guildId, String name) {
		return this.gatherRepository.nameExists(guildId, name);
	}

	@Override
	public boolean shortNameExists(String guildId, String shortName) {
		return this.gatherRepository.shortNameExists(guildId, shortName);
	}

	@Override
	public boolean channelExists(String channelId) {
		return this.gatherRepository.channelExists(channelId);
	}
}
