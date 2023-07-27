package com.felpeto.realestate.domain.vo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum PropertyKind {

  RESIDENTIAL("single-family",
      "multi-family",
      "townhouses",
      "condominiums",
      "apartments",
      "manufactured"),
  COMMERCIAL("public",
      "retail",
      "office",
      "co-working"),
  LAND("land"),
  INDUSTRIAL("heavy-manufacturing",
      "light-manufacturing",
      "warehouses",
      "distribution-facilities");

  private static final String VALUE_NOT_FOUND = "PropertyKind | Desired value not found";
  private static final Map<String, PropertyKind> VALUE_MAP = new HashMap<>();

  static {
    for (PropertyKind kind : values()) {
      for (String kindValue : kind.kind) {
        VALUE_MAP.put(kindValue.toLowerCase(), kind);
      }
    }
  }

  private final String[] kind;

  PropertyKind(final String... kind) {
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

  public String[] getKind() {
    return kind.clone();
  }
}
