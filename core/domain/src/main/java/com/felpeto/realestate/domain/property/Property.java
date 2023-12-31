package com.felpeto.realestate.domain.property;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.property.vo.Condominium;
import com.felpeto.realestate.domain.property.vo.LeisureItem;
import com.felpeto.realestate.domain.property.vo.Money;
import com.felpeto.realestate.domain.property.vo.PropertyKind;
import com.felpeto.realestate.domain.property.vo.Registration;
import com.felpeto.realestate.domain.property.vo.Rent;
import com.felpeto.realestate.domain.property.vo.Sale;
import com.felpeto.realestate.domain.vo.Address;
import java.util.List;
import java.util.UUID;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;

@FieldNameConstants(level = PRIVATE)
public record Property(UUID uuid,
                       Registration registration,
                       PropertyKind propertyKind,
                       Address address,
                       PropertySize size,
                       Rent rent,
                       Sale sale,
                       Boolean isFurnished,
                       Money taxes,
                       Condominium condominium,
                       String description,
                       List<LeisureItem> items) {

  private static final String MANDATORY_FIELD = "[Property] | {0} is mandatory";

  public Property(UUID uuid,
      Registration registration,
      PropertyKind propertyKind,
      Address address,
      PropertySize size,
      Rent rent,
      Sale sale,
      Boolean isFurnished,
      Money taxes,
      Condominium condominium,
      String description,
      List<LeisureItem> items) {
    this.registration = requireNonNull(registration, format(MANDATORY_FIELD, Fields.registration));
    this.propertyKind = requireNonNull(propertyKind, format(MANDATORY_FIELD, Fields.propertyKind));
    this.address = requireNonNull(address, format(MANDATORY_FIELD, Fields.address));
    this.size = requireNonNull(size, format(MANDATORY_FIELD, Fields.size));
    this.rent = requireNonNull(rent, format(MANDATORY_FIELD, Fields.rent));
    this.sale = requireNonNull(sale, format(MANDATORY_FIELD, Fields.sale));
    this.isFurnished = requireNonNull(isFurnished, format(MANDATORY_FIELD, Fields.isFurnished));
    this.taxes = requireNonNull(taxes, format(MANDATORY_FIELD, Fields.taxes));
    this.condominium = requireNonNull(condominium, format(MANDATORY_FIELD, Fields.condominium));
    this.items = List.copyOf(items);

    if (StringUtils.isBlank(description)) {
      throw new IllegalArgumentException(format(MANDATORY_FIELD, Fields.description));
    }

    this.description = description;
    this.uuid = uuid;

  }

  public static PropertyBuilder builder() {
    return new PropertyBuilder();
  }

  public static class PropertyBuilder {

    private UUID uuid;
    private Registration registration;
    private PropertyKind propertyKind;
    private Address address;
    private PropertySize size;
    private Rent rent;
    private Sale sale;
    private Boolean isFurnished;
    private Money taxes;
    private Condominium condominium;
    private String description;
    private List<LeisureItem> items;

    public PropertyBuilder uuid(UUID uuid) {
      this.uuid = uuid;
      return this;
    }

    public PropertyBuilder registration(Registration registration) {
      this.registration = registration;
      return this;
    }

    public PropertyBuilder propertyKind(PropertyKind propertyKind) {
      this.propertyKind = propertyKind;
      return this;
    }

    public PropertyBuilder address(Address address) {
      this.address = address;
      return this;
    }

    public PropertyBuilder size(PropertySize size) {
      this.size = size;
      return this;
    }

    public PropertyBuilder rent(Rent rent) {
      this.rent = rent;
      return this;
    }

    public PropertyBuilder sale(Sale sale) {
      this.sale = sale;
      return this;
    }

    public PropertyBuilder isFurnished(Boolean isFurnished) {
      this.isFurnished = isFurnished;
      return this;
    }

    public PropertyBuilder taxes(Money taxes) {
      this.taxes = taxes;
      return this;
    }

    public PropertyBuilder condominium(Condominium condominium) {
      this.condominium = condominium;
      return this;
    }

    public PropertyBuilder description(String description) {
      this.description = description;
      return this;
    }

    public PropertyBuilder items(List<LeisureItem> items) {
      this.items = List.copyOf(items);
      return this;
    }

    public Property build() {
      return new Property(uuid,
          registration,
          propertyKind,
          address,
          size,
          rent,
          sale,
          isFurnished,
          taxes,
          condominium,
          description,
          items
      );
    }
  }
}
