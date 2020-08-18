package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.service.GuildService;
import com.iii.gamepetto.gamepettobackend.transferobject.request.BotPrefix;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import com.iii.gamepetto.gamepettobackend.transferobject.response.GuildResponse;
import com.iii.gamepetto.gamepettobackend.validator.BotPrefixValidator;
import com.iii.gamepetto.gamepettobackend.validator.GuildRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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

    @InitBinder("botPrefix")
    public void initGuildPrefixBinder(WebDataBinder binder) {
        binder.addValidators(new BotPrefixValidator());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuildResponse addGuild(@Valid @RequestBody final GuildRequest guildRequest) {
        return this.guildService.saveOrUpdate(guildRequest);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> removeGuild(@PathVariable String id) {
        boolean isRemoved = this.guildService.updateBotPresentToFalse(id);
        if (!isRemoved) {
            return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/prefix")
    public ResponseEntity<Map<String, String>> getAllActivePrefixes() {
        Map<String, String> prefixesMap = this.guildService.getAllPrefixesForBotsInServers();
        return new ResponseEntity<>(prefixesMap, HttpStatus.OK);
    }

    @PatchMapping(value = "{id}/prefix")
    public ResponseEntity<?> updateGuildPrefix(@PathVariable String id, @Valid @RequestBody final BotPrefix botPrefix) {
        this.guildService.updateGuildPrefix(id, botPrefix.getBotPrefix());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
