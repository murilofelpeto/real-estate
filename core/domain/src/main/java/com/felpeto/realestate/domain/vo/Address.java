package com.felpeto.realestate.domain.vo;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(level = PRIVATE)
public record Address(Country country,
                      State state,
                      City city,
                      Neighborhood neighborhood,
                      ZipCode zipCode,
                      StreetName streetName,
                      PropertyNumber number,
                      String complement) {

  private static final String MANDATORY_FIELD = "[Address] | {0} is mandatory";

  public Address(final Country country,
      final State state,
      final City city,
      final Neighborhood neighborhood,
      final ZipCode zipCode,
      final StreetName streetName,
      final PropertyNumber number,
      final String complement) {

    this.country = requireNonNull(country, format(MANDATORY_FIELD, Fields.country));
    this.state = requireNonNull(state, format(MANDATORY_FIELD, Fields.state));
    this.city = requireNonNull(city, format(MANDATORY_FIELD, Fields.city));
    this.neighborhood = requireNonNull(neighborhood, format(MANDATORY_FIELD, Fields.neighborhood));
    this.zipCode = requireNonNull(zipCode, format(MANDATORY_FIELD, Fields.zipCode));
    this.streetName = requireNonNull(streetName, format(MANDATORY_FIELD, Fields.streetName));
    this.number = requireNonNull(number, format(MANDATORY_FIELD, Fields.number));
    this.complement = complement.trim();
  }
}
