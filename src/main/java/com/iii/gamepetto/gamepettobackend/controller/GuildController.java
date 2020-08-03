package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.service.GuildService;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.GuildResponse;
import com.iii.gamepetto.gamepettobackend.validator.GuildRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/guild")
public class GuildController {

    private final GuildService guildService;

    public GuildController(GuildService guildService) {
        this.guildService = guildService;
    }

    @InitBinder("guildRequest")
    public void initGuildRequestBinder(WebDataBinder binder) {
        binder.addValidators(new GuildRequestValidator());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuildResponse addGuild(@Valid @RequestBody final GuildRequest guildRequest) {
        return this.guildService.saveOrUpdate(guildRequest);
    }
}
