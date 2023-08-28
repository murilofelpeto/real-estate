package com.felpeto.realestate.domain.property.vo;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class Sale {

  private final boolean isSale;
  private final Money price;


  private Sale(final boolean isSale, final Money price) {
    this.isSale = isSale;
    this.price = price;
  }

  public static Sale of(final boolean isSale, final Money price) {
    if (isSale) {
      requireNonNull(price, "Price is mandatory");
      return new Sale(true, price);
    }
    return new Sale(false, null);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Sale sale = (Sale) o;
    return Objects.equals(price, sale.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }
}
