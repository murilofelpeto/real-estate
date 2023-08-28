package com.felpeto.realestate.domain.property;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import com.jparams.verifier.tostring.ToStringVerifier;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PropertyTest {

  private static final String MANDATORY_FIELD = "[Property] | {0} is mandatory";
  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
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

    return Stream.of(Arguments.of(
            null,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "registration"),
            NullPointerException.class),
        Arguments.of(
            registration,
            null,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "propertyKind"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            null,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "address"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            null,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "size"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            null,
            sale,
            isFurnished,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "rent"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            null,
            isFurnished,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "sale"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            null,
            taxes,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "isFurnished"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            null,
            condominium,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "taxes"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            null,
            description,
            leisureItems,
            format(MANDATORY_FIELD, "condominium"),
            NullPointerException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            null,
            leisureItems,
            format(MANDATORY_FIELD, "description"),
            IllegalArgumentException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            "",
            leisureItems,
            format(MANDATORY_FIELD, "description"),
            IllegalArgumentException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            "  ",
            leisureItems,
            format(MANDATORY_FIELD, "description"),
            IllegalArgumentException.class),
        Arguments.of(
            registration,
            propertyKind,
            address,
            propertySize,
            rent,
            sale,
            isFurnished,
            taxes,
            condominium,
            description,
            null,
            "Cannot invoke \"java.util.Collection.toArray()\" because \"coll\" is null",
            NullPointerException.class));
  }

  private static Condominium createCondominium() {
    return Condominium.of(faker.bool().bool(),
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))),
        List.of(faker.options().option(LeisureItem.class)));
  }

  private static Sale createSale() {
    return Sale.of(
        true,
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))));
  }

  private static Rent createRent() {
    return Rent.of(
        true,
        Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100))));
  }

  private static PropertySize createPropertySize() {
    return PropertySize.builder()
        .buildingArea(Size.of(faker.number().numberBetween(1, 100)))
        .garage(Size.of(faker.number().numberBetween(1, 10)))
        .landSize(Size.of(faker.number().numberBetween(1, 100)))
        .rooms(Size.of(faker.number().numberBetween(1, 10)))
        .build();
  }

  private static Address createAddress() {
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

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Property.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Property.class)
        .suppress(Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  @DisplayName("Given valid information when create property then return valid property")
  void givenValidInformationWhenCreatePropertyThenReturnValidProperty() {
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
    final var property = Property.builder()
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

    assertThat(property).isNotNull()
        .satisfies(p -> {
          assertThat(p.uuid()).isEqualTo(uuid);
          assertThat(p.registration()).isEqualTo(registration);
          assertThat(p.propertyKind()).isEqualTo(propertyKind);
          assertThat(p.address()).isEqualTo(address);
          assertThat(p.size()).isEqualTo(propertySize);
          assertThat(p.rent()).isEqualTo(rent);
          assertThat(p.sale()).isEqualTo(sale);
          assertThat(p.isFurnished()).isEqualTo(isFurnished);
          assertThat(p.taxes()).isEqualTo(taxes);
          assertThat(p.condominium()).isEqualTo(condominium);
          assertThat(p.description()).isEqualTo(description);
          assertThat(p.items()).isEqualTo(leisureItems);
        });
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid information when create property then throws an exception")
  void givenInvalidInformationWhenCreatePropertyThenThrowsException(
      final Registration registration,
      final PropertyKind propertyKind,
      final Address address,
      final PropertySize propertySize,
      final Rent rent,
      final Sale sale,
      final Boolean isFurnished,
      final Money taxes,
      final Condominium condominium,
      final String description,
      final List<LeisureItem> leisureItems,
      final String message,
      final Class<?> exception) {

    assertThatThrownBy(() -> Property.builder()
        .uuid(UUID.randomUUID())
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
        .build())
        .hasMessage(message)
        .isExactlyInstanceOf(exception);
  }
}