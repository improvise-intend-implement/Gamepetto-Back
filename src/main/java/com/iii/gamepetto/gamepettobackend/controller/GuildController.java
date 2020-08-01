package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.request.GuildRequest;
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
    public void addGuild(@RequestBody final GuildRequest guildRequest) {
        this.guildService.saveOrUpdate(guildRequest);
    }
}
