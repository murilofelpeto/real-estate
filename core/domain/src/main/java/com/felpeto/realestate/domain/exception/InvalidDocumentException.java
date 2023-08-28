package com.felpeto.realestate.domain.exception;

public non-sealed class InvalidDocumentException extends UnprocessableEntityException {

  public InvalidDocumentException(final String message,
      final String parameter,
      final String target,
      final String field,
      final String violationMessage) {

    super(message, parameter, target, field, violationMessage);
  }
}
