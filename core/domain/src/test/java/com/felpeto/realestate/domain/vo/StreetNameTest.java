package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class StreetNameTest {

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

    assertThatThrownBy(() -> StreetName.of(streetName))
        .hasMessage("StreetName is mandatory")
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }
}