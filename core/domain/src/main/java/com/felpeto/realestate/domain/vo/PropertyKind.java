package com.felpeto.realestate.domain.vo;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
  private final List<String> kind;

  PropertyKind(final String... kind) {
    this.kind = List.of(kind);
  }

  public static PropertyKind of(final String propertyKind) {
    requireNonNull(propertyKind, VALUE_NOT_FOUND);
    return Arrays.stream(values())
        .filter(property -> property.kind.contains(propertyKind.toLowerCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(VALUE_NOT_FOUND));
  }

  public List<String> getKind() {
    return new ArrayList<>(kind);
  }
}
