package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.transferobject.response.MapResponse;

import java.util.List;

public interface MapService {

	List<MapResponse> findAllByGameId(Long gameId);
}
