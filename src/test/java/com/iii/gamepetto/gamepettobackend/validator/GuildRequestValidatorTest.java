package com.iii.gamepetto.gamepettobackend.validator;


import com.iii.gamepetto.gamepettobackend.transferobject.GuildRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class GuildRequestValidatorTest {

    private GuildRequestValidator sut;
    private GuildRequest guildRequest;
    private Errors errors;
    private Integer guildIdMaxLength = 32;
    private Integer nameMaxLength = 100;
    private Integer iconMaxLength = 128;

    @BeforeEach
    private void setup() {
        sut = new GuildRequestValidator();
        guildRequest = new GuildRequest();
        errors = new BeanPropertyBindingResult(guildRequest, "guildRequest");
    }

    @Test
    public void validatorShouldntGenerateErrorsWhenObjectIsFullValid() {
        //given
        guildRequest.setGuildId("12315421");
        guildRequest.setName("Test");
        guildRequest.setIcon("dasdasdasdasdas");

        //when
        sut.validate(guildRequest, errors);

        //then
        assertThat(errors.hasErrors(), is(false));
    }

    @Test
    public void validatorShouldntGenerateErrorsWhenObjectIsValidWithoutIcon() {
        //given
        guildRequest.setGuildId("123");
        guildRequest.setName("Asd");

        //when
        sut.validate(guildRequest, errors);

        //then
        assertThat(errors.hasErrors(), is(false));
    }

    @Test
    public void validatorShouldGenerateErrorsWhenObjectPropertiesAreNull() {
        //given
        //when
        sut.validate(guildRequest, errors);

        //then
        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getFieldError("guildId"), is(notNullValue()));
        assertThat(errors.getFieldError("name"), is(notNullValue()));
    }

    @Test
    public void validatorShouldGenerateErrorsWhenObjectPropertiesAreEmpty() {
        //given
        guildRequest.setGuildId("");
        guildRequest.setName("");

        //when
        sut.validate(guildRequest, errors);

        //then
        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getFieldError("guildId"), is(notNullValue()));
        assertThat(errors.getFieldError("name"), is(notNullValue()));
    }

    @Test
    public void validatorShouldGenerateErrorsWhenObjectPropertiesExceedsMaxLength() {
        //given
        String guildId = IntStream.range(0, guildIdMaxLength+1)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());
        String name = IntStream.range(0, nameMaxLength+1)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());
        String icon = IntStream.range(0, iconMaxLength+1)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());
        guildRequest.setGuildId(guildId);
        guildRequest.setName(name);
        guildRequest.setIcon(icon);

        //when
        sut.validate(guildRequest, errors);

        //then
        assertThat(errors.hasErrors(), is(true));
        assertThat(errors.getFieldError("guildId"), is(notNullValue()));
        assertThat(errors.getFieldError("name"), is(notNullValue()));
        assertThat(errors.getFieldError("icon"), is(notNullValue()));
    }

}