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

class CityTest {

  private static final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(City.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(City.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build City then return valid City")
  void givenValidParametersWhenBuildCityThenReturnValidCity() {
    final var cityName = faker.address().city();

    final var city = City.of(cityName);

    assertThat(city.getValue()).isEqualTo(cityName);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build City then throw exception")
  void givenInvalidParametersWhenBuildCityThenThrowException(final String cityName) {

    assertThatThrownBy(() -> City.of(cityName))
        .hasMessage("City is mandatory")
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }
}