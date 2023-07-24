package com.felpeto.realestate.domain.vo;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@ToString
@EqualsAndHashCode
@FieldNameConstants(level = PRIVATE)
public class Condominium {

  private static final String MANDATORY_FIELD = "[Condominium] | {0} is mandatory";

  private final boolean isCondominium;
  private final Money price;
  private final List<LeisureItem> items;

  private Condominium(final boolean isCondominium,
      final Money price,
      final List<LeisureItem> items) {

    this.isCondominium = isCondominium;
    this.price = price;
    this.items = items;
  }

  public static Condominium of(final boolean isCondominium,
      final Money price,
      final List<LeisureItem> items) {
    if (isCondominium) {
      requireNonNull(price, format(MANDATORY_FIELD, Fields.price));
      requireNonNull(items, format(MANDATORY_FIELD, Fields.items));
      return new Condominium(true, price, items);
    }
    return new Condominium(false, null, null);
  }
}

