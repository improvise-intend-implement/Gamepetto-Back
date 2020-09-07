package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.service.GatherService;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gathers")
public class GatherController {

	private final GatherService gatherService;

	public GatherController(GatherService gatherService) {
		this.gatherService = gatherService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createGather(@RequestBody final GatherRequest gatherRequest) {
		this.gatherService.createGather(gatherRequest);
	}

}
