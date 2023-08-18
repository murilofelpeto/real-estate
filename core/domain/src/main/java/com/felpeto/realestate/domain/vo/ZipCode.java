package com.felpeto.realestate.domain.vo;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class ZipCode {

  private static final String MANDATORY_FIELD = "Zip-code is mandatory";
  private static final String FIELD = "ZipCode.value";
  private static final String TARGET = ZipCode.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Zip-code must not be blank or null";

  private final String value;

  private ZipCode(final String value) {
    this.value = value;
  }

  public static ZipCode of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }
    return new ZipCode(value);
  }
}
