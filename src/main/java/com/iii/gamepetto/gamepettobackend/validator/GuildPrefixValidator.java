package com.iii.gamepetto.gamepettobackend.validator;

import com.iii.gamepetto.gamepettobackend.transferobject.GuildPrefix;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GuildPrefixValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return GuildPrefix.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		GuildPrefix guildPrefix = (GuildPrefix) o;
		if (guildPrefix.getBotPrefix() == null || guildPrefix.getBotPrefix().trim().length() == 0) {
			errors.rejectValue("botPrefix", "validator.Guild.botPrefix.empty");
		} else if (guildPrefix.getBotPrefix().length() > 8) {
			errors.rejectValue("botPrefix", "validator.Guild.botPrefix.length.max");
		}
	}
}
