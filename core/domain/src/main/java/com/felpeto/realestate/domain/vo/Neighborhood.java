package com.felpeto.realestate.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public class Neighborhood {

  private final String value;

  private Neighborhood(final String value) {
    this.value = value;
  }

  public static Neighborhood of(final String value) {
    if(StringUtils.isBlank(value)) {
        throw new IllegalArgumentException("Neighborhood is mandatory");
    }

    return new Neighborhood(value);
  }
}
