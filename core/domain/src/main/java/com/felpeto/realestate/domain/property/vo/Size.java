package com.felpeto.realestate.domain.property.vo;

import static java.util.Objects.requireNonNull;

import com.felpeto.realestate.domain.exception.InvalidNumberLimitException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class Size {

  private static final String INVALID_NUMBER = "Size number must be greater than 0";
  private static final String FIELD = "Size.value";
  private static final String TARGET = Size.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "When you build a Size, you must provide a number greater than 0";
  private final Integer value;

  private Size(final Integer value) {
    this.value = value;
  }

  public static Size of(final Integer value) {
    requireNonNull(value, "Size is mandatory");
    if (value < 0) {
      throw new InvalidNumberLimitException(INVALID_NUMBER,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }

    return new Size(value);
  }
}
