package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class Size {

  private final Integer value;

  private Size(final Integer value) {
    this.value = value;
  }

  public static Size of(final Integer value) {
    requireNonNull(value, "Size is mandatory");
    if (value < 0) {
      throw new IllegalArgumentException("Size must be positive");
    }

    return new Size(value);
  }
}
