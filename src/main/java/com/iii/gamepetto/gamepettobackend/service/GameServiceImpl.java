package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.repository.GameRepository;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GameResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

	private final GameRepository gameRepository;

	public GameServiceImpl(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	@Override
	public List<GameResponse> findAll() {
		return this.gameRepository.findAllBy();
	}
}
