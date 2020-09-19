package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.exception.GamepettoEntityNotFoundException;
import com.iii.gamepetto.gamepettobackend.model.GameEntity;
import com.iii.gamepetto.gamepettobackend.model.GatherEntity;
import com.iii.gamepetto.gamepettobackend.model.GuildEntity;
import com.iii.gamepetto.gamepettobackend.model.MapEntity;
import com.iii.gamepetto.gamepettobackend.repository.GameRepository;
import com.iii.gamepetto.gamepettobackend.repository.GatherRepository;
import com.iii.gamepetto.gamepettobackend.repository.GuildRepository;
import com.iii.gamepetto.gamepettobackend.repository.MapRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GatherServiceImpl implements GatherService {

	private final GatherRepository gatherRepository;
	private final GuildRepository guildRepository;
	private final GameRepository gameRepository;
	private final MapRepository mapRepository;
	private final ModelMapper modelMapper;

	public GatherServiceImpl(GatherRepository gatherRepository, GuildRepository guildRepository, GameRepository gameRepository, MapRepository mapRepository, ModelMapper modelMapper) {
		this.gatherRepository = gatherRepository;
		this.guildRepository = guildRepository;
		this.gameRepository = gameRepository;
		this.mapRepository = mapRepository;
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
		gatherEntity.setMaps(findMaps(gatherRequest.getGameId(), gatherRequest.getMapsIds()));
		this.gatherRepository.save(gatherEntity);
	}

	private Set<MapEntity> findMaps(Long gameId, Set<Long> mapsIds) {
		Set<MapEntity> maps = this.mapRepository.findAllByGameIdAndIdIn(gameId, mapsIds);
		if (maps.size() != mapsIds.size()) {
			Set<Long> mapsIdsFound = maps.stream().map(MapEntity::getId).collect(Collectors.toSet());
			Set<Long> mapsIdsCopy = new HashSet<>(mapsIds);
			mapsIdsCopy.removeAll(mapsIdsFound);
			throw new GamepettoEntityNotFoundException("Not all provided maps couldn't be found", "mapsIds", mapsIdsCopy);
		}
		return maps;
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
