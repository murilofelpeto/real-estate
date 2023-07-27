package com.felpeto.realestate.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class ZipCode {

  private final String value;

  private ZipCode(final String value) {
    this.value = value;
  }

  public static ZipCode of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new IllegalArgumentException("ZipCode is mandatory");
    }
    return new ZipCode(value);
  }
}
