package com.felpeto.realestate.controller.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.domain.Filter;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Money;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.Size;
import com.felpeto.realestate.domain.vo.State;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class FilterMapper {

  public static Filter toFilter(final FilterDto filterDto) {
    return Filter.builder()
        .city(City.of(filterDto.getCity()))
        .country(Country.of(filterDto.getCountry()))
        .garage(Size.of(filterDto.getGarage()))
        .isFurnished(filterDto.getIsFurnished())
        .isRent(filterDto.getIsRent())
        .isSale(filterDto.getIsSale())
        .neighborhood(Neighborhood.of(filterDto.getNeighborhood()))
        .propertyKind(PropertyKind.of(filterDto.getPropertyKind()))
        .rooms(Size.of(filterDto.getRooms()))
        .size(Size.of(filterDto.getSize()))
        .state(State.of(filterDto.getState()))
        .totalPrice(Money.of(filterDto.getTotalPrice()))
        .build();
  }
}
