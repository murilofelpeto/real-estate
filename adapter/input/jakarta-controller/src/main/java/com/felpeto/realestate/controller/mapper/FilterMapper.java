package com.felpeto.realestate.controller.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.domain.Filter;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.Size;
import com.felpeto.realestate.domain.vo.State;
import java.util.Optional;
import java.util.function.Function;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = PRIVATE)
public class FilterMapper {

  public static Filter toFilter(final FilterDto filterDto) {
    return Filter.builder()
        .city(mapStringToDomainObject(filterDto.getCity(), City::of))
        .country(mapStringToDomainObject(filterDto.getCountry(), Country::of))
        .garage(mapDomainObject(filterDto.getGarage(), Size::of))
        .isFurnished(filterDto.getIsFurnished())
        .isRent(filterDto.getIsRent())
        .isSale(filterDto.getIsSale())
        .neighborhood(mapStringToDomainObject(filterDto.getNeighborhood(), Neighborhood::of))
        .propertyKind(mapDomainObject(filterDto.getPropertyKind(), PropertyKind::of))
        .rooms(mapDomainObject(filterDto.getRooms(), Size::of))
        .size(mapDomainObject(filterDto.getSize(), Size::of))
        .state(mapStringToDomainObject(filterDto.getState(), State::of))
        .build();
  }

  private static <T, R> R mapDomainObject(T value, Function<T, R> mapper) {
    return Optional.ofNullable(value).map(mapper).orElse(null);
  }

  private static <T> T mapStringToDomainObject(final String value, Function<String, T> mapper) {
    if (StringUtils.isBlank(value)) {
      return null;
    }
    return mapper.apply(value);
  }
}
