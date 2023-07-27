package com.felpeto.realestate.domain.vo;

import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@ToString
@FieldNameConstants(level = PRIVATE)
public final class Condominium {

  private static final String MANDATORY_FIELD = "[Condominium] | {0} is mandatory";

  private final boolean isCondominium;
  private final Money price;
  private final List<LeisureItem> items;

  private Condominium(final boolean isCondominium,
      final Money price,
      final List<LeisureItem> items) {

    this.isCondominium = isCondominium;
    this.price = price;
    this.items = unmodifiableList(items);
  }

  public static Condominium of(final boolean isCondominium,
      final Money price,
      final List<LeisureItem> items) {

    if (isCondominium) {
      requireNonNull(price, format(MANDATORY_FIELD, Fields.price));
      requireNonNull(items, format(MANDATORY_FIELD, Fields.items));
      return new Condominium(true, price, items);
    }
    return new Condominium(false, null, emptyList());
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Condominium that = (Condominium) o;
    return Objects.equals(price, that.price) && Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, items);
  }
}

