package com.felpeto.realestate.domain.person.vo;

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

class EmailTest {

  private static final String MANDATORY_FIELD = "Email is mandatory";
  private static final String FIELD = "Email.value";
  private static final String TARGET = Email.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Email must be valid";

  private final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ", "asdsa.@xyz.com", ".asdas@xyz.com", "asdf@gmail.dlskjf");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Email.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Email.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Email then return Email")
  void givenValidParametersWhenBuildEmailThenReturnValidEmail() {
    final var value = faker.internet().emailAddress();
    final var email = Email.of(value);

    assertThat(email.getValue()).isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Email then throw exception")
  void givenInvalidParametersWhenBuildEmailThenThrowException(final String value) {
    final var exception = catchThrowableOfType(() -> Email.of(value),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }

}