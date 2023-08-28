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

class NameTest {

  private static final String MANDATORY_FIELD = "Name is mandatory";
  private static final String FIELD = "Name.value";
  private static final String TARGET = Name.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Name must not be blank or null";
  private final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Name.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Name.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Name then return valid Name")
  void givenValidParametersWhenBuildNameThenReturnValidName() {
    final var value = faker.name().fullName();

    final var name = Name.of(value);

    assertThat(name.getValue()).isEqualTo(value);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Name then throw exception")
  void givenInvalidParametersWhenBuildNameThenThrowException(final String value) {
    final var exception = catchThrowableOfType(() -> Name.of(value),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}