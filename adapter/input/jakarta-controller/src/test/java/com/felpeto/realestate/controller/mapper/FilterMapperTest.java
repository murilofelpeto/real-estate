package com.felpeto.realestate.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.github.javafaker.Faker;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FilterMapperTest {

  private final Faker faker = new Faker();

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

  private FilterDto createFilter() {
    final var propertyKind = Arrays.stream(
            faker.options().option(PropertyKind.class)
                .getKind())
        .findFirst()
        .get();

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