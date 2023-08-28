package com.felpeto.realestate.domain.property.vo;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class Rent {

  private final boolean isRent;
  private final Money price;

  private Rent(final boolean isRent, final Money price) {
    this.isRent = isRent;
    this.price = price;
  }

  public static Rent of(final boolean isRent, final Money price) {
    if (isRent) {
      requireNonNull(price, "Price is mandatory");
      return new Rent(true, price);
    }
    return new Rent(false, null);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Rent rent = (Rent) o;
    return Objects.equals(price, rent.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }
}
