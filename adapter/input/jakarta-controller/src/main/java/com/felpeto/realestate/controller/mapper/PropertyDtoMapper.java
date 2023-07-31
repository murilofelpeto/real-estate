package com.felpeto.realestate.controller.mapper;

import static java.util.Collections.emptyMap;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.vo.LeisureItem;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
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

    final var propertyItems = toPropertyItems(property);
    final var condominiumItems = toCondominiumItems(property);

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
        .images(emptyMap())
        .isCondominium(condominium.isCondominium())
        .isFurnished(property.isFurnished())
        .isRent(rent.isRent())
        .isSale(sale.isSale())
        .landSize(size.landSize().getValue())
        .neighborhood(address.neighborhood().getValue())
        .number(address.number().getValue())
        .propertyItems(propertyItems)
        .propertyKind(property.propertyKind().name())
        .rentPrice(rent.isRent() ? rent.getPrice().getValue() : null)
        .rooms(size.rooms().getValue())
        .salePrice(sale.isSale() ? sale.getPrice().getValue() : null)
        .state(address.state().getValue())
        .streetName(address.streetName().getValue())
        .taxes(property.taxes().getValue())
        .zipCode(address.zipCode().getValue())
        .build();
  }

  private static Map<String, List<String>> toPropertyItems(final Property property) {
    final var propertyAvailableItems = property.items()
        .stream()
        .map(LeisureItem::getDescription)
        .toList();

    final var propertyUnavailableItems = Stream.concat(
            property.items().stream(),
            LeisureItem.streamValues())
        .distinct()
        .map(LeisureItem::getDescription)
        .toList();

    return Map.of(
        "Available", propertyAvailableItems,
        "Unavailable", propertyUnavailableItems);
  }

  private static Map<String, List<String>> toCondominiumItems(final Property property) {
    final var condominiumAvailableItems = property.condominium()
        .getItems()
        .stream()
        .map(LeisureItem::getDescription)
        .toList();

    final var condominiumUnavailableItems = Stream.concat(
            property.condominium().getItems().stream(),
            LeisureItem.streamValues())
        .distinct()
        .map(LeisureItem::getDescription)
        .toList();

    return Map.of("Available", condominiumAvailableItems,
        "Unavailable", condominiumUnavailableItems);
  }
}
