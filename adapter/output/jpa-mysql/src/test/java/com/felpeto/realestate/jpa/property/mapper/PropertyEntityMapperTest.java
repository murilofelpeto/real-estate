package com.felpeto.realestate.jpa.property.mapper;

import static com.felpeto.realestate.jpa.property.mapper.PropertyEntityMapper.toProperties;
import static com.felpeto.realestate.jpa.property.mapper.PropertyEntityMapper.toProperty;
import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.jpa.property.entity.LeisureItemEntity;
import com.felpeto.realestate.jpa.property.entity.PropertyEntity;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PropertyEntityMapperTest {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private static final Faker faker = new Faker();
  private static final BigDecimal TEN = BigDecimal.TEN.setScale(2, RoundingMode.HALF_UP);

  @Test
  @DisplayName("Given Property entity when map to model then return property")
  void givenPropertyEntityWhenMapToModelThenReturnProperty() {
    final var entity = createEntity();

    final var property = toProperty(entity);

    final var address = property.address();
    final var condominium = property.condominium();
    final var rent = property.rent();
    final var sale = property.sale();
    final var propertySize = property.size();

    final var condominiumLeisureItems = entity.getCondominiumLeisureItems()
        .stream()
        .map(LeisureItemEntity::getItem)
        .map(LeisureItem::of)
        .toList();

    final var propertyLeisureItems = entity.getPropertyLeisureItems()
        .stream()
        .map(LeisureItemEntity::getItem)
        .map(LeisureItem::of)
        .toList();

    assertThat(address.city().getValue()).isEqualTo(entity.getCity());
    assertThat(address.complement()).isEqualTo(entity.getComplement());
    assertThat(address.country().getValue()).isEqualTo(entity.getCountry());
    assertThat(address.neighborhood().getValue()).isEqualTo(entity.getNeighborhood());
    assertThat(address.number().getValue()).isEqualTo(entity.getHouseNumber());
    assertThat(address.state().getValue()).isEqualTo(entity.getState());
    assertThat(address.streetName().getValue()).isEqualTo(entity.getStreetName());
    assertThat(address.zipCode().getValue()).isEqualTo(entity.getZipcode());
    assertThat(condominium.isCondominium()).isEqualTo(entity.isCondominium());
    assertThat(condominium.getItems()).isEqualTo(condominiumLeisureItems);
    assertThat(condominium.getPrice().getValue()).isEqualTo(entity.getCondominiumPrice());
    assertThat(property.description()).isEqualTo(entity.getDescription());
    assertThat(property.isFurnished()).isEqualTo(entity.isFurnished());
    assertThat(rent.isRent()).isEqualTo(entity.isRent());
    assertThat(rent.getPrice().getValue()).isEqualTo(entity.getRentPrice());
    assertThat(property.registration().getValue()).isEqualTo(entity.getRegistration());
    assertThat(sale.isSale()).isEqualTo(entity.isSale());
    assertThat(sale.getPrice().getValue()).isEqualTo(entity.getSalePrice());
    assertThat(propertySize.buildingArea().getValue()).isEqualTo(entity.getBuildingArea());
    assertThat(propertySize.garage().getValue()).isEqualTo(entity.getGarage());
    assertThat(propertySize.landSize().getValue()).isEqualTo(entity.getLandSize());
    assertThat(propertySize.rooms().getValue()).isEqualTo(entity.getRooms());
    assertThat(property.taxes().getValue()).isEqualTo(entity.getTaxes());
    assertThat(property.uuid()).isEqualTo(entity.getUuid());
    assertThat(property.items()).isEqualTo(propertyLeisureItems);
  }

  @Test
  @DisplayName("Given Properties entity when map to model then return List of properties")
  void givenPropertiesEntityWhenMapToModelThenReturnListOfProperties() {
    final var entity1 = createEntity();
    final var entity2 = createEntity();

    final var properties = toProperties(List.of(entity1, entity2));

    assertThat(properties).isNotEmpty()
        .hasSize(2);
  }

  private PropertyEntity createEntity() {
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
    final var taxes = BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100));
    final var uuid = UUID.randomUUID();
    final var zipcode = faker.address().zipCode();

    return PropertyEntity.builder()
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
        .isRent(true)
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
        .taxes(taxes)
        .uuid(uuid)
        .zipcode(zipcode)
        .build();
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