package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildResponse;

import java.util.Map;

public interface GuildService {

    GuildResponse saveOrUpdate(GuildRequest guildRequest);

    boolean updateBotPresentToFalse(String guildId);

    Map<String, String> getAllPrefixesForBotsInServers();

    void updateGuildPrefix(String guildId, String botPrefix);
}
