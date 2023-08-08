package com.felpeto.realestate.domain.exception;

public final class InvalidPropertyNumberException extends UnprocessableEntityException {

  public InvalidPropertyNumberException(final String message,
      final String parameter,
      final String target,
      final String field,
      final String violationMessage) {

    super(message, parameter, target, field, violationMessage);
  }
}
