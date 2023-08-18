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

class NeighborhoodTest {

  private static final String MANDATORY_FIELD = "Neighborhood is mandatory";
  private static final String FIELD = "Neighborhood.value";
  private static final String TARGET = Neighborhood.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Neighborhood name must not be blank or null";

  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Neighborhood.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Neighborhood.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Neighborhood then return valid Neighborhood")
  void givenValidParametersWhenBuildNeighborhoodThenReturnValidNeighborhood() {
    final var neighborhoodName = faker.harryPotter().spell();

    final var neighborhood = Neighborhood.of(neighborhoodName);

    assertThat(neighborhood.getValue()).isEqualTo(neighborhoodName);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Neighborhood then throw exception")
  void givenInvalidParamsWhenBuildNeighborhoodThenThrowException(final String neighborhoodName) {

    final var exception = catchThrowableOfType(() -> Neighborhood.of(neighborhoodName),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }
}