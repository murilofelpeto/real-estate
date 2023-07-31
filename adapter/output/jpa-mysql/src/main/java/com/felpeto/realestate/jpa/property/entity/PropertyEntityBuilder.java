package com.felpeto.realestate.jpa.property.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PropertyEntityBuilder {

  private Long id;
  private UUID uuid;
  private String registration;
  private String propertyKind;
  private List<String> propertyLeisureItems;
  private String country;
  private String state;
  private String city;
  private String neighborhood;
  private String zipcode;
  private String streetName;
  private Integer houseNumber;
  private String complement;
  private Integer landSize;
  private Integer buildingArea;
  private Integer garage;
  private Integer rooms;
  private boolean isRent;
  private BigDecimal rentPrice;
  private boolean isSale;
  private BigDecimal salePrice;
  private boolean isFurnished;
  private BigDecimal taxes;
  private boolean isCondominium;
  private BigDecimal condominiumPrice;
  private List<String> condominiumLeisureItems;
  private String description;

  public PropertyEntityBuilder id(final Long id) {
    this.id = id;
    return this;
  }

  public PropertyEntityBuilder uuid(final UUID uuid) {
    this.uuid = uuid;
    return this;
  }

  public PropertyEntityBuilder registration(final String registration) {
    this.registration = registration;
    return this;
  }

  public PropertyEntityBuilder propertyKind(final String propertyKind) {
    this.propertyKind = propertyKind;
    return this;
  }

  public PropertyEntityBuilder propertyLeisureItems(final List<String> propertyLeisureItems) {
    this.propertyLeisureItems = List.copyOf(propertyLeisureItems);
    return this;
  }

  public PropertyEntityBuilder country(final String country) {
    this.country = country;
    return this;
  }

  public PropertyEntityBuilder state(final String state) {
    this.state = state;
    return this;
  }

  public PropertyEntityBuilder city(final String city) {
    this.city = city;
    return this;
  }

  public PropertyEntityBuilder neighborhood(final String neighborhood) {
    this.neighborhood = neighborhood;
    return this;
  }

  public PropertyEntityBuilder zipcode(final String zipcode) {
    this.zipcode = zipcode;
    return this;
  }

  public PropertyEntityBuilder streetName(final String streetName) {
    this.streetName = streetName;
    return this;
  }

  public PropertyEntityBuilder houseNumber(final Integer houseNumber) {
    this.houseNumber = houseNumber;
    return this;
  }

  public PropertyEntityBuilder complement(final String complement) {
    this.complement = complement;
    return this;
  }

  public PropertyEntityBuilder landSize(final Integer landSize) {
    this.landSize = landSize;
    return this;
  }

  public PropertyEntityBuilder buildingArea(final Integer buildingArea) {
    this.buildingArea = buildingArea;
    return this;
  }

  public PropertyEntityBuilder garage(final Integer garage) {
    this.garage = garage;
    return this;
  }

  public PropertyEntityBuilder rooms(final Integer rooms) {
    this.rooms = rooms;
    return this;
  }

  public PropertyEntityBuilder isRent(final boolean isRent) {
    this.isRent = isRent;
    return this;
  }

  public PropertyEntityBuilder rentPrice(final BigDecimal rentPrice) {
    this.rentPrice = rentPrice;
    return this;
  }

  public PropertyEntityBuilder isSale(final boolean isSale) {
    this.isSale = isSale;
    return this;
  }

  public PropertyEntityBuilder salePrice(final BigDecimal salePrice) {
    this.salePrice = salePrice;
    return this;
  }

  public PropertyEntityBuilder isFurnished(final boolean isFurnished) {
    this.isFurnished = isFurnished;
    return this;
  }

  public PropertyEntityBuilder taxes(final BigDecimal taxes) {
    this.taxes = taxes;
    return this;
  }

  public PropertyEntityBuilder isCondominium(final boolean isCondominium) {
    this.isCondominium = isCondominium;
    return this;
  }

  public PropertyEntityBuilder condominiumPrice(final BigDecimal condominiumPrice) {
    this.condominiumPrice = condominiumPrice;
    return this;
  }

  public PropertyEntityBuilder condominiumLeisureItems(final List<String> condominiumLeisureItems) {
    this.condominiumLeisureItems = List.copyOf(condominiumLeisureItems);
    return this;
  }

  public PropertyEntityBuilder description(final String description) {
    this.description = description;
    return this;
  }

  public PropertyEntity build() {
    return new PropertyEntity(id, uuid, registration, propertyKind, propertyLeisureItems, country,
        state, city, neighborhood, zipcode, streetName, houseNumber, complement, landSize,
        buildingArea, garage, rooms, isRent, rentPrice, isSale, salePrice, isFurnished, taxes,
        isCondominium, condominiumPrice, condominiumLeisureItems, description);
  }
}