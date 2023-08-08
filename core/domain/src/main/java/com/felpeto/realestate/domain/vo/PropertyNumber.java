package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import com.felpeto.realestate.domain.exception.InvalidPropertyNumberException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class PropertyNumber {

  private static final String INVALID_NUMBER = "Property number must be greater than 0";
  private static final String FIELD = "Property Number";
  private static final String TARGET = PropertyNumber.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "When you build a Property number, you must provide a number greater than 0";
  private final Integer value;

  private PropertyNumber(final Integer value) {
    this.value = value;
  }

  public static PropertyNumber of(final Integer value) {
    requireNonNull(value, "Property number is mandatory");

    if (value <= 0) {
      throw new InvalidPropertyNumberException(INVALID_NUMBER,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }
    return new PropertyNumber(value);
  }
}
