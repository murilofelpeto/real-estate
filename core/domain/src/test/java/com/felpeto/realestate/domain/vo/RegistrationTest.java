package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidFormatException;
import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class RegistrationTest {

  private static final String REGEX_MESSAGE = "^[0-9]{5}.[0-9]{1}.[0-9]{7}-[0-9]{2}$";
  private static final String REGEX = "[0-9]{5}.[0-9]{1}.[0-9]{7}-[0-9]{2}";
  private static final String INVALID_REGISTRATION_FORMAT = "Invalid registration format";
  private static final String FIELD = "value";
  private static final String TARGET = Registration.class.getSimpleName();
  private static final String VIOLATION_MESSAGE =
      "You must provide a registration following the pattern: " + REGEX_MESSAGE;
  private static final String MANDATORY_FIELD = "Registration is mandatory";
  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Registration.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Registration.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Registration then return valid Registration")
  void givenValidParametersWhenBuildRegistrationThenReturnValidRegistration() {
    final var expression = faker.expression(faker.regexify(REGEX));

    final var registration = Registration.of(expression);

    assertThat(registration.getValue()).isEqualTo(expression);
  }

  @Test
  @DisplayName("Given invalid pattern when build Registration then throw exception")
  void givenInvalidPatternWhenBuildRegistrationThenThrowException() {
    final var expression = faker.rickAndMorty().location();

    final var exception = catchThrowableOfType(() -> Registration.of(expression),
        InvalidFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_REGISTRATION_FORMAT);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Registration then throw exception")
  void givenInvalidParametersWhenBuildRegistrationThenThrowException(final String registration) {

    final var exception = catchThrowableOfType(() -> Registration.of(registration),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}