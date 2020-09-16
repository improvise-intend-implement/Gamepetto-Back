package com.iii.gamepetto.gamepettobackend.validator;

import com.iii.gamepetto.gamepettobackend.transferobject.request.BotPrefix;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BotPrefixValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return BotPrefix.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		BotPrefix botPrefix = (BotPrefix) o;

		if (botPrefix.getBotPrefix() == null || botPrefix.getBotPrefix().isBlank()) {
			errors.rejectValue("botPrefix", "validator.Guild.botPrefix.empty");
		} else if (botPrefix.getBotPrefix().length() > 3) {
			errors.rejectValue("botPrefix", "validator.Guild.botPrefix.length.max");
		} else if (botPrefix.getBotPrefix().contains(" ")) {
			errors.rejectValue("botPrefix", "validator.Guild.botPrefix.whiteSpaces");
		}
	}
}
