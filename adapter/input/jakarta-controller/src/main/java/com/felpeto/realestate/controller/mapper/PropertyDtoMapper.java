package com.felpeto.realestate.controller.mapper;

import static com.felpeto.realestate.controller.dto.output.Availability.AVAILABLE;
import static com.felpeto.realestate.controller.dto.output.Availability.UNAVAILABLE;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.controller.dto.PropertyKindDto;
import com.felpeto.realestate.controller.dto.output.LeisureItemsResponseDto;
import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.PropertyKind;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PropertyDtoMapper {

  public static List<PropertyResponseDto> toPropertiesResponseDto(final List<Property> properties) {
    return properties.stream().map(PropertyDtoMapper::toPropertyResponseDto).toList();
  }

  public static PropertyResponseDto toPropertyResponseDto(final Property property) {
    final var address = property.address();
    final var condominium = property.condominium();
    final var rent = property.rent();
    final var sale = property.sale();
    final var size = property.size();

    final var propertyItems = toLeisureItemsResponseDto(property.items());
    final var condominiumItems = toLeisureItemsResponseDto(condominium.getItems());

    return PropertyResponseDto.builder()
        .id(property.uuid())
        .buildingArea(size.buildingArea().getValue())
        .city(address.city().getValue())
        .complement(address.complement())
        .condominiumItems(condominiumItems)
        .condominiumValue(condominium.isCondominium() ? condominium.getPrice().getValue() : null)
        .country(address.country().getValue())
        .description(property.description())
        .garage(size.garage().getValue())
        .isCondominium(condominium.isCondominium())
        .isFurnished(property.isFurnished())
        .isRent(rent.isRent())
        .isSale(sale.isSale())
        .landSize(size.landSize().getValue())
        .neighborhood(address.neighborhood().getValue())
        .number(address.number().getValue())
        .propertyItems(propertyItems)
        .propertyKind(toPropertyKindDto(property.propertyKind()))
        .rentPrice(rent.isRent() ? rent.getPrice().getValue() : null)
        .rooms(size.rooms().getValue())
        .salePrice(sale.isSale() ? sale.getPrice().getValue() : null)
        .state(address.state().getValue())
        .streetName(address.streetName().getValue())
        .taxes(property.taxes().getValue())
        .zipCode(address.zipCode().getValue())
        .build();
  }

  private static PropertyKindDto toPropertyKindDto(final PropertyKind propertyKind) {
    return PropertyKindDto.valueOf(propertyKind.name());
  }

  private static Set<LeisureItemsResponseDto> toLeisureItemsResponseDto(
      final List<LeisureItem> leisureItems) {

    final var availableItems = leisureItems.stream()
        .map(LeisureItem::getDescription)
        .collect(Collectors.toCollection(HashSet::new));

    final var unavailableItems = LeisureItem.streamValues()
        .map(LeisureItem::getDescription)
        .collect(Collectors.toCollection(HashSet::new));

    unavailableItems.removeAll(availableItems);

    final var availableResponse = LeisureItemsResponseDto.builder()
        .availability(AVAILABLE)
        .items(availableItems)
        .build();

    final var unavailableResponse = LeisureItemsResponseDto.builder()
        .availability(UNAVAILABLE)
        .items(unavailableItems)
        .build();

    return Set.of(availableResponse, unavailableResponse);
  }
}
