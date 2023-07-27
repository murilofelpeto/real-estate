package com.felpeto.realestate.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class StreetName {

  private final String value;

  private StreetName(final String value) {
    this.value = value;
  }

  public static StreetName of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new IllegalArgumentException("StreetName is mandatory");
    }
    return new StreetName(value);
  }
}
