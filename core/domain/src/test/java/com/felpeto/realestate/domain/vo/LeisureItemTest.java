package com.felpeto.realestate.domain.vo;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.felpeto.realestate.domain.exception.UnprocessableEntityException;
import com.felpeto.realestate.domain.exception.ValueNotFoundException;
import com.github.javafaker.Faker;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class LeisureItemTest {

  private static final String MANDATORY_FIELD = "description is mandatory";
  private static final String FIELD = "LeisureItem.description";
  private static final String TARGET = LeisureItem.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "You must inform a valid description for leisure item";
  private static final String DESCRIPTION_NOT_FOUND = "description not found: {0}";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidItems() {
    final String description = faker.chuckNorris().fact();
    final String message = format(DESCRIPTION_NOT_FOUND, description);

    return Stream.of(
        Arguments.of(
            null,
            MANDATORY_FIELD,
            FIELD,
            VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        Arguments.of(
            "",
            MANDATORY_FIELD,
            FIELD,
            VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        Arguments.of(
            " ",
            MANDATORY_FIELD,
            FIELD,
            VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        Arguments.of(
            description,
            message,
            FIELD,
            VIOLATION_MESSAGE,
            ValueNotFoundException.class));
  }

  @ParameterizedTest
  @EnumSource(LeisureItem.class)
  @DisplayName("Given valid descriptions for LeisureItem then return Leisure item")
  void givenValidDescriptionsForLeisureItemThenReturnLeisureItem(final LeisureItem item) {
    var expectedDescription = item.getDescription();
    assertThat(item).isEqualTo(LeisureItem.of(expectedDescription));

  }

  @ParameterizedTest
  @MethodSource("invalidItems")
  @DisplayName("Given invalid descriptions for LeisureItem then throw exception")
  void givenInvalidDescriptionsForLeisureItemThenThrowException(
      final String description,
      final String message,
      final String field,
      final String violationMessage,
      final Class<? extends UnprocessableEntityException> clazz) {

    final var exception = catchThrowableOfType(() -> LeisureItem.of(description), clazz);

    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getParameter()).isEqualTo(field);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(field);
    assertThat(exception.getViolationMessage()).isEqualTo(violationMessage);
  }

  @Test
  @DisplayName("Given all values for LeisureItem then return all")
  void givenAllValuesWhenGetStreamThenReturnAll() {
    final var allDescriptions = LeisureItem.streamValues()
        .map(LeisureItem::getDescription)
        .collect(Collectors.toSet());

    assertThat(allDescriptions)
        .hasSize(LeisureItem.values().length)
        .containsExactlyInAnyOrder(
            LeisureItem.streamValues().map(LeisureItem::getDescription).toArray(String[]::new)
        );
  }
}