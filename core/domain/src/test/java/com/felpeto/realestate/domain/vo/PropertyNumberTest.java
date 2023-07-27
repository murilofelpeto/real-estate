package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.felpeto.realestate.domain.exception.InvalidPropertyNumberException;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PropertyNumberTest {

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
    assertThatThrownBy(() -> PropertyNumber.of(0))
        .hasMessage("Property number must be greater than 0")
        .isExactlyInstanceOf(InvalidPropertyNumberException.class);
  }

  @Test
  @DisplayName("Given number lower than 0 when build PropertyNumber then throw exception")
  void givenNumberLowerThanZeroWhenBuildPropertyNumberThenThrowException() {
    final var number = faker.number().numberBetween(-999, -1);
    assertThatThrownBy(() -> PropertyNumber.of(number))
        .hasMessage("Property number must be greater than 0")
        .isExactlyInstanceOf(InvalidPropertyNumberException.class);
  }
}