package com.felpeto.realestate.domain.vo;

import com.felpeto.realestate.domain.exception.InvalidFormatException;
import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
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

  private static final String INVALID_REGISTRATION_FORMAT = "Invalid registration format";
  private static final String FIELD = "value";
  private static final String TARGET = Registration.class.getSimpleName();
  private static final String VIOLATION_MESSAGE =
      "You must provide a registration following the pattern: " + REGEX;
  private static final String MANDATORY_FIELD = "Registration is mandatory";

  private final String value;

  private Registration(final String value) {
    this.value = value;
  }

  public static Registration of(final String value) {
    if (StringUtils.isBlank(value)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }

    if (!PATTERN.matcher(value).matches()) {
      throw new InvalidFormatException(INVALID_REGISTRATION_FORMAT,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }

    return new Registration(value.trim());
  }
}
