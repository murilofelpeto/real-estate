package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class StateTest {

  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ", faker.address().state());
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(State.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(State.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build State then return valid State")
  void givenValidParametersWhenBuildStateThenReturnValidState() {
    final var stateName = faker.address().stateAbbr();

    final var state = State.of(stateName);

    assertThat(state.getValue()).isEqualTo(stateName);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build State then throw exception")
  void givenInvalidParametersWhenBuildStateThenThrowException(final String stateName) {

    assertThatThrownBy(() -> State.of(stateName))
        .hasMessage("State is mandatory and must be abbreviated")
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }
}