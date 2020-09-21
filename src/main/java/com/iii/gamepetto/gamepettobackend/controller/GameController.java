package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.service.GameService;
import com.iii.gamepetto.gamepettobackend.service.MapService;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GameResponse;
import com.iii.gamepetto.gamepettobackend.transferobject.response.MapResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

	private final GameService gameService;
	private final MapService mapService;

	public GameController(GameService gameService, MapService mapService) {
		this.gameService = gameService;
		this.mapService = mapService;
	}

	@GetMapping
	public ResponseEntity<List<GameResponse>> getAllGames() {
		List<GameResponse> games = this.gameService.findAll();
		return new ResponseEntity<>(games, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/maps")
	public ResponseEntity<List<MapResponse>> getMaps(@PathVariable Long id) {
		List<MapResponse> maps = this.mapService.findAllByGameId(id);
		return new ResponseEntity<>(maps, HttpStatus.OK);
	}
}
