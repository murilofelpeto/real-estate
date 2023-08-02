package com.felpeto.realestate.jpa.property.mapper;

import static lombok.AccessLevel.PRIVATE;

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
import com.felpeto.realestate.jpa.property.entity.LeisureItemEntity;
import com.felpeto.realestate.jpa.property.entity.PropertyEntity;
import java.util.List;
import java.util.Set;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PropertyEntityMapper {

  public static List<Property> toProperties(final List<PropertyEntity> propertyEntities) {
    return propertyEntities.stream().map(PropertyEntityMapper::toProperty).toList();
  }

  public static Property toProperty(final PropertyEntity entity) {
    return Property.builder()
        .address(toAddress(entity))
        .condominium(toCondominium(entity))
        .description(entity.getDescription())
        .isFurnished(entity.isFurnished())
        .items(toLeisureItems(entity.getPropertyLeisureItems()))
        .propertyKind(toPropertyKind(entity.getPropertyKind()))
        .registration(Registration.of(entity.getRegistration()))
        .rent(toRent(entity))
        .sale(toSale(entity))
        .size(toSize(entity))
        .taxes(Money.of(entity.getTaxes()))
        .uuid(entity.getUuid())
        .build();
  }

  private static PropertySize toSize(final PropertyEntity entity) {
    return PropertySize.builder()
        .buildingArea(Size.of(entity.getBuildingArea()))
        .garage(Size.of(entity.getGarage()))
        .landSize(Size.of(entity.getLandSize()))
        .rooms(Size.of(entity.getRooms()))
        .build();
  }

  private static Sale toSale(final PropertyEntity entity) {
    return Sale.of(entity.isSale(), Money.of(entity.getSalePrice()));
  }

  private static Rent toRent(final PropertyEntity entity) {
    return Rent.of(entity.isRent(), Money.of(entity.getRentPrice()));
  }

  private static PropertyKind toPropertyKind(final String propertyKind) {
    return PropertyKind.of(propertyKind);
  }

  private static List<LeisureItem> toLeisureItems(
      final Set<LeisureItemEntity> propertyLeisureItems) {
    return propertyLeisureItems.stream()
        .map(LeisureItemEntity::getItem)
        .map(LeisureItem::of).toList();
  }

  private static Condominium toCondominium(final PropertyEntity entity) {
    return Condominium.of(
        entity.isCondominium(),
        Money.of(entity.getCondominiumPrice()),
        toLeisureItems(entity.getCondominiumLeisureItems()));
  }

  private static Address toAddress(final PropertyEntity entity) {
    return Address.builder()
        .city(City.of(entity.getCity()))
        .complement(entity.getComplement())
        .country(Country.of(entity.getCountry()))
        .neighborhood(Neighborhood.of(entity.getNeighborhood()))
        .number(PropertyNumber.of(entity.getHouseNumber()))
        .state(State.of(entity.getState()))
        .streetName(StreetName.of(entity.getStreetName()))
        .zipCode(ZipCode.of(entity.getZipcode()))
        .build();
  }
}
