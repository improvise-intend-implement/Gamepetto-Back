package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.repository.MapRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.response.MapResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

	private final MapRepository mapRepository;

	public MapServiceImpl(MapRepository mapRepository) {
		this.mapRepository = mapRepository;
	}

	@Override
	public List<MapResponse> findAllByGameId(Long gameId) {
		return this.mapRepository.findAllByGameId(gameId);
	}
}
