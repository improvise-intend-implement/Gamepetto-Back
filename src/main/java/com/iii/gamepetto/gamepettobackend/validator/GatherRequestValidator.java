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

		this.validateGuildId(errors, gather.getGuildId());
		this.validateName(errors, gather.getGuildId(), gather.getName());
		this.validateShortName(errors, gather.getGuildId(), gather.getShortName());
		this.validateChannelId(errors, gather.getChannelId());
		this.validatePlayersPerTeam(errors, gather.getPlayersPerTeam());
		this.validateMapsNumber(errors, gather.getMapsNumber());
		this.validateGameId(errors, gather.getGameId());
		this.validateMapsRandom(errors, gather.getMapsRandom());
		this.validateCaptainRolePriority(errors, gather.getCaptainRolePriority());
		this.validateAllAllowed(errors, gather.getAllAllowed());
	}

	private void validateGuildId(Errors errors, String guildId) {
		if (guildId == null || guildId.isBlank()) {
			errors.rejectValue("guildId", "validator.Guild.id.empty");
		}
	}

	private void validateName(Errors errors, String guildId, String name) {
		if (name == null || name.isBlank()) {
			errors.rejectValue("name", "validator.Gather.name.empty");
		} else if (name.length() > 128) {
			errors.rejectValue("name", "validator.Gather.name.length.max");
		} else if (name.startsWith(" ") || name.endsWith(" ")) {
			errors.rejectValue("name", "validator.Gather.name.whiteSpaces.startEnd");
		} else if (this.gatherService.nameExists(guildId, name)) {
			errors.rejectValue("name", "validator.Gather.name.exists");
		}
	}

	private void validateShortName(Errors errors, String guildId, String shortName) {
		if (shortName == null || shortName.isBlank()) {
			errors.rejectValue("shortName", "validator.Gather.shortName.empty");
		} else if (shortName.length() > 16) {
			errors.rejectValue("shortName", "validator.Gather.shortName.length.max");
		} else if (shortName.matches("^[0-9]+$")) {
			errors.rejectValue("shortName", "validator.Gather.shortName.onlyNumbers");
		} else if (shortName.startsWith("-") || shortName.startsWith("#")) {
			errors.rejectValue("shortName", "validator.Gather.shortName.illegalCharacters");
		} else if (shortName.contains(" ")) {
			errors.rejectValue("shortName", "validator.Gather.shortName.whiteSpaces");
		} else if (this.gatherService.shortNameExists(guildId, shortName)) {
			errors.rejectValue("shortName", "validator.Gather.shortName.exists");
		}
	}

	private void validateChannelId(Errors errors, String channelId) {
		if (channelId == null || channelId.isBlank()) {
			errors.rejectValue("channelId", "validator.Gather.channelId.empty");
		} else if (this.gatherService.channelExists(channelId)) {
			errors.rejectValue("channelId", "validator.Gather.channelId.exists");
		}
	}

	private void validatePlayersPerTeam(Errors errors, Integer playersPerTeam) {
		if (playersPerTeam == null) {
			errors.rejectValue("playersPerTeam", "validator.Gather.playersPerTeam.empty");
		} else if (playersPerTeam < 1 || playersPerTeam > 100) {
			errors.rejectValue("playersPerTeam", "validator.Gather.playersPerTeam.value.range");
		}
	}

	private void validateMapsNumber(Errors errors, Integer mapsNumber) {
		if (mapsNumber == null) {
			errors.rejectValue("mapsNumber", "validator.Gather.mapsNumber.empty");
		} else if (mapsNumber < 1 || mapsNumber > 10) {
			errors.rejectValue("mapsNumber", "validator.Gather.mapsNumber.value.range");
		}
	}

	private void validateGameId(Errors errors, Long gameId) {
		if (gameId == null) {
			errors.rejectValue("gameId", "validator.Gather.gameId.empty");
		}
	}

	private void validateMapsRandom(Errors errors, Boolean mapsRandom) {
		if (mapsRandom == null) {
			errors.rejectValue("mapsRandom", "validator.Gather.mapsRandom.empty");
		}
	}

	private void validateCaptainRolePriority(Errors errors, Boolean captainRolePriority) {
		if (captainRolePriority == null) {
			errors.rejectValue("captainRolePriority", "validator.Gather.captainRolePriority.empty");
		}
	}

	private void validateAllAllowed(Errors errors, Boolean allAllowed) {
		if (allAllowed == null) {
			errors.rejectValue("allAllowed", "validator.Gather.allAllowed.empty");
		}
	}
}
