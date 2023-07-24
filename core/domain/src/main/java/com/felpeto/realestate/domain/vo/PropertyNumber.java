package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import com.felpeto.realestate.domain.exception.InvalidPropertyNumberException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class PropertyNumber {

  private final Integer value;

  private PropertyNumber(final Integer value) {
    this.value = value;
  }

  public static PropertyNumber of(final Integer value) {
    requireNonNull(value, "Property number is mandatory");

    if(value <= 0) {
        throw new InvalidPropertyNumberException("Property number must be greater than 0");
    }
    return new PropertyNumber(value);
  }
}
