package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class Money {

  private final BigDecimal value;

  private Money(final BigDecimal value) {
    this.value = value;
  }

  public static Money of(final BigDecimal value) {
    requireNonNull(value, "Money is mandatory");
    if (BigDecimal.ONE.compareTo(value) > 0) {
      throw new IllegalArgumentException("value must be greater than or equal to 1: " + value);
    }
    return new Money(value.setScale(2, RoundingMode.HALF_UP));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Money money = (Money) o;
    return value.compareTo(money.value) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value.stripTrailingZeros());
  }
}
