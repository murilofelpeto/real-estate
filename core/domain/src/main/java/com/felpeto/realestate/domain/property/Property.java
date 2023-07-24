package com.felpeto.realestate.domain.property;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.vo.Address;
import com.felpeto.realestate.domain.vo.Condominium;
import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.Money;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.Registration;
import com.felpeto.realestate.domain.vo.Rent;
import com.felpeto.realestate.domain.vo.Sale;
import java.util.List;
import lombok.Builder;
import lombok.experimental.FieldNameConstants;
import org.apache.commons.lang3.StringUtils;

@FieldNameConstants(level = PRIVATE)
public record Property(Registration registration,
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

  @Builder
  public Property(final Registration registration,
      final PropertyKind propertyKind,
      final Address address,
      final PropertySize size,
      final Rent rent,
      final Sale sale,
      final Boolean isFurnished,
      final Money taxes,
      final Condominium condominium,
      final String description,
      final List<LeisureItem> items) {

    this.registration = requireNonNull(registration, format(MANDATORY_FIELD, Fields.registration));
    this.propertyKind = requireNonNull(propertyKind, format(MANDATORY_FIELD, Fields.propertyKind));
    this.address = requireNonNull(address, format(MANDATORY_FIELD, Fields.address));
    this.size = requireNonNull(size, format(MANDATORY_FIELD, Fields.size));
    this.rent = requireNonNull(rent, format(MANDATORY_FIELD, Fields.rent));
    this.sale = requireNonNull(sale, format(MANDATORY_FIELD, Fields.sale));
    this.isFurnished = requireNonNull(isFurnished, format(MANDATORY_FIELD, Fields.isFurnished));
    this.taxes = requireNonNull(taxes, format(MANDATORY_FIELD, Fields.taxes));
    this.condominium = requireNonNull(condominium, format(MANDATORY_FIELD, Fields.condominium));
    this.items = requireNonNull(items, format(MANDATORY_FIELD, Fields.items));

    if (StringUtils.isBlank(description)) {
      throw new IllegalArgumentException(format(MANDATORY_FIELD, Fields.description));
    }

    this.description = requireNonNull(description, format(MANDATORY_FIELD, Fields.description));
  }
}
