package com.felpeto.realestate.domain.vo;

import static java.text.MessageFormat.format;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.felpeto.realestate.domain.exception.ValueNotFoundException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum PropertyKind {

  RESIDENTIAL("residential"),
  COMMERCIAL("commercial"),
  LAND("land"),
  INDUSTRIAL("industrial");

  private static final Map<String, PropertyKind> VALUE_MAP = new HashMap<>();

  private static final String MANDATORY_FIELD = "property kind is mandatory";
  private static final String FIELD = "PropertyKind.kind";
  private static final String TARGET = PropertyKind.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "You must inform a valid property kind";
  private static final String DESCRIPTION_NOT_FOUND = "property kind not found: {0}";

  static {
    for (PropertyKind propertyKind : values()) {
      VALUE_MAP.put(propertyKind.kind.toLowerCase(), propertyKind);
    }
  }

  private final String kind;

  PropertyKind(final String kind) {
    this.kind = kind;
  }

  public static PropertyKind of(final String propertyKind) {
    if (StringUtils.isBlank(propertyKind)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }

    if (!VALUE_MAP.containsKey(propertyKind.toLowerCase())) {
      throw new ValueNotFoundException(format(DESCRIPTION_NOT_FOUND, propertyKind),
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }

    return VALUE_MAP.get(propertyKind.toLowerCase());
  }
}
