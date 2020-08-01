package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.response.GuildResponse;

public interface GuildService {

    GuildResponse saveOrUpdate(GuildRequest guildRequest);

}