package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.github.javafaker.Faker;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class PropertyKindTest {

  private static final String VALUE_NOT_FOUND = "PropertyKind | Desired value not found";

  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    return Stream.of(
        arguments(faker.hobbit().location(), new IllegalArgumentException()),
        arguments("", new IllegalArgumentException()),
        arguments("  ", new IllegalArgumentException()),
        arguments(null, new NullPointerException()));
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
  void givenInvalidValuesWhenCallOfThenThrowException(final String kind, Throwable exception) {
    assertThatThrownBy(() -> PropertyKind.of(kind))
        .isExactlyInstanceOf(exception.getClass())
        .hasMessage(VALUE_NOT_FOUND);
  }
}