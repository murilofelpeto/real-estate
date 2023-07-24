package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Rent {

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
}
