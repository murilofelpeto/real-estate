package com.felpeto.realestate.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.github.javafaker.Faker;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FilterMapperTest {

  private final Faker faker = new Faker();

  private static Stream<Arguments> nullOrEmptyFields() {
    return Stream.of(
        Arguments.of("", "", null, null, null, null, "", "", null, null, ""),
        Arguments.of("  ", " ", null, null, null, null, "    ", " ", null, null, "  "),
        Arguments.of(null, null, null, null, null, null, null, null, null, null, null)
    );
  }

  @Test
  @DisplayName("Given filterDto when map to filter then return valid filter")
  void givenFilterDtoWhenMapToFilterThenReturnValidFilter() {
    final var filterDto = createFilter();

    final var filter = FilterMapper.toFilter(filterDto);

    assertThat(filter.city().getValue()).isEqualTo(filterDto.getCity());
    assertThat(filter.country().getValue()).isEqualTo(filterDto.getCountry());
    assertThat(filter.garage().getValue()).isEqualTo(filterDto.getGarage());
    assertThat(filter.isFurnished()).isEqualTo(filterDto.getIsFurnished());
    assertThat(filter.isRent()).isEqualTo(filterDto.getIsRent());
    assertThat(filter.isSale()).isEqualTo(filterDto.getIsSale());
    assertThat(filter.neighborhood().getValue()).isEqualTo(filterDto.getNeighborhood());
    assertThat(filter.propertyKind().getKind()).contains(filterDto.getPropertyKind());
    assertThat(filter.rooms().getValue()).isEqualTo(filterDto.getRooms());
    assertThat(filter.size().getValue()).isEqualTo(filterDto.getSize());
    assertThat(filter.state().getValue()).isEqualTo(filterDto.getState());
  }

  @ParameterizedTest
  @MethodSource("nullOrEmptyFields")
  @DisplayName("Given filterDto with null or empty fields when map to filter then return valid filter")
  void givenFilterDtoWithNullOrEmptyFieldsWhenMapToFilterThenReturnValidFilter(
      final String city,
      final String country,
      final Integer garage,
      final Boolean isFurnished,
      final Boolean isRent,
      final Boolean isSale,
      final String neighborhood,
      final String propertyKind,
      final Integer rooms,
      final Integer size,
      final String state) {

    final var filterDto = FilterDto.builder()
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

    final var filter = FilterMapper.toFilter(filterDto);

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

  private FilterDto createFilter() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();

    return FilterDto.builder()
        .city(faker.address().city())
        .country(faker.address().country())
        .garage(faker.number().numberBetween(1, 99))
        .isFurnished(faker.bool().bool())
        .isRent(faker.bool().bool())
        .isSale(faker.bool().bool())
        .neighborhood(faker.pokemon().name())
        .propertyKind(propertyKind)
        .rooms(faker.number().numberBetween(1, 9999))
        .size(faker.number().numberBetween(60, 9999999))
        .state(faker.address().stateAbbr())
        .build();
  }
}