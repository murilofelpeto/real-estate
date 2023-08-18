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

class StreetNameTest {

  private static final String MANDATORY_FIELD = "Street name is mandatory";
  private static final String FIELD = "StreetName.value";
  private static final String TARGET = StreetName.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Street name must not be blank or null";
  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(StreetName.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(StreetName.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build StreetName then return valid StreetName")
  void givenValidParametersWhenBuildStreetNameThenReturnValidStreetName() {
    final var streetName = faker.address().streetName();

    final var street = StreetName.of(streetName);

    assertThat(street.getValue()).isEqualTo(streetName);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build StreetName then throw exception")
  void givenInvalidParametersWhenBuildStreetNameThenThrowException(final String streetName) {
    final var exception = catchThrowableOfType(() -> StreetName.of(streetName),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}