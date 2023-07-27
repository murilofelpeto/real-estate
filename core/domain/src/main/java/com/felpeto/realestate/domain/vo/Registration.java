package com.felpeto.realestate.domain.vo;

import com.felpeto.realestate.domain.exception.InvalidFormatException;
import java.util.regex.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class Registration {

  private static final String REGEX = "^[0-9]{5}.[0-9]{1}.[0-9]{7}-[0-9]{2}$";
  private static final Pattern PATTERN = Pattern.compile(REGEX);

  private final String value;

  private Registration(final String value) {
    this.value = value;
  }

  public static Registration of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new IllegalArgumentException("Registration is mandatory");
    }

    if (!PATTERN.matcher(value).matches()) {
      throw new InvalidFormatException("Invalid registration format");
    }

    return new Registration(value.trim());
  }
}
