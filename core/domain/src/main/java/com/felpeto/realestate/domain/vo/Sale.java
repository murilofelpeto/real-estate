package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Sale {


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
}
