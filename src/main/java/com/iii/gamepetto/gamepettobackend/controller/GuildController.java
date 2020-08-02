package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildResponse;
import com.iii.gamepetto.gamepettobackend.service.GuildService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guild")
public class GuildController {

    private final GuildService guildService;

    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuildResponse addGuild(@RequestBody final GuildRequest guildRequest) {
        return this.guildService.saveOrUpdate(guildRequest);
    }
}
