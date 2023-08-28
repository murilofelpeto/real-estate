package com.felpeto.realestate.domain.person.vo;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidNumberLimitException;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PhoneNumberTest {

  private static final String INVALID_NUMBER = "{0} must be greater than 0";
  private static final String FIELD = "PhoneNumber.{0}";
  private static final String TARGET = PhoneNumber.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "When you build a Phone number, you must provide a {0} greater than 0";

  private final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    return Stream.of(
        Arguments.of(0, 1, "areaCode"),
        Arguments.of(-1, 1, "areaCode"),
        Arguments.of(1, 0, "number"),
        Arguments.of(1, -1, "number")
    );
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(PhoneNumber.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(PhoneNumber.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build PhoneNumber then return valid PhoneNumber")
  void givenValidParametersWhenBuildPhoneNumberThenReturnValidPhoneNumber() {
    final var areaCode = faker.number().numberBetween(1, 99);
    final var number = faker.number().numberBetween(111111, 99999999);
    final var phoneNumber = PhoneNumber.of(areaCode, number);

    assertThat(phoneNumber.getAreaCode()).isEqualTo(areaCode);
    assertThat(phoneNumber.getNumber()).isEqualTo(number);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build PhoneNumber then throw exception")
  void givenInvalidParametersWhenBuildPhoneNumberThenThrowException(final int areaCode,
      final int number, final String fieldName) {
    final var exception = catchThrowableOfType(() -> PhoneNumber.of(areaCode, number),
        InvalidNumberLimitException.class);

    assertThat(exception.getMessage()).isEqualTo(format(INVALID_NUMBER, fieldName));
    assertThat(exception.getParameter()).isEqualTo(format(FIELD, fieldName));
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(format(FIELD, fieldName));
    assertThat(exception.getViolationMessage()).isEqualTo(format(VIOLATION_MESSAGE, fieldName));
  }
}