package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.transferobject.response.GameResponse;

import java.util.List;

public interface GameService {

	List<GameResponse> findAll();
}
