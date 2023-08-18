package com.felpeto.realestate.jpa.property.entity;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "property", indexes = {
    @Index(name = "idx_property_entity", columnList = "property_id, uuid, registration")})
@NoArgsConstructor
public class PropertyEntity {

  private static final BigDecimal ONE = BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP);
  @Id
  @Column(name = "property_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long propertyId;

  @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
  private UUID uuid;

  @Column(name = "registration", nullable = false, unique = true)
  private String registration;

  @Column(name = "property_kind", nullable = false)
  private String propertyKind;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "condominium_leisure_item",
      joinColumns =
      @JoinColumn(name = "property_id"),
      inverseJoinColumns = @JoinColumn(name = "leisure_item_id"))
  private Set<LeisureItemEntity> condominiumLeisureItems;

  @Column(name = "country", nullable = false, length = 100)
  private String country;

  @Column(name = "state", nullable = false, length = 2)
  private String state;

  @Column(name = "city", nullable = false, length = 100)
  private String city;

  @Column(name = "neighborhood", length = 150)
  private String neighborhood;

  @Column(name = "zipcode", nullable = false, length = 50)
  private String zipcode;

  @Column(name = "street_name", nullable = false)
  private String streetName;

  @Column(name = "house_number", nullable = false, length = 6)
  private Integer houseNumber;

  @Column(name = "complement", length = 100)
  private String complement;

  @Column(name = "land_size", nullable = false)
  private Integer landSize;

  @Column(name = "building_area", nullable = false)
  private Integer buildingArea;

  @Column(name = "garage", nullable = false)
  private Integer garage;

  @Column(name = "rooms", nullable = false)
  private Integer rooms;

  @Column(name = "isRent")
  private boolean isRent;

  @Column(name = "rentPrice", scale = 2)
  private BigDecimal rentPrice;

  @Column(name = "isSale")
  private boolean isSale;

  @Column(name = "salePrice", scale = 2)
  private BigDecimal salePrice;

  @Column(name = "isFurnished")
  private boolean isFurnished;

  @Column(name = "taxes", nullable = false)
  private BigDecimal taxes;

  @Column(name = "isCondominium")
  private boolean isCondominium;

  @Column(name = "condominium_price", scale = 2)
  private BigDecimal condominiumPrice;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "property_leisure_item",
      joinColumns =
      @JoinColumn(name = "property_id"),
      inverseJoinColumns = @JoinColumn(name = "leisure_item_id"))
  private Set<LeisureItemEntity> propertyLeisureItems;

  @Column(name = "description", nullable = false)
  private String description;

  public PropertyEntity(
      final Long propertyId,
      final UUID uuid,
      final String registration,
      final String propertyKind,
      final Set<LeisureItemEntity> propertyLeisureItems,
      final String country,
      final String state,
      final String city,
      final String neighborhood,
      final String zipcode,
      final String streetName,
      final Integer houseNumber,
      final String complement,
      final Integer landSize,
      final Integer buildingArea,
      final Integer garage,
      final Integer rooms,
      final boolean isRent,
      final BigDecimal rentPrice,
      final boolean isSale,
      final BigDecimal salePrice,
      final boolean isFurnished,
      final BigDecimal taxes,
      final boolean isCondominium,
      final BigDecimal condominiumPrice,
      final Set<LeisureItemEntity> condominiumLeisureItems,
      final String description) {

    this.propertyId = propertyId;
    this.uuid = uuid;
    this.registration = registration;
    this.propertyKind = propertyKind;
    this.propertyLeisureItems = unmodifiableSet(propertyLeisureItems);
    this.country = country;
    this.state = state;
    this.city = city;
    this.neighborhood = neighborhood;
    this.zipcode = zipcode;
    this.streetName = streetName;
    this.houseNumber = houseNumber;
    this.complement = complement;
    this.landSize = landSize;
    this.buildingArea = buildingArea;
    this.garage = garage;
    this.rooms = rooms;
    this.isRent = isRent;
    this.rentPrice = defaultScale(rentPrice);
    this.isSale = isSale;
    this.salePrice = defaultScale(salePrice);
    this.isFurnished = isFurnished;
    this.taxes = defaultScale(taxes);
    this.isCondominium = isCondominium;
    this.condominiumPrice = defaultScale(condominiumPrice);
    this.condominiumLeisureItems = unmodifiableSet(condominiumLeisureItems);
    this.description = description;

    validateRentOrSale(isRent, isSale);
    validateCondominiumPrice(isCondominium);
    validatePrices(isRent, rentPrice, isSale, salePrice, isCondominium, condominiumPrice);
  }

  public static PropertyEntityBuilder builder() {
    return new PropertyEntityBuilder();
  }

  public Set<LeisureItemEntity> getCondominiumLeisureItems() {
    return unmodifiableSet(condominiumLeisureItems);
  }

  public Set<LeisureItemEntity> getPropertyLeisureItems() {
    return unmodifiableSet(propertyLeisureItems);
  }

  private void validatePrices(
      final boolean isRent,
      final BigDecimal rentPrice,
      final boolean isSale,
      final BigDecimal salePrice,
      final boolean isCondominium,
      final BigDecimal condominiumPrice) {

    if (isRent
        && rentPrice == null
        || ONE.compareTo(rentPrice) > 0) {

      throw new IllegalArgumentException("Rent price must be greater than 1");
    }

    if (isSale
        && salePrice == null
        || ONE.compareTo(salePrice) > 0) {

      throw new IllegalArgumentException("Sale price must be greater than 1");
    }

    if (isCondominium
        && condominiumPrice == null
        || ONE.compareTo(condominiumPrice) > 0) {

      throw new IllegalArgumentException("Condominium price must be greater than 1");
    }
  }

  private void validateRentOrSale(final boolean isRent, final boolean isSale) {
    if (!isRent && !isSale) {
      throw new IllegalArgumentException("Rent or Sale must be true");
    }

    if (!isRent) {
      this.rentPrice = null;
    }

    if (!isSale) {
      this.salePrice = null;
    }
  }

  private void validateCondominiumPrice(final boolean isCondominium) {
    if (!isCondominium) {
      this.condominiumPrice = null;
      this.condominiumLeisureItems = emptySet();
    }
  }

  private BigDecimal defaultScale(BigDecimal value) {
    return value != null ? value.setScale(2, RoundingMode.HALF_UP) : null;
  }

  @PrePersist
  public void prePersist() {
    this.uuid = UUID.randomUUID();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final PropertyEntity that = (PropertyEntity) o;
    return Objects.equals(uuid, that.uuid) && Objects.equals(registration,
        that.registration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, registration);
  }
}
