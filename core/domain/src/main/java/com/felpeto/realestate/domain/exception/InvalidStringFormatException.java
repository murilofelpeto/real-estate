package com.felpeto.realestate.domain.exception;

public final class InvalidStringFormatException extends UnprocessableEntityException {


  public InvalidStringFormatException(final String message,
      final String parameter,
      final String target,
      final String field,
      final String violationMessage) {

    super(message, parameter, target, field, violationMessage);
  }
}
