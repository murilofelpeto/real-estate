package com.felpeto.realestate.domain.property.vo;

import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.State;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PropertyFilterTest {

  private final Faker faker = new Faker();

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(PropertyFilter.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(PropertyFilter.class)
        .suppress(Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  @DisplayName("Given valid information when create Filter then return valid Filter")
  void givenValidInformationWhenCreateFilterThenReturnValidFilter() {
    final var country = Country.of(faker.country().name());
    final var state = State.of(faker.address().stateAbbr());
    final var city = City.of(faker.address().city());
    final var neighborhood = Neighborhood.of(faker.pokemon().name());
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var size = Size.of(faker.number().numberBetween(1, 100));
    final var rooms = Size.of(faker.number().numberBetween(1, 10));
    final var isRent = faker.bool().bool();
    final var isSale = faker.bool().bool();
    final var isFurnished = faker.bool().bool();
    final var garage = Size.of(faker.number().numberBetween(1, 10));

    final var filter = PropertyFilter.builder()
        .city(city)
        .country(country)
        .garage(garage)
        .isFurnished(isFurnished)
        .isRent(isRent)
        .isSale(isSale)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .rooms(rooms)
        .size(size)
        .state(state)
        .build();

    assertThat(filter.city()).isEqualTo(city);
    assertThat(filter.country()).isEqualTo(country);
    assertThat(filter.garage()).isEqualTo(garage);
    assertThat(filter.isFurnished()).isEqualTo(isFurnished);
    assertThat(filter.isRent()).isEqualTo(isRent);
    assertThat(filter.isSale()).isEqualTo(isSale);
    assertThat(filter.neighborhood()).isEqualTo(neighborhood);
    assertThat(filter.propertyKind()).isEqualTo(propertyKind);
    assertThat(filter.rooms()).isEqualTo(rooms);
    assertThat(filter.size()).isEqualTo(size);
    assertThat(filter.state()).isEqualTo(state);
  }

  @Test
  @DisplayName("Given all null field when build Filter then return Filter")
  void givenAllNullFieldWhenBuildFilterThenReturnFilter() {
    final var filter = PropertyFilter.builder()
        .city(null)
        .country(null)
        .garage(null)
        .isFurnished(null)
        .isRent(null)
        .isSale(null)
        .neighborhood(null)
        .propertyKind(null)
        .rooms(null)
        .size(null)
        .state(null)
        .build();

    assertThat(filter.city()).isNull();
    assertThat(filter.country()).isNull();
    assertThat(filter.garage()).isNull();
    assertThat(filter.isFurnished()).isNull();
    assertThat(filter.isRent()).isNull();
    assertThat(filter.isSale()).isNull();
    assertThat(filter.neighborhood()).isNull();
    assertThat(filter.propertyKind()).isNull();
    assertThat(filter.rooms()).isNull();
    assertThat(filter.size()).isNull();
    assertThat(filter.state()).isNull();
  }
}