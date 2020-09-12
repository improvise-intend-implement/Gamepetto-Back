package com.iii.gamepetto.gamepettobackend.validator;

import com.iii.gamepetto.gamepettobackend.service.GatherService;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GatherRequestValidator implements Validator {

	private final GatherService gatherService;

	public GatherRequestValidator(GatherService gatherService) {
		this.gatherService = gatherService;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return GatherRequest.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		GatherRequest gather = (GatherRequest) o;

		if (gather.getGuildId() == null || gather.getGuildId().isBlank()) {
			errors.rejectValue("guildId", "validator.Guild.id.empty");
		}

		if (gather.getName() == null || gather.getName().isBlank()) {
			errors.rejectValue("name", "validator.Gather.name.empty");
		} else if (gather.getName().length() > 128) {
			errors.rejectValue("name", "validator.Gather.name.length.max");
		} else if (this.gatherService.nameExists(gather.getGuildId(), gather.getName())) {
			errors.rejectValue("name", "validator.Gather.name.exists");
		}

		if (gather.getShortName() == null || gather.getShortName().isBlank()) {
			errors.rejectValue("shortName", "validator.Gather.shortName.empty");
		} else if (gather.getShortName().length() > 16) {
			errors.rejectValue("shortName", "validator.Gather.shortName.length.max");
		} else if (this.gatherService.shortNameExists(gather.getGuildId(), gather.getShortName())) {
			errors.rejectValue("shortName", "validator.Gather.shortName.exists");
		}

		if (gather.getChannelId() == null || gather.getChannelId().isBlank()) {
			errors.rejectValue("channelId", "validator.Gather.channelId.empty");
		} else if (this.gatherService.channelExists(gather.getChannelId())) {
			errors.rejectValue("channelId", "validator.Gather.channelId.exists");
		}

		if (gather.getPlayersPerTeam() == null) {
			errors.rejectValue("playersPerTeam", "validator.Gather.playersPerTeam.empty");
		} else if (gather.getPlayersPerTeam() < 1 || gather.getPlayersPerTeam() > 100) {
			errors.rejectValue("playersPerTeam", "validator.Gather.playersPerTeam.value.range");
		}

		if (gather.getMapsNumber() == null) {
			errors.rejectValue("mapsNumber", "validator.Gather.mapsNumber.empty");
		} else if (gather.getMapsNumber() < 1 || gather.getMapsNumber() > 10) {
			errors.rejectValue("mapsNumber", "validator.Gather.mapsNumber.value.range");
		}

		if (gather.getGameId() == null) {
			errors.rejectValue("gameId", "validator.Gather.gameId.empty");
		}

		if (gather.getMapsRandom() == null) {
			errors.rejectValue("mapsRandom", "validator.Gather.mapsRandom.empty");
		}

		if (gather.getCaptainRolePriority() == null) {
			errors.rejectValue("captainRolePriority", "validator.Gather.captainRolePriority.empty");
		}

		if (gather.getAllAllowed() == null) {
			errors.rejectValue("allAllowed", "validator.Gather.allAllowed.empty");
		}
	}
}
