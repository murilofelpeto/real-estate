package com.felpeto.realestate.domain.property.vo;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.felpeto.realestate.domain.exception.UnprocessableEntityException;
import com.felpeto.realestate.domain.exception.ValueNotFoundException;
import com.github.javafaker.Faker;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class PropertyKindTest {

  private static final String MANDATORY_FIELD = "property kind is mandatory";
  private static final String FIELD = "PropertyKind.kind";
  private static final String TARGET = PropertyKind.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "You must inform a valid property kind";
  private static final String DESCRIPTION_NOT_FOUND = "property kind not found: {0}";

  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    final String kind = faker.chuckNorris().fact();
    final String message = format(DESCRIPTION_NOT_FOUND, kind);

    return Stream.of(Arguments.of(null, MANDATORY_FIELD, FIELD, VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        Arguments.of("", MANDATORY_FIELD, FIELD, VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        Arguments.of(" ", MANDATORY_FIELD, FIELD, VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        Arguments.of(kind, message, FIELD, VIOLATION_MESSAGE, ValueNotFoundException.class));
  }

  @ParameterizedTest
  @EnumSource(PropertyKind.class)
  @DisplayName("Given values when call of then return valid property kind")
  void givenValuesWhenCallOfThenReturnValidPropertyKind(PropertyKind propertyKind) {
    final var expectedKind = propertyKind.getKind();
    assertThat(propertyKind).isEqualTo(PropertyKind.of(expectedKind));
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  void givenInvalidValuesWhenCallOfThenThrowException(final String propertyKind,
      final String message, final String field, final String violationMessage,
      final Class<? extends UnprocessableEntityException> clazz) {

    final var exception = catchThrowableOfType(() -> PropertyKind.of(propertyKind), clazz);

    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getParameter()).isEqualTo(field);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(field);
    assertThat(exception.getViolationMessage()).isEqualTo(violationMessage);
  }
}
