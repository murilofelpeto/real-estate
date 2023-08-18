package com.felpeto.realestate.domain.vo;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class State {

  private static final String MANDATORY_FIELD = "State is mandatory";
  private static final String FIELD = "State.value";
  private static final String TARGET = State.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The State name must not be blank or null";

  private final String value;

  private State(final String value) {
    this.value = value;
  }

  public static State of(final String value) {
    if (StringUtils.isBlank(value) || value.length() != 2) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }
    return new State(value);
  }
}
