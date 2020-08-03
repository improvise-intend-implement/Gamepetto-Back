package com.iii.gamepetto.gamepettobackend.service;

import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildResponse;

public interface GuildService {

    GuildResponse saveOrUpdate(GuildRequest guildRequest);

}
