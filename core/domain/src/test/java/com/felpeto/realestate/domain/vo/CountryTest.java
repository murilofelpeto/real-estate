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

class CountryTest {

  private static final String MANDATORY_FIELD = "Country is mandatory";
  private static final String FIELD = "Country.value";
  private static final String TARGET = Country.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Country name must not be blank or null";
  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Country.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Country.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Country then return valid Country")
  void givenValidParametersWhenBuildCountryThenReturnValidCountry() {
    final var countryName = faker.address().country();

    final var country = Country.of(countryName);

    assertThat(country.getValue()).isEqualTo(countryName);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Country then throw exception")
  void givenInvalidParametersWhenBuildCountryThenThrowException(final String countryName) {
    final var exception = catchThrowableOfType(() -> Country.of(countryName),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}