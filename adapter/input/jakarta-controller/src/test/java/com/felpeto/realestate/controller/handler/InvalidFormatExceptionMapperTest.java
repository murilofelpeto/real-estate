package com.felpeto.realestate.controller.handler;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.handler.dto.ErrorDto;
import com.felpeto.realestate.domain.exception.InvalidFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class InvalidFormatExceptionMapperTest {

  private final InvalidFormatExceptionMapper mapper = new InvalidFormatExceptionMapper();

  @Test
  @DisplayName("Given InvalidFormatException when map toResponse then return BAD_REQUEST")
  void givenInvalidFormatExceptionWhenMapToResponseThenReturnBadRequest() {
    final var message = "InvalidFormatException";
    final var exception = new InvalidFormatException(message);

    final var response = mapper.toResponse(exception);
    final var body = (ErrorDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
    assertThat(body.getMessage()).isEqualTo(message);
  }
}