package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CityTest {

  private static final String MANDATORY_FIELD = "City is mandatory";
  private static final String FIELD = "City.value";
  private static final String TARGET = City.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The City name must not be blank or null";
  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(City.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(City.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build City then return valid City")
  void givenValidParametersWhenBuildCityThenReturnValidCity() {
    final var cityName = faker.address().city();

    final var city = City.of(cityName);

    assertThat(city.getValue()).isEqualTo(cityName);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build City then throw exception")
  void givenInvalidParametersWhenBuildCityThenThrowException(final String cityName) {
    final var exception = catchThrowableOfType(() -> City.of(cityName),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}