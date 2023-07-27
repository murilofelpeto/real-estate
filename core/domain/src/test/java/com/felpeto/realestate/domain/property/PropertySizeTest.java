package com.felpeto.realestate.domain.property;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.felpeto.realestate.domain.vo.Size;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PropertySizeTest {

  private static final String MANDATORY_FIELD = "[PropertySize] | {0} is mandatory";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    final var landSize = Size.of(faker.number().numberBetween(1, 100));
    final var buildingArea = Size.of(faker.number().numberBetween(1, 100));
    final var garage = Size.of(faker.number().numberBetween(1, 100));
    final var rooms = Size.of(faker.number().numberBetween(1, 100));

    return Stream.of(
        Arguments.of(null, buildingArea, garage, rooms, "landSize"),
        Arguments.of(landSize, null, garage, rooms, "buildingArea"),
        Arguments.of(landSize, buildingArea, null, rooms, "garage"),
        Arguments.of(landSize, buildingArea, garage, null, "rooms"));
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(PropertySize.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(PropertySize.class)
        .suppress(Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build property size then return valid Property size")
  void givenValidParametersWhenBuildPropertySizeThenReturnValidPropertySize() {
    final var buildingArea = Size.of(faker.number().numberBetween(1, 100));
    final var landSize = Size.of(faker.number().numberBetween(1, 100));
    final var garage = Size.of(faker.number().numberBetween(1, 100));
    final var rooms = Size.of(faker.number().numberBetween(1, 100));
    final var propertySize = PropertySize.builder()
        .landSize(landSize)
        .buildingArea(buildingArea)
        .garage(garage)
        .rooms(rooms)
        .build();

    assertThat(propertySize.buildingArea()).isEqualTo(buildingArea);
    assertThat(propertySize.garage()).isEqualTo(garage);
    assertThat(propertySize.landSize()).isEqualTo(landSize);
    assertThat(propertySize.rooms()).isEqualTo(rooms);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build property size then return throws exception")
  void givenInvalidParametersWhenBuildPropertySizeThenReturnThrowsException(
      final Size landSize,
      final Size buildingArea,
      final Size garage,
      final Size rooms,
      final String field) {

    assertThatThrownBy(() -> PropertySize.builder()
        .landSize(landSize)
        .buildingArea(buildingArea)
        .garage(garage)
        .rooms(rooms)
        .build())
        .hasMessage(format(MANDATORY_FIELD, field))
        .isExactlyInstanceOf(NullPointerException.class);
  }
}