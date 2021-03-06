package com.iii.gamepetto.gamepettobackend.validator;

import com.iii.gamepetto.gamepettobackend.service.GatherService;
import com.iii.gamepetto.gamepettobackend.transferobject.request.GatherRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class GatherRequestValidatorTest {

	@Mock
	GatherService gatherService;
	@InjectMocks
	GatherRequestValidator sut;
	GatherRequest gatherRequest;
	Errors errors;
	Integer nameMaxLength = 128;
	Integer shortNameMaxLength = 16;
	Integer playersPerTeamMaxValue = 100;
	Integer mapsNumberMaxValue = 10;
	Integer nameMinLength = 1;
	Integer shortNameMinLength = 1;
	Integer playersPerTeamMinValue = 1;
	Integer mapsNumberMinValue = 1;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		this.gatherRequest = new GatherRequest();
		this.errors = new BeanPropertyBindingResult(this.gatherRequest, "gatherRequest");
	}

	@Test
	void validatorShouldntGenerateErrorsWhenObjectIsFullyValidWithMaxValues() {
		//given
		String name = IntStream.range(0, this.nameMaxLength)
				.mapToObj(i -> "x")
				.collect(Collectors.joining());
		String shortName = IntStream.range(0, this.shortNameMaxLength)
				.mapToObj(i -> "x")
				.collect(Collectors.joining());
		Set<Long> mapsIds = LongStream.range(0, this.mapsNumberMaxValue)
				.boxed()
				.collect(Collectors.toSet());
		this.gatherRequest.setGuildId("123123");
		this.gatherRequest.setAllAllowed(true);
		this.gatherRequest.setCaptainRolePriority(true);
		this.gatherRequest.setChannelId("12321321312");
		this.gatherRequest.setGameId(1L);
		this.gatherRequest.setMapsNumber(this.mapsNumberMaxValue);
		this.gatherRequest.setPlayersPerTeam(this.playersPerTeamMaxValue);
		this.gatherRequest.setName(name);
		this.gatherRequest.setShortName(shortName);
		this.gatherRequest.setMapsRandom(false);
		this.gatherRequest.setMapsIds(mapsIds);
		given(this.gatherService.shortNameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.nameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.channelExists(anyString())).willReturn(false);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(false));
	}

	@Test
	void validatorShouldntGenerateErrorsWhenObjectIsFullyValidWithMinValues() {
		//given
		String name = IntStream.range(0, this.nameMinLength)
				.mapToObj(i -> "x")
				.collect(Collectors.joining());
		String shortName = IntStream.range(0, this.shortNameMinLength)
				.mapToObj(i -> "x")
				.collect(Collectors.joining());
		Set<Long> mapsIds = LongStream.range(0, this.mapsNumberMinValue)
				.boxed()
				.collect(Collectors.toSet());
		this.gatherRequest.setGuildId("123123");
		this.gatherRequest.setAllAllowed(true);
		this.gatherRequest.setCaptainRolePriority(true);
		this.gatherRequest.setChannelId("12321321312");
		this.gatherRequest.setGameId(1L);
		this.gatherRequest.setMapsNumber(this.mapsNumberMinValue);
		this.gatherRequest.setPlayersPerTeam(this.playersPerTeamMinValue);
		this.gatherRequest.setName(name);
		this.gatherRequest.setShortName(shortName);
		this.gatherRequest.setMapsRandom(false);
		this.gatherRequest.setMapsIds(mapsIds);
		given(this.gatherService.shortNameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.nameExists(anyString(), anyString())).willReturn(false);
		given(this.gatherService.channelExists(anyString())).willReturn(false);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(false));
	}

	@Test
	void validatorShouldGenerateErrorsWhenPropertiesAreNull() {
		//given
		//freshly created object

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getErrorCount(), is(10));
		assertThat(this.errors.getFieldError("guildId"), is(notNullValue()));
		assertThat(this.errors.getFieldError("allAllowed"), is(notNullValue()));
		assertThat(this.errors.getFieldError("captainRolePriority"), is(notNullValue()));
		assertThat(this.errors.getFieldError("channelId"), is(notNullValue()));
		assertThat(this.errors.getFieldError("gameId"), is(notNullValue()));
		assertThat(this.errors.getFieldError("mapsNumber"), is(notNullValue()));
		assertThat(this.errors.getFieldError("playersPerTeam"), is(notNullValue()));
		assertThat(this.errors.getFieldError("name"), is(notNullValue()));
		assertThat(this.errors.getFieldError("shortName"), is(notNullValue()));
		assertThat(this.errors.getFieldError("mapsRandom"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorsOnFieldsWhichShouldntBeBlank() {
		//given
		this.gatherRequest.setGuildId(" ");
		this.gatherRequest.setChannelId(" ");
		this.gatherRequest.setName(" ");
		this.gatherRequest.setShortName(" ");

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("guildId"), is(notNullValue()));
		assertThat(this.errors.getFieldError("channelId"), is(notNullValue()));
		assertThat(this.errors.getFieldError("name"), is(notNullValue()));
		assertThat(this.errors.getFieldError("shortName"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorsOnFieldsWhichAreLesserThanMin() {
		//given
		this.gatherRequest.setPlayersPerTeam(this.playersPerTeamMinValue - 1);
		this.gatherRequest.setMapsNumber(this.mapsNumberMinValue - 1);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("playersPerTeam"), is(notNullValue()));
		assertThat(this.errors.getFieldError("mapsNumber"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorsOnFieldsWhichAreGreaterThanMax() {
		//given
		String name = IntStream.range(0, this.nameMaxLength + 1)
				.mapToObj(i -> "x")
				.collect(Collectors.joining());
		String shortName = IntStream.range(0, this.shortNameMaxLength + 1)
				.mapToObj(i -> "x")
				.collect(Collectors.joining());
		this.gatherRequest.setName(name);
		this.gatherRequest.setShortName(shortName);
		this.gatherRequest.setMapsNumber(this.mapsNumberMaxValue + 1);
		this.gatherRequest.setPlayersPerTeam(this.playersPerTeamMaxValue + 1);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("name"), is(notNullValue()));
		assertThat(this.errors.getFieldErrors("shortName"), is(notNullValue()));
		assertThat(this.errors.getFieldError("mapsNumber"), is(notNullValue()));
		assertThat(this.errors.getFieldError("playersPerTeam"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorsOnFieldsWhichExistenceShouldBeCheckedOnValidatorLevel() {
		//given
		this.gatherRequest.setGuildId("123");
		this.gatherRequest.setName("name one");
		this.gatherRequest.setShortName("123shortie3");
		this.gatherRequest.setChannelId("1231421321");
		given(this.gatherService.nameExists(this.gatherRequest.getGuildId(), this.gatherRequest.getName()))
				.willReturn(true);
		given(this.gatherService.shortNameExists(this.gatherRequest.getGuildId(), this.gatherRequest.getShortName()))
				.willReturn(true);
		given(this.gatherService.channelExists(this.gatherRequest.getChannelId())).willReturn(true);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("name"), is(notNullValue()));
		assertThat(this.errors.getFieldError("shortName"), is(notNullValue()));
		assertThat(this.errors.getFieldError("channelId"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorWhenShortNameContainsOnlyNumbers() {
		//given
		this.gatherRequest.setShortName("1832132");

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("shortName"), is(notNullValue()));
	}

	@ParameterizedTest
	@ValueSource(strings = {"#gathero23", "-nameski", "@yup"})
	void validatorShouldGenerateErrorWhenShortNameContainsIllegalCharacterAtTheBeginning(String shortName) {
		//given
		this.gatherRequest.setShortName(shortName);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("shortName"), is(notNullValue()));
	}

	@ParameterizedTest
	@ValueSource(strings = {" beginning", "end ", "in the middle"})
	void validateShouldGenerateErrorWhenShortNameContainsWhiteSpaceAnywhere(String shortName) {
		//given
		this.gatherRequest.setShortName(shortName);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("shortName"), is(notNullValue()));
	}

	@ParameterizedTest
	@ValueSource(strings = {" at the beginning", "at the end "})
	void validateShouldGenerateErrorWhenNameStartsOrEndsWithWhiteSpace(String name) {
		//given
		this.gatherRequest.setName(name);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.hasErrors(), is(true));
		assertThat(this.errors.getFieldError("name"), is(notNullValue()));
	}

	@Test
	void validatorShouldGenerateErrorWhenMapsIdsSizeIsLessThanMapsNumber() {
		//given
		int mapsNumber = this.mapsNumberMinValue;
		Set<Long> mapsIds = LongStream
				.range(0, mapsNumber - 1)
				.boxed()
				.collect(Collectors.toSet());
		this.gatherRequest.setMapsNumber(mapsNumber);
		this.gatherRequest.setMapsIds(mapsIds);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.getFieldError("mapsIds"), is(notNullValue()));
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1})
	void validatorShouldntGenerateErrorWhenMapsIdsSizeIsEqualOrGreater(Integer mapsIncrement) {
		//given
		int mapsNumber = this.mapsNumberMinValue;
		Set<Long> mapsIds = LongStream
				.range(0, mapsNumber + mapsIncrement)
				.boxed()
				.collect(Collectors.toSet());
		this.gatherRequest.setMapsNumber(mapsNumber);
		this.gatherRequest.setMapsIds(mapsIds);

		//when
		this.sut.validate(this.gatherRequest, this.errors);

		//then
		assertThat(this.errors.getFieldError("mapsIds"), is(nullValue()));
	}
}
