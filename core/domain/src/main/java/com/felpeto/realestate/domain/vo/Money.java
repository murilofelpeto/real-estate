package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Money {

private final BigDecimal value;

  private Money(final BigDecimal value) {
    this.value = value;
  }

  public static Money of(final BigDecimal value) {
    requireNonNull(value, "value is mandatory");
    if(BigDecimal.ONE.compareTo(value) < 0) {
        throw new IllegalArgumentException("value must be greater than or equal to 1");
    }
    return new Money(value);
  }
}
