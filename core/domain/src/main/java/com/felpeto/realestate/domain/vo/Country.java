package com.felpeto.realestate.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class Country {

  private final String value;

  private Country(final String value) {
    this.value = value;
  }

  public static Country of(final String value) {
    if(StringUtils.isBlank(value)) {
      throw new IllegalArgumentException("Country is mandatory");
    }
    return new Country(value);
  }
}
