package com.iii.gamepetto.gamepettobackend.validator;

import com.iii.gamepetto.gamepettobackend.transferobject.request.BotPrefix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class BotPrefixValidatorTest {

	BotPrefixValidator sut;
	BotPrefix botPrefix;
	Errors errors;
	Integer botPrefixMaxLength = 8;

	@BeforeEach
	void setup() {
		this.sut = new BotPrefixValidator();
		this.botPrefix = new BotPrefix();
		this.errors = new BeanPropertyBindingResult(this.botPrefix, "botPrefix");
	}

	@Test
	void validatorShouldntGenerateErrorsWhenObjectIsValid() {
		//given
		this.botPrefix.setBotPrefix("!gp");

		//when
		this.sut.validate(this.botPrefix, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(false));
	}

	@Test
	void validatorShouldGenerateErrorWhenBotPrefixPropertyIsNull() {
		//given
		this.botPrefix.setBotPrefix(null);

		//when
		this.sut.validate(this.botPrefix, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("botPrefix"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorWhenBotPrefixPropertyIsEmpty() {
		//given
		this.botPrefix.setBotPrefix("");

		//when
		this.sut.validate(this.botPrefix, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("botPrefix"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorWhenBotPrefixPropertyIsLongerThanMaxAllowedLength() {
		//given
		String botPrefix = IntStream.range(0, this.botPrefixMaxLength+1)
				.mapToObj(i -> "!")
				.collect(Collectors.joining());
		this.botPrefix.setBotPrefix(botPrefix);

		//when
		this.sut.validate(this.botPrefix, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("botPrefix"), is(notNullValue()));
	}
}
