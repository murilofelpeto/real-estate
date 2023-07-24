package com.felpeto.realestate.controller.handler;

import com.felpeto.realestate.controller.handler.dto.ErrorDto;
import com.felpeto.realestate.domain.exception.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

  @Override
  public Response toResponse(final InvalidFormatException exception) {
    final ErrorDto errorDto = ErrorDto.builder()
        .message(exception.getMessage())
        .build();

    return Response.status(Response.Status.BAD_REQUEST)
        .entity(errorDto)
        .build();
  }
}
