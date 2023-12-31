package com.felpeto.realestate.controller.mapper;

import static com.felpeto.realestate.controller.dto.output.Availability.AVAILABLE;
import static com.felpeto.realestate.controller.dto.output.Availability.UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

import com.felpeto.realestate.controller.dto.output.LeisureItemsResponseDto;
import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.property.PropertySize;
import com.felpeto.realestate.domain.property.vo.Condominium;
import com.felpeto.realestate.domain.property.vo.LeisureItem;
import com.felpeto.realestate.domain.property.vo.Money;
import com.felpeto.realestate.domain.property.vo.PropertyKind;
import com.felpeto.realestate.domain.property.vo.PropertyNumber;
import com.felpeto.realestate.domain.property.vo.Registration;
import com.felpeto.realestate.domain.property.vo.Rent;
import com.felpeto.realestate.domain.property.vo.Sale;
import com.felpeto.realestate.domain.property.vo.Size;
import com.felpeto.realestate.domain.vo.Address;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.State;
import com.felpeto.realestate.domain.vo.StreetName;
import com.felpeto.realestate.domain.vo.ZipCode;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PropertyDtoMapperTest {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private final Faker faker = new Faker();

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

  @Test
  @DisplayName("Given Condominium property for rent and sale when map to response dto then return valid PropertyResponseDto")
  void givenCondominiumPropertyForRentAndSaleWhenMapToResponseDtoThenReturnValidPropertyResponseDto() {
    final var property = createProperty(true, true, true);
    final var propertySize = property.size();
    final var address = property.address();
    final var condominium = property.condominium();
    final var rent = property.rent();
    final var sale = property.sale();

    final var propertyItems = toLeisureItemsResponseDto(property.items());
    final Set<LeisureItemsResponseDto> condominiumItems = toLeisureItemsResponseDto(
        condominium.getItems());

    final var response = PropertyDtoMapper.toPropertyResponseDto(property);

    assertThat(response.getBuildingArea()).isEqualTo(propertySize.buildingArea().getValue());
    assertThat(response.getCity()).isEqualTo(address.city().getValue());
    assertThat(response.getComplement()).isEqualTo(address.complement());

    assertThat(response.getCondominiumItems())
        .isNotEmpty()
        .containsExactlyInAnyOrderElementsOf(condominiumItems);

    assertThat(response.getCondominiumValue()).isEqualTo(condominium.getPrice().getValue());
    assertThat(response.getCountry()).isEqualTo(address.country().getValue());
    assertThat(response.getDescription()).isEqualTo(property.description());
    assertThat(response.getGarage()).isEqualTo(propertySize.garage().getValue());
    assertThat(response.getId()).isEqualTo(property.uuid());
    assertThat(response.getIsCondominium()).isEqualTo(condominium.isCondominium());
    assertThat(response.getIsFurnished()).isEqualTo(property.isFurnished());
    assertThat(response.getIsRent()).isEqualTo(rent.isRent());
    assertThat(response.getIsSale()).isEqualTo(sale.isSale());
    assertThat(response.getLandSize()).isEqualTo(propertySize.landSize().getValue());
    assertThat(response.getNeighborhood()).isEqualTo(address.neighborhood().getValue());
    assertThat(response.getNumber()).isEqualTo(address.number().getValue());
    assertThat(response.getPropertyItems()).containsExactlyInAnyOrderElementsOf(propertyItems);
    assertThat(response.getPropertyKind().name()).isEqualTo(property.propertyKind().name());
    assertThat(response.getRentPrice()).isEqualTo(rent.getPrice().getValue());
    assertThat(response.getRooms()).isEqualTo(propertySize.rooms().getValue());
    assertThat(response.getSalePrice()).isEqualTo(sale.getPrice().getValue());
    assertThat(response.getState()).isEqualTo(address.state().getValue());
    assertThat(response.getStreetName()).isEqualTo(address.streetName().getValue());
    assertThat(response.getTaxes()).isEqualTo(property.taxes().getValue());
    assertThat(response.getZipCode()).isEqualTo(address.zipCode().getValue());
  }

  @Test
  @DisplayName("Given property not for rent and not for sale when map to response dto then return valid PropertyResponseDto")
  void givenPropertyNotForRentAndNotForSaleWhenMapToResponseDtoThenReturnValidPropertyResponseDto() {
    final var property = createProperty(false, false, false);
    final var condominium = property.condominium();
    final var rent = property.rent();
    final var sale = property.sale();

    final var condominiumItems = toLeisureItemsResponseDto(condominium.getItems());

    final var response = PropertyDtoMapper.toPropertyResponseDto(property);

    assertThat(response.getCondominiumItems())
        .isNotEmpty()
        .containsExactlyInAnyOrderElementsOf(condominiumItems);

    assertThat(response.getIsCondominium()).isEqualTo(condominium.isCondominium());
    assertThat(response.getCondominiumValue()).isNull();
    assertThat(response.getIsRent()).isEqualTo(rent.isRent());
    assertThat(response.getIsSale()).isEqualTo(sale.isSale());
    assertThat(response.getRentPrice()).isNull();
    assertThat(response.getSalePrice()).isNull();
  }

  @Test
  @DisplayName("Given properties when map to response dto then return List of PropertyResponseDto")
  void givenPropertiesWhenMapToResponseDtoThenReturnListOfPropertyResponseDto() {
    final var property1 = createProperty(true, true, true);
    final var property2 = createProperty(true, true, true);

    final var response = PropertyDtoMapper.toPropertiesResponseDto(List.of(property1, property2));

    assertThat(response).isNotNull()
        .asInstanceOf(list(PropertyResponseDto.class))
        .hasSize(2);
  }

  private Property createProperty(
      final boolean isRent,
      final boolean isSale,
      final boolean isCondominium) {

    final var registration = Registration.of(faker.expression(faker.regexify(REGEX)));
    final var propertyKind = faker.options().option(PropertyKind.class);
    final var address = createAddress();
    final var propertySize = createPropertySize();
    final var rent = createRent(isRent);
    final var sale = createSale(isSale);
    final var isFurnished = faker.bool().bool();
    final var taxes = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
    final var condominium = createCondominium(isCondominium);
    final var description = faker.lorem().paragraph();
    final var leisureItems = List.of(faker.options().option(LeisureItem.class));
    final var uuid = UUID.randomUUID();

    return Property.builder()
        .registration(registration)
        .propertyKind(propertyKind)
        .address(address)
        .size(propertySize)
        .rent(rent)
        .sale(sale)
        .isFurnished(isFurnished)
        .taxes(taxes)
        .condominium(condominium)
        .description(description)
        .items(leisureItems)
        .uuid(uuid)
        .build();
  }

  private Condominium createCondominium(final boolean isCondominium) {
    return Condominium.of(isCondominium,
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))),
        List.of(faker.options().option(LeisureItem.class)));
  }

  private Sale createSale(final boolean isSale) {
    return Sale.of(
        isSale,
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))));
  }

  private Rent createRent(final boolean isRent) {
    return Rent.of(
        isRent,
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))));
  }

  private PropertySize createPropertySize() {
    return PropertySize.builder()
        .buildingArea(Size.of(faker.number().numberBetween(1, 100)))
        .garage(Size.of(faker.number().numberBetween(1, 10)))
        .landSize(Size.of(faker.number().numberBetween(1, 100)))
        .rooms(Size.of(faker.number().numberBetween(1, 10)))
        .build();
  }

  private Address createAddress() {
    return Address.builder()
        .city(City.of(faker.address().city()))
        .complement(faker.ancient().god())
        .country(Country.of(faker.country().name()))
        .neighborhood(Neighborhood.of(faker.pokemon().name()))
        .number(PropertyNumber.of(faker.number().numberBetween(1, 9999)))
        .state(State.of(faker.address().stateAbbr()))
        .streetName(StreetName.of(faker.address().streetName()))
        .zipCode(ZipCode.of(faker.address().zipCode()))
        .build();
  }
}