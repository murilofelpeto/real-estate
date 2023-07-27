package com.felpeto.realestate.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class State {

  private final String value;

  private State(final String value) {
    this.value = value;
  }

  public static State of(final String value) {
    if (StringUtils.isBlank(value) || value.length() != 2) {
      throw new IllegalArgumentException("State is mandatory and must be abbreviated");
    }
    return new State(value);
  }
}
