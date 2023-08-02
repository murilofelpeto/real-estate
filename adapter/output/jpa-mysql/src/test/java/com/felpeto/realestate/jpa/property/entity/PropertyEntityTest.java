package com.felpeto.realestate.jpa.property.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PropertyEntityTest {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private static final Faker faker = new Faker();
  private static final BigDecimal ONE = BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP);
  private static final BigDecimal TEN = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);
  private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

  private static Stream<Arguments> invalidParams() {

    return Stream.of(
        Arguments.of(true, null,
            true, ONE,
            true, ONE,
            "Condominium price must be greater than 1"),
        Arguments.of(true, ZERO,
            true, ONE,
            true, ONE,
            "Condominium price must be greater than 1"),
        Arguments.of(true, ONE,
            true, null,
            true, ONE,
            "Rent price must be greater than 1"),
        Arguments.of(true, ONE,
            true, ZERO,
            true, ONE,
            "Rent price must be greater than 1"),
        Arguments.of(true, ONE,
            true, ONE,
            true, null,
            "Sale price must be greater than 1"),
        Arguments.of(true, ONE,
            true, ONE,
            true, ZERO,
            "Sale price must be greater than 1"),
        Arguments.of(true, ONE,
            false, ONE,
            false, ONE,
            "Rent or Sale must be true")
    );
  }

  @Test
  void validToString() {
    ToStringVerifier.forClass(PropertyEntity.class)
        .withClassName(NameStyle.SIMPLE_NAME)
        .verify();
  }

  @Test
  void validEqualsAndHashcode() {
    EqualsVerifier.simple().forClass(PropertyEntity.class)
        .withOnlyTheseFields("uuid", "registration")
        .verify();
  }

  @Test
  @DisplayName("Given no args when use no args constructor then return property entity")
  void givenNoArgsWhenUseNoArgsConstructorThenReturnPropertyEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rooms = faker.number().numberBetween(1, 100);
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();
    final var entity = new PropertyEntity();

    entity.setBuildingArea(buildingArea);
    entity.setCity(city);
    entity.setComplement(complement);
    entity.setCondominium(true);
    entity.setCondominiumLeisureItems(condominiumLeisureItem);
    entity.setCondominiumPrice(TEN);
    entity.setCountry(country);
    entity.setDescription(description);
    entity.setFurnished(isFurnished);
    entity.setGarage(garage);
    entity.setHouseNumber(houseNumber);
    entity.setPropertyId(id);
    entity.setLandSize(landSize);
    entity.setNeighborhood(neighborhood);
    entity.setPropertyKind(propertyKind);
    entity.setPropertyLeisureItems(propertyLeisureItem);
    entity.setRegistration(registration);
    entity.setRent(true);
    entity.setRentPrice(TEN);
    entity.setRooms(rooms);
    entity.setSale(true);
    entity.setSalePrice(TEN);
    entity.setState(state);
    entity.setStreetName(streetName);
    entity.setTaxes(TEN);
    entity.setUuid(uuid);
    entity.setZipcode(zipcode);

    assertThat(entity.getBuildingArea()).isEqualTo(buildingArea);
    assertThat(entity.getCity()).isEqualTo(city);
    assertThat(entity.getComplement()).isEqualTo(complement);
    assertThat(entity.getCondominiumLeisureItems()).hasSameElementsAs(condominiumLeisureItem);
    assertThat(entity.getCondominiumPrice()).isEqualTo(TEN);
    assertThat(entity.getCountry()).isEqualTo(country);
    assertThat(entity.getDescription()).isEqualTo(description);
    assertThat(entity.getGarage()).isEqualTo(garage);
    assertThat(entity.getHouseNumber()).isEqualTo(houseNumber);
    assertThat(entity.isCondominium()).isTrue();
    assertThat(entity.isFurnished()).isEqualTo(isFurnished);
    assertThat(entity.isRent()).isTrue();
    assertThat(entity.isSale()).isTrue();
    assertThat(entity.getLandSize()).isEqualTo(landSize);
    assertThat(entity.getNeighborhood()).isEqualTo(neighborhood);
    assertThat(entity.getPropertyKind()).isEqualTo(propertyKind);
    assertThat(entity.getPropertyLeisureItems()).hasSameElementsAs(propertyLeisureItem);
    assertThat(entity.getRegistration()).isEqualTo(registration);
    assertThat(entity.getRentPrice()).isEqualTo(TEN);
    assertThat(entity.getRooms()).isEqualTo(rooms);
    assertThat(entity.getSalePrice()).isEqualTo(TEN);
    assertThat(entity.getState()).isEqualTo(state);
    assertThat(entity.getStreetName()).isEqualTo(streetName);
    assertThat(entity.getTaxes()).isEqualTo(TEN);
    assertThat(entity.getUuid()).isEqualTo(uuid);
    assertThat(entity.getZipcode()).isEqualTo(zipcode);
  }

  @Test
  @DisplayName("Given valid information when build property entity then return valid property entity")
  void givenValidInformationWhenBuildPropertyEntityThenReturnValidPropertyEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var condominiumPrice = ONE;
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rentPrice = ONE;
    final var rooms = faker.number().numberBetween(1, 100);
    final var salePrice = ONE;
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    final var entity = PropertyEntity.builder()
        .id(id)
        .buildingArea(buildingArea)
        .city(city)
        .complement(complement)
        .condominiumLeisureItems(condominiumLeisureItem)
        .condominiumPrice(condominiumPrice)
        .country(country)
        .description(description)
        .garage(garage)
        .houseNumber(houseNumber)
        .isCondominium(true)
        .isFurnished(isFurnished)
        .isRent(true)
        .isSale(true)
        .landSize(landSize)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItem)
        .registration(registration)
        .rentPrice(rentPrice)
        .rooms(rooms)
        .salePrice(salePrice)
        .state(state)
        .streetName(streetName)
        .taxes(TEN)
        .uuid(uuid)
        .zipcode(zipcode)
        .build();

    assertThat(entity.getBuildingArea()).isEqualTo(buildingArea);
    assertThat(entity.getCity()).isEqualTo(city);
    assertThat(entity.getComplement()).isEqualTo(complement);
    assertThat(entity.getCondominiumLeisureItems()).hasSameElementsAs(condominiumLeisureItem);
    assertThat(entity.getCondominiumPrice()).isEqualTo(condominiumPrice);
    assertThat(entity.getCountry()).isEqualTo(country);
    assertThat(entity.getDescription()).isEqualTo(description);
    assertThat(entity.getGarage()).isEqualTo(garage);
    assertThat(entity.getHouseNumber()).isEqualTo(houseNumber);
    assertThat(entity.isCondominium()).isTrue();
    assertThat(entity.isFurnished()).isEqualTo(isFurnished);
    assertThat(entity.isRent()).isTrue();
    assertThat(entity.isSale()).isTrue();
    assertThat(entity.getLandSize()).isEqualTo(landSize);
    assertThat(entity.getNeighborhood()).isEqualTo(neighborhood);
    assertThat(entity.getPropertyKind()).isEqualTo(propertyKind);
    assertThat(entity.getPropertyLeisureItems()).hasSameElementsAs(propertyLeisureItem);
    assertThat(entity.getRegistration()).isEqualTo(registration);
    assertThat(entity.getRentPrice()).isEqualTo(rentPrice);
    assertThat(entity.getRooms()).isEqualTo(rooms);
    assertThat(entity.getSalePrice()).isEqualTo(salePrice);
    assertThat(entity.getState()).isEqualTo(state);
    assertThat(entity.getStreetName()).isEqualTo(streetName);
    assertThat(entity.getTaxes()).isEqualTo(TEN);
    assertThat(entity.getUuid()).isEqualTo(uuid);
    assertThat(entity.getZipcode()).isEqualTo(zipcode);

    assertThat(entity.getCondominiumPrice().scale()).isEqualTo(2);
    assertThat(entity.getRentPrice().scale()).isEqualTo(2);
    assertThat(entity.getSalePrice().scale()).isEqualTo(2);
    assertThat(entity.getTaxes().scale()).isEqualTo(2);
  }

  @Test
  @DisplayName("Given prices higher than One when build property entity then return valid property entity")
  void givenPricesHigherThanOneWhenBuildPropertyEntityThenReturnValidPropertyEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var condominiumPrice = TEN;
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rentPrice = TEN;
    final var rooms = faker.number().numberBetween(1, 100);
    final var salePrice = TEN;
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    final var entity = PropertyEntity.builder()
        .id(id)
        .buildingArea(buildingArea)
        .city(city)
        .complement(complement)
        .condominiumLeisureItems(condominiumLeisureItem)
        .condominiumPrice(condominiumPrice)
        .country(country)
        .description(description)
        .garage(garage)
        .houseNumber(houseNumber)
        .isCondominium(true)
        .isFurnished(isFurnished)
        .isRent(true)
        .isSale(true)
        .landSize(landSize)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItem)
        .registration(registration)
        .rentPrice(rentPrice)
        .rooms(rooms)
        .salePrice(salePrice)
        .state(state)
        .streetName(streetName)
        .taxes(TEN)
        .uuid(uuid)
        .zipcode(zipcode)
        .build();

    assertThat(entity.getCondominiumPrice()).isEqualTo(condominiumPrice);
    assertThat(entity.getRentPrice()).isEqualTo(rentPrice);
    assertThat(entity.getSalePrice()).isEqualTo(salePrice);
  }

  @Test
  @DisplayName("Given property not for sale and not condominium when build property entity then return valid property entity")
  void givenPropertyNotForSaleAndNotCondominiumWhenBuildPropertyEntityThenReturnValidPropertyEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rooms = faker.number().numberBetween(1, 100);
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    final var entity = PropertyEntity.builder()
        .id(id)
        .buildingArea(buildingArea)
        .city(city)
        .complement(complement)
        .condominiumLeisureItems(condominiumLeisureItem)
        .condominiumPrice(TEN)
        .country(country)
        .description(description)
        .garage(garage)
        .houseNumber(houseNumber)
        .isCondominium(false)
        .isFurnished(isFurnished)
        .isRent(true)
        .isSale(false)
        .landSize(landSize)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItem)
        .registration(registration)
        .rentPrice(TEN)
        .rooms(rooms)
        .salePrice(TEN)
        .state(state)
        .streetName(streetName)
        .taxes(TEN)
        .uuid(uuid)
        .zipcode(zipcode)
        .build();

    assertThat(entity.isRent()).isTrue();
    assertThat(entity.getRentPrice()).isEqualTo(TEN);

    assertThat(entity.isSale()).isFalse();
    assertThat(entity.getSalePrice()).isNull();

    assertThat(entity.isCondominium()).isFalse();
    assertThat(entity.getCondominiumPrice()).isNull();
    assertThat(entity.getCondominiumLeisureItems()).isEmpty();
  }

  @Test
  @DisplayName("Given property not for rent when build property entity then return valid property entity")
  void givenPropertyNotForRentWhenBuildPropertyEntityThenReturnValidPropertyEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rooms = faker.number().numberBetween(1, 100);
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    final var entity = PropertyEntity.builder()
        .id(id)
        .buildingArea(buildingArea)
        .city(city)
        .complement(complement)
        .condominiumLeisureItems(condominiumLeisureItem)
        .condominiumPrice(TEN)
        .country(country)
        .description(description)
        .garage(garage)
        .houseNumber(houseNumber)
        .isCondominium(true)
        .isFurnished(isFurnished)
        .isRent(false)
        .isSale(true)
        .landSize(landSize)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItem)
        .registration(registration)
        .rentPrice(TEN)
        .rooms(rooms)
        .salePrice(TEN)
        .state(state)
        .streetName(streetName)
        .taxes(TEN)
        .uuid(uuid)
        .zipcode(zipcode)
        .build();

    assertThat(entity.isRent()).isFalse();
    assertThat(entity.getRentPrice()).isNull();

    assertThat(entity.isSale()).isTrue();
    assertThat(entity.getSalePrice()).isEqualTo(TEN);

    assertThat(entity.isCondominium()).isTrue();
    assertThat(entity.getCondominiumPrice()).isEqualTo(TEN);
    assertThat(entity.getCondominiumLeisureItems()).hasSameElementsAs(condominiumLeisureItem);
  }

  @Test
  @DisplayName("Given entity without uuid when call pre persist then return entity with uuid")
  void givenEntityWithoutUuidWhenCallPrePersistThenReturnEntityWithUuid() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rooms = faker.number().numberBetween(1, 100);
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var zipcode = faker.address().zipCode();

    final var entity = PropertyEntity.builder()
        .id(id)
        .buildingArea(buildingArea)
        .city(city)
        .complement(complement)
        .condominiumLeisureItems(condominiumLeisureItem)
        .condominiumPrice(TEN)
        .country(country)
        .description(description)
        .garage(garage)
        .houseNumber(houseNumber)
        .isCondominium(true)
        .isFurnished(isFurnished)
        .isRent(false)
        .isSale(true)
        .landSize(landSize)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItem)
        .registration(registration)
        .rentPrice(TEN)
        .rooms(rooms)
        .salePrice(TEN)
        .state(state)
        .streetName(streetName)
        .taxes(TEN)
        .zipcode(zipcode)
        .build();

    assertThat(entity.getUuid()).isNull();

    entity.prePersist();

    assertThat(entity.getUuid()).isNotNull();

  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid params when build property entity then throw exception")
  void givenInvalidParamsWhenBuildPropertyEntityThenThrowException(
      final boolean isCondominium,
      final BigDecimal condominiumPrice,
      final boolean isRent,
      final BigDecimal rentPrice,
      final boolean isSale,
      final BigDecimal salePrice,
      final String message) {

    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final var id = faker.number().numberBetween(1L, 100L);
    final var buildingArea = faker.number().numberBetween(1, 100);
    final var city = faker.address().city();
    final var complement = faker.ancient().god();
    final var condominiumLeisureItem = createLeisureItemEntity();
    final var country = faker.address().country();
    final var description = faker.lorem().paragraph();
    final int garage = faker.number().numberBetween(1, 10);
    final var houseNumber = faker.number().numberBetween(1, 9999);
    final var isFurnished = faker.bool().bool();
    final var landSize = faker.number().numberBetween(1, 100);
    final var neighborhood = faker.rickAndMorty().location();
    final var propertyLeisureItem = createLeisureItemEntity();
    final var registration = faker.expression(faker.regexify(REGEX));
    final var rooms = faker.number().numberBetween(1, 100);
    final var state = faker.address().stateAbbr();
    final var streetName = faker.address().streetName();
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    assertThatThrownBy(() -> PropertyEntity.builder()
        .id(id)
        .buildingArea(buildingArea)
        .city(city)
        .complement(complement)
        .condominiumLeisureItems(condominiumLeisureItem)
        .condominiumPrice(condominiumPrice)
        .country(country)
        .description(description)
        .garage(garage)
        .houseNumber(houseNumber)
        .isCondominium(isCondominium)
        .isFurnished(isFurnished)
        .isRent(isRent)
        .isSale(isSale)
        .landSize(landSize)
        .neighborhood(neighborhood)
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItem)
        .registration(registration)
        .rentPrice(rentPrice)
        .rooms(rooms)
        .salePrice(salePrice)
        .state(state)
        .streetName(streetName)
        .taxes(TEN)
        .uuid(uuid)
        .zipcode(zipcode)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(message);
  }

  private Set<LeisureItemEntity> createLeisureItemEntity() {
    final var items = new HashSet<LeisureItemEntity>();
    for (int i = 0; i < 10; i++) {
      final var leisureItemEntity = LeisureItemEntity.builder()
          .item(faker.options().option(LeisureItem.class).getDescription())
          .leisureItemId(faker.number().numberBetween(1L, 100L))
          .uuid(UUID.randomUUID())
          .build();
      items.add(leisureItemEntity);
    }
    return items;
  }
}