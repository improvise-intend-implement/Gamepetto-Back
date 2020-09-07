package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.model.GatherEntity;
import com.iii.gamepetto.gamepettobackend.repository.GatherRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GatherServiceImpl implements GatherService {

	private final GatherRepository gatherRepository;
	private final ModelMapper modelMapper;
	private final GuildService guildService;
	private final GameService gameService;

	public GatherServiceImpl(GatherRepository gatherRepository, ModelMapper modelMapper, GuildService guildService, GameService gameService) {
		this.gatherRepository = gatherRepository;
		this.modelMapper = modelMapper;
		this.guildService = guildService;
		this.gameService = gameService;
	}

	@Override
	public void createGather(GatherRequest gatherRequest) {
		GatherEntity gatherEntity = this.modelMapper.map(gatherRequest, GatherEntity.class);
		gatherEntity.setGame(this.gameService.getGameEntity(gatherRequest.getGameId()));
		gatherEntity.setGuild(this.guildService.getGuildEntity(gatherRequest.getGuildId()));
		this.gatherRepository.save(gatherEntity);
	}
}
