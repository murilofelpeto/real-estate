package com.felpeto.realestate.domain.person.vo;

import static java.text.MessageFormat.format;
import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.exception.InvalidNumberLimitException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@ToString
@EqualsAndHashCode
@FieldNameConstants(level = PRIVATE)
public final class PhoneNumber {

  private static final String INVALID_NUMBER = "{0} must be greater than 0";
  private static final String FIELD = "PhoneNumber.{0}";
  private static final String TARGET = PhoneNumber.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "When you build a Phone number, you must provide a {0} greater than 0";

  private final int areaCode;
  private final int number;

  private PhoneNumber(final int areaCode, final int number) {
    this.areaCode = areaCode;
    this.number = number;
  }

  public static PhoneNumber of(final int areaCode, final int number) {
    if (areaCode <= 0) {
      throw new InvalidNumberLimitException(format(INVALID_NUMBER, Fields.areaCode),
          format(FIELD, Fields.areaCode),
          TARGET,
          format(FIELD, Fields.areaCode),
          format(VIOLATION_MESSAGE, Fields.areaCode));
    }

    if (number <= 0) {
      throw new InvalidNumberLimitException(format(INVALID_NUMBER, Fields.number),
          format(FIELD, Fields.number),
          TARGET,
          format(FIELD, Fields.number),
          format(VIOLATION_MESSAGE, Fields.number));
    }

    return new PhoneNumber(areaCode, number);
  }

}
