package com.felpeto.realestate.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class City {

  private final String value;

  private City(final String value) {
    this.value = value;
  }

  public static City of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new IllegalArgumentException("City is mandatory");
    }
    return new City(value);
  }
}
