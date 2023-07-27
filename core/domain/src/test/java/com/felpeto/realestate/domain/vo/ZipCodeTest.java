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

class ZipCodeTest {

  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(ZipCode.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(ZipCode.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build ZipCode then return valid ZipCode")
  void givenValidParametersWhenBuildZipCodeThenReturnValidZipCode() {
    final var zip = faker.address().zipCode();

    final var zipCode = ZipCode.of(zip);

    assertThat(zipCode.getValue()).isEqualTo(zip);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build ZipCode then throw exception")
  void givenInvalidParametersWhenBuildZipCodeThenThrowException(final String zipCode) {

    assertThatThrownBy(() -> ZipCode.of(zipCode))
        .hasMessage("ZipCode is mandatory")
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }
}