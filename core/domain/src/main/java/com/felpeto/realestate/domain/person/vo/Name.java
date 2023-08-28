package com.felpeto.realestate.domain.person.vo;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class Name {

  private static final String MANDATORY_FIELD = "Name is mandatory";
  private static final String FIELD = "Name.value";
  private static final String TARGET = Name.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Name must not be blank or null";

  private final String value;

  private Name(final String value) {
    this.value = value;
  }

  public static Name of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }
    return new Name(value);
  }
}
