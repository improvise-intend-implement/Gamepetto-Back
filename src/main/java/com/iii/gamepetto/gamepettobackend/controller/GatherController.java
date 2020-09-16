package com.iii.gamepetto.gamepettobackend.controller;

import com.iii.gamepetto.gamepettobackend.service.GatherService;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import com.iii.gamepetto.gamepettobackend.validator.GatherRequestValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/gathers")
public class GatherController {

	private final GatherService gatherService;

	public GatherController(GatherService gatherService) {
		this.gatherService = gatherService;
	}

	@InitBinder("gatherRequest")
	public void initGatherRequestBinder(WebDataBinder binder) {
		binder.addValidators(new GatherRequestValidator(this.gatherService));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createGather(@Valid @RequestBody final GatherRequest gatherRequest) {
		this.gatherService.createGather(gatherRequest);
	}

}
