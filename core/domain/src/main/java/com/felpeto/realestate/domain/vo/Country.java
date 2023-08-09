package com.felpeto.realestate.domain.vo;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class Country {

  private static final String MANDATORY_FIELD = "Country is mandatory";
  private static final String FIELD = "Country.value";
  private static final String TARGET = Country.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Country name must not be blank or null";

  private final String value;

  private Country(final String value) {
    this.value = value;
  }

  public static Country of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }
    return new Country(value);
  }
}
