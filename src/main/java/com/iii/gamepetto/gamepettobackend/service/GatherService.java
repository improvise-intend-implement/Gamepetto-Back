package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;

public interface GatherService {

	void createGather(GatherRequest gatherRequest);

	boolean nameExists(String guildId, String name);

	boolean shortNameExists(String guildId, String shortName);

	boolean channelExists(String channelId);
}
