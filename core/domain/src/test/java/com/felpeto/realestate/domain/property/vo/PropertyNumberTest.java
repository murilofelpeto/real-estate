package com.felpeto.realestate.domain.property.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidNumberLimitException;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PropertyNumberTest {

  private static final String INVALID_NUMBER = "Property number must be greater than 0";
  private static final String FIELD = "PropertyNumber.value";
  private static final String TARGET = PropertyNumber.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "When you build a Property number, you must provide a number greater than 0";

  private static final Faker faker = new Faker();

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(PropertyNumber.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(PropertyNumber.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build PropertyNumber then return valid PropertyNumber")
  void givenValidParametersWhenBuildPropertyNumberThenReturnValidPropertyNumber() {
    final var number = faker.number().numberBetween(1, 999);

    final var propertyNumber = PropertyNumber.of(number);

    assertThat(propertyNumber.getValue()).isEqualTo(number);
  }

  @Test
  @DisplayName("Given invalid parameters when build PropertyNumber then throw exception")
  void givenInvalidParametersWhenBuildPropertyNumberThenThrowException() {

    assertThatThrownBy(() -> PropertyNumber.of(null))
        .hasMessage("Property number is mandatory")
        .isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Given number lower than 1 when build PropertyNumber then throw exception")
  void givenNumberLowerThanOneWhenBuildPropertyNumberThenThrowException() {
    final var exception = catchThrowableOfType(() -> PropertyNumber.of(0),
        InvalidNumberLimitException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_NUMBER);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }

  @Test
  @DisplayName("Given number lower than 0 when build PropertyNumber then throw exception")
  void givenNumberLowerThanZeroWhenBuildPropertyNumberThenThrowException() {
    final var number = faker.number().numberBetween(-999, -1);

    final var exception = catchThrowableOfType(() -> PropertyNumber.of(number),
        InvalidNumberLimitException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_NUMBER);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}