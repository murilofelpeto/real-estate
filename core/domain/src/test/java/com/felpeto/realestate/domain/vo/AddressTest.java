package com.felpeto.realestate.domain.vo;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

class AddressTest {

  private static final String MANDATORY_FIELD = "[Address] | {0} is mandatory";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var neighborhood = Neighborhood.of(faker.harryPotter().spell());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var number = PropertyNumber.of(Integer.parseInt(faker.address().buildingNumber()));
    final var complement = faker.address().streetSuffix();

    return Stream.of(
        Arguments.of(
            null,
            state,
            city,
            neighborhood,
            zipCode,
            streetName,
            number,
            complement,
            "country"),
        Arguments.of(
            country,
            null,
            city,
            neighborhood,
            zipCode,
            streetName,
            number,
            complement,
            "state"),
        Arguments.of(
            country,
            state,
            null,
            neighborhood,
            zipCode,
            streetName,
            number,
            complement,
            "city"),
        Arguments.of(
            country,
            state,
            city,
            null,
            zipCode,
            streetName,
            number,
            complement,
            "neighborhood"),
        Arguments.of(
            country,
            state,
            city,
            neighborhood,
            null,
            streetName,
            number,
            complement,
            "zipCode"),
        Arguments.of(
            country,
            state,
            city,
            neighborhood,
            zipCode,
            null,
            number,
            complement,
            "streetName"),
        Arguments.of(
            country,
            state,
            city,
            neighborhood,
            zipCode,
            streetName,
            null,
            complement,
            "number"));
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Address.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Address.class)
        .suppress(Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build address then return valid address")
  void givenValidParametersWhenBuildAddressThenReturnValidAddress() {
    final var country = Country.of(faker.address().country());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var neighborhood = Neighborhood.of(faker.harryPotter().spell());
    final var zipCode = ZipCode.of(faker.address().zipCode());
    final var streetName = StreetName.of(faker.address().streetName());
    final var number = PropertyNumber.of(Integer.parseInt(faker.address().buildingNumber()));
    final var complement = faker.address().streetSuffix();
    final var address = Address.builder()
        .country(country)
        .state(state)
        .city(city)
        .neighborhood(neighborhood)
        .zipCode(zipCode)
        .streetName(streetName)
        .number(number)
        .complement(complement)
        .build();

    assertThat(address.country()).isEqualTo(country);
    assertThat(address.state()).isEqualTo(state);
    assertThat(address.city()).isEqualTo(city);
    assertThat(address.neighborhood()).isEqualTo(neighborhood);
    assertThat(address.zipCode()).isEqualTo(zipCode);
    assertThat(address.streetName()).isEqualTo(streetName);
    assertThat(address.number()).isEqualTo(number);
    assertThat(address.complement()).isEqualTo(complement);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build address then throw exception")
  void givenInvalidParametersWhenBuildAddressThenThrowException(
      final Country country,
      final State state,
      final City city,
      final Neighborhood neighborhood,
      final ZipCode zipCode,
      final StreetName streetName,
      final PropertyNumber number,
      final String complement,
      final String field) {

    assertThatThrownBy(() -> Address.builder()
        .country(country)
        .state(state)
        .city(city)
        .neighborhood(neighborhood)
        .zipCode(zipCode)
        .streetName(streetName)
        .number(number)
        .complement(complement)
        .build())
        .isInstanceOf(NullPointerException.class)
        .hasMessage(format(MANDATORY_FIELD, field));
  }
}