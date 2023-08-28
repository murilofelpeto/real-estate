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

class SizeTest {

  private static final String INVALID_NUMBER = "Size number must be greater than 0";
  private static final String FIELD = "Size.value";
  private static final String TARGET = Size.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "When you build a Size, you must provide a number greater than 0";
  private static final Faker faker = new Faker();

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Size.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Size.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Size then return valid Size")
  void givenValidParametersWhenBuildSizeThenReturnValidSize() {
    final var number = faker.number().numberBetween(1, 999);

    final var size = Size.of(number);

    assertThat(size.getValue()).isEqualTo(number);
  }

  @Test
  @DisplayName("Given invalid parameters when build Size then throw exception")
  void givenInvalidParametersWhenBuildSizeThenThrowException() {

    assertThatThrownBy(() -> Size.of(null))
        .hasMessage("Size is mandatory")
        .isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("Given number 0 when build Size then return valid Size")
  void givenNumberZeroWhenBuildSizeThenReturnValidSize() {
    final var number = 0;

    final var size = Size.of(number);

    assertThat(size.getValue()).isEqualTo(number);
  }

  @Test
  @DisplayName("Given number lower than 0 when build Size then throw exception")
  void givenNumberLowerThanZeroWhenBuildSizeThenThrowException() {
    final var number = faker.number().numberBetween(-999, -1);

    final var exception = catchThrowableOfType(() -> Size.of(number),
        InvalidNumberLimitException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_NUMBER);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}