package com.felpeto.realestate.controller;

import static com.felpeto.realestate.controller.mapper.FilterMapper.toFilter;
import static com.felpeto.realestate.controller.mapper.PageMapper.toPage;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.controller.dto.input.PageDto;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.property.PropertySize;
import com.felpeto.realestate.domain.vo.Address;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Condominium;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.Money;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.PropertyNumber;
import com.felpeto.realestate.domain.vo.Registration;
import com.felpeto.realestate.domain.vo.Rent;
import com.felpeto.realestate.domain.vo.Sale;
import com.felpeto.realestate.domain.vo.Size;
import com.felpeto.realestate.domain.vo.State;
import com.felpeto.realestate.domain.vo.StreetName;
import com.felpeto.realestate.domain.vo.ZipCode;
import com.felpeto.realestate.usecase.property.PropertyGetter;
import com.github.javafaker.Faker;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyControllerTest {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private final Faker faker = new Faker();
  @Mock
  private PropertyGetter propertyGetter;

  @InjectMocks
  private PropertyController controller;

  @Test
  @DisplayName("Given pagination and filter when get property then return valid property")
  void givenPaginationAndFilterWhenGetPropertyThenReturnValidProperty() {
    final var pageDto = createPage();
    final var filterDto = createFilter();
    final var property = createProperty();

    when(propertyGetter.findAll(toPage(pageDto), toFilter(filterDto))).thenReturn(
        List.of(property));

    final var response = controller.findAll(pageDto, filterDto);

    assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
    assertThat(response.getEntity()).isNotNull();

    verify(propertyGetter).findAll(toPage(pageDto), toFilter(filterDto));
    verifyNoMoreInteractions(propertyGetter);
  }

  @Test
  @DisplayName("Given pagination and filter when get property then return empty content")
  void givenPaginationAndFilterWhenGetPropertyThenReturnEmptyContent() {
    final var pageDto = createPage();
    final var filterDto = createFilter();

    when(propertyGetter.findAll(toPage(pageDto), toFilter(filterDto))).thenReturn(emptyList());

    final var response = controller.findAll(pageDto, filterDto);

    assertThat(response.getStatusInfo()).isEqualTo(Status.NO_CONTENT);
    assertThat(response.getEntity()).isNull();

    verify(propertyGetter).findAll(toPage(pageDto), toFilter(filterDto));
    verifyNoMoreInteractions(propertyGetter);
  }

  private PageDto createPage() {
    return PageDto.builder()
        .limit(2)
        .offset(2)
        .sort("+total_price")
        .build();
  }

  private FilterDto createFilter() {
    final String propertyKind = Arrays.stream(
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

  private Property createProperty() {
    final var registration = Registration.of(faker.expression(faker.regexify(REGEX)));
    final var propertyKind = faker.options().option(PropertyKind.class);
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