package com.felpeto.realestate.domain.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum PropertyKind {

  RESIDENTIAL("residential"),
  COMMERCIAL("commercial"),
  LAND("land"),
  INDUSTRIAL("industrial");

  private static final String VALUE_NOT_FOUND = "PropertyKind | Desired value not found";
  private static final Map<String, PropertyKind> VALUE_MAP = new HashMap<>();

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
    Objects.requireNonNull(propertyKind, VALUE_NOT_FOUND);
    String lowercaseKind = propertyKind.toLowerCase();
    PropertyKind result = VALUE_MAP.get(lowercaseKind);
    if (result == null) {
      throw new IllegalArgumentException(VALUE_NOT_FOUND);
    }
    return result;
  }
}
