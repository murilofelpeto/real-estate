package com.felpeto.realestate.domain.person.vo;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.validator.routines.EmailValidator;

@Getter
@ToString
@EqualsAndHashCode
public final class Email {

  private static final String MANDATORY_FIELD = "Email is mandatory";
  private static final String FIELD = "Email.value";
  private static final String TARGET = Email.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Email must be valid";

  private static final EmailValidator EMAIL = EmailValidator.getInstance(true, true);

  private final String value;

  private Email(final String value) {
    this.value = value;
  }

  public static Email of(final String value) {
    if (!EMAIL.isValid(value)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }
    return new Email(value);
  }
}
