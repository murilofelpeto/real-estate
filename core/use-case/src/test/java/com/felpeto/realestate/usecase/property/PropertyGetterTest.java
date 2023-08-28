package com.felpeto.realestate.usecase.property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.felpeto.realestate.domain.Page;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.property.PropertySize;
import com.felpeto.realestate.domain.property.vo.Condominium;
import com.felpeto.realestate.domain.property.vo.LeisureItem;
import com.felpeto.realestate.domain.property.vo.Money;
import com.felpeto.realestate.domain.property.vo.PropertyFilter;
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
import com.felpeto.realestate.usecase.property.port.PropertyGateway;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyGetterTest {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private final Faker faker = new Faker();

  @Mock
  private PropertyGateway propertyGateway;

  @InjectMocks
  private PropertyGetter propertyGetter;

  @Test
  @DisplayName("Given page and filter when call findAll then return list of properties")
  void givenPageAndFilterWhenCallFindAllThenReturnListOfProperties() {
    final var page = Page.from(1, 10, "+total_value");
    final var filter = PropertyFilter.builder()
        .city(City.of(faker.address().city()))
        .country(Country.of(faker.address().country()))
        .garage(Size.of(faker.number().numberBetween(0, 10)))
        .isFurnished(faker.bool().bool())
        .isRent(faker.bool().bool())
        .isSale(faker.bool().bool())
        .neighborhood(Neighborhood.of(faker.rickAndMorty().location()))
        .propertyKind(faker.options().option(PropertyKind.class))
        .rooms(Size.of(faker.number().numberBetween(0, 10)))
        .size(Size.of(faker.number().numberBetween(0, 10)))
        .state(State.of(faker.address().stateAbbr()))
        .build();
    final var property = createProperty();

    when(propertyGateway.findAll(page, filter)).thenReturn(List.of(property));

    final var response = propertyGetter.findAll(page, filter);
    assertThat(response).hasSize(1)
        .containsExactly(property);

    verify(propertyGateway).findAll(page, filter);
    verifyNoMoreInteractions(propertyGateway);
  }

  private Property createProperty() {
    final String propertyKind = faker.options().option(PropertyKind.class).getKind();

    final var registration = Registration.of(faker.expression(faker.regexify(REGEX)));
    final var address = createAddress();
    final var propertySize = createPropertySize();
    final var rent = createRent();
    final var sale = createSale();
    final var isFurnished = faker.bool().bool();
    final var taxes = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)));
    final var condominium = createCondominium();
    final var description = faker.lorem().paragraph();
    final var leisureItems = List.of(faker.options().option(LeisureItem.class));
    final var uuid = UUID.randomUUID();
    return Property.builder()
        .registration(registration)
        .propertyKind(PropertyKind.of(propertyKind))
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

  private Condominium createCondominium() {
    return Condominium.of(faker.bool().bool(),
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))),
        List.of(faker.options().option(LeisureItem.class)));
  }

  private Sale createSale() {
    return Sale.of(
        true,
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))));
  }

  private Rent createRent() {
    return Rent.of(
        true,
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