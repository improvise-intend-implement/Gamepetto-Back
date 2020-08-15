package com.iii.gamepetto.gamepettobackend.validator;


import com.iii.gamepetto.gamepettobackend.transferobject.request.GuildRequest;
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

    GuildRequestValidator sut;
    GuildRequest guildRequest;
    Errors errors;
    Integer guildIdMaxLength = 32;
    Integer nameMaxLength = 100;
    Integer iconMaxLength = 128;

    @BeforeEach
    void setup() {
        this.sut = new GuildRequestValidator();
        this.guildRequest = new GuildRequest();
        this.errors = new BeanPropertyBindingResult(this.guildRequest, "guildRequest");
    }

    @Test
    void validatorShouldntGenerateErrorsWhenObjectIsFullValid() {
        //given
        this.guildRequest.setGuildId("12315421");
        this.guildRequest.setName("Test");
        this.guildRequest.setIcon("dasdasdasdasdas");

        //when
        this.sut.validate(this.guildRequest, this.errors);

        //then
        assertThat(this.errors.hasErrors(), is(false));
    }

    @Test
    void validatorShouldntGenerateErrorsWhenObjectIsValidWithoutIcon() {
        //given
        this.guildRequest.setGuildId("123");
        this.guildRequest.setName("Asd");

        //when
        this.sut.validate(this.guildRequest, this.errors);

        //then
        assertThat(this.errors.hasErrors(), is(false));
    }

    @Test
    void validatorShouldGenerateErrorsWhenObjectPropertiesAreNull() {
        //given
        //when
        this.sut.validate(this.guildRequest, this.errors);

        //then
        assertThat(this.errors.hasErrors(), is(true));
        assertThat(this.errors.getFieldError("guildId"), is(notNullValue()));
        assertThat(this.errors.getFieldError("name"), is(notNullValue()));
    }

    @Test
    void validatorShouldGenerateErrorsWhenObjectPropertiesAreEmpty() {
        //given
        this.guildRequest.setGuildId("");
        this.guildRequest.setName("");

        //when
        this.sut.validate(this.guildRequest, this.errors);

        //then
        assertThat(this.errors.hasErrors(), is(true));
        assertThat(this.errors.getFieldError("guildId"), is(notNullValue()));
        assertThat(this.errors.getFieldError("name"), is(notNullValue()));
    }

    @Test
    void validatorShouldGenerateErrorsWhenObjectPropertiesExceedsMaxLength() {
        //given
        String guildId = IntStream.range(0, this.guildIdMaxLength+1)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());
        String name = IntStream.range(0, this.nameMaxLength+1)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());
        String icon = IntStream.range(0, this.iconMaxLength+1)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());
        this.guildRequest.setGuildId(guildId);
        this.guildRequest.setName(name);
        this.guildRequest.setIcon(icon);

        //when
        this.sut.validate(this.guildRequest, this.errors);

        //then
        assertThat(this.errors.hasErrors(), is(true));
        assertThat(this.errors.getFieldError("guildId"), is(notNullValue()));
        assertThat(this.errors.getFieldError("name"), is(notNullValue()));
        assertThat(this.errors.getFieldError("icon"), is(notNullValue()));
    }

}
