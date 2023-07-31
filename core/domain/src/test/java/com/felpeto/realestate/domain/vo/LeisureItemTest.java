package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidItems() {
    final String description = faker.chuckNorris().fact();

    return Stream.of(
        Arguments.of(null, "description is mandatory", IllegalArgumentException.class),
        Arguments.of("", "description is mandatory", IllegalArgumentException.class),
        Arguments.of(" ", "description is mandatory", IllegalArgumentException.class),
        Arguments.of(description, "description not found: " + description,
            IllegalArgumentException.class));
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
      final Class exception) {

    assertThatThrownBy(() -> LeisureItem.of(description))
        .isInstanceOf(exception)
        .hasMessage(message);
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