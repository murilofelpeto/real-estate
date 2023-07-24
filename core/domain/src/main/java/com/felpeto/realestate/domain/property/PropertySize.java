package com.felpeto.realestate.domain.property;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.vo.Size;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants(level = PRIVATE)
public record PropertySize(Size landSize,
                           Size buildingArea,
                           Size garage,
                           Size rooms) {

  private static final String MANDATORY_FIELD = "[PropertySize] | {0} is mandatory";

  public PropertySize(final Size landSize,
      final Size buildingArea,
      final Size garage,
      final Size rooms) {

    this.landSize = requireNonNull(landSize, format(MANDATORY_FIELD, Fields.landSize));
    this.buildingArea = requireNonNull(buildingArea, format(MANDATORY_FIELD, Fields.buildingArea));
    this.garage = requireNonNull(garage, format(MANDATORY_FIELD, Fields.garage));
    this.rooms = requireNonNull(rooms, format(MANDATORY_FIELD, Fields.rooms));
  }
}
