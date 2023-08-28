package com.felpeto.realestate.domain.person.vo;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(level = PRIVATE)
public record Contact(
    Email email,
    PhoneNumber phoneNumber) {

  private static final String MANDATORY_FIELD = "[Contact] | {0} is mandatory";

  public Contact(final Email email, final PhoneNumber phoneNumber) {
    this.email = requireNonNull(email, format(MANDATORY_FIELD, Fields.email));
    this.phoneNumber = requireNonNull(phoneNumber, format(MANDATORY_FIELD, Fields.phoneNumber));
  }
}
