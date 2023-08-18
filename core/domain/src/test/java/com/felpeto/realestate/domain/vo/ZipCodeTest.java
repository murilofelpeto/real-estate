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

class ZipCodeTest {

  private static final String MANDATORY_FIELD = "Zip-code is mandatory";
  private static final String FIELD = "ZipCode.value";
  private static final String TARGET = ZipCode.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Zip-code must not be blank or null";
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
    final var exception = catchThrowableOfType(() -> ZipCode.of(zipCode),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}