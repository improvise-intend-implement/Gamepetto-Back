package com.iii.gamepetto.gamepettobackend.validator;

import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class GuildRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return GuildRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        GuildRequest guild = (GuildRequest) o;

        if (guild.getId() == null || guild.getId().isBlank()) {
            errors.rejectValue("id", "validator.Guild.id.empty");
        } else if (guild.getId().length() > 32) {
            errors.rejectValue("id", "validator.Guild.id.length.max");
        }

        if (guild.getName() == null || guild.getName().isBlank()) {
            errors.rejectValue("name", "validator.Guild.name.empty");
        } else if (guild.getName().length() > 100) {
            errors.rejectValue("name", "validator.Guild.name.length.max");
        }

        if (guild.getIcon() != null && guild.getIcon().length() > 128) {
            errors.rejectValue("icon", "validator.Guild.icon.length.max");
        }
    }
}
