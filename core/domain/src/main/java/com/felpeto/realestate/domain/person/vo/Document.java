package com.felpeto.realestate.domain.person.vo;


import static java.util.Objects.requireNonNull;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import org.apache.commons.lang3.StringUtils;

public record Document(
    DocumentType type,
    String number) {

  private static final String MANDATORY_FIELD = "Document number is mandatory";
  private static final String FIELD = "DocumentNumber.value";
  private static final String TARGET = Document.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Document number must not be blank or null";
  private static final String DOCUMENT_TYPE_MESSAGE = "Document type is mandatory";

  public Document(final DocumentType type, final String number) {
    this.type = requireNonNull(type, DOCUMENT_TYPE_MESSAGE);

    if (StringUtils.isBlank(number)) {
      throw new InvalidStringFormatException(MANDATORY_FIELD,
          FIELD,
          TARGET,
          FIELD,
          VIOLATION_MESSAGE);
    }

    this.type.validate(number);
    this.number = this.type.normalize(number);
  }
}
