package com.felpeto.realestate.controller.handler;

import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.handler.dto.ErrorDetails;
import com.felpeto.realestate.controller.handler.dto.ErrorResponseDto;
import com.felpeto.realestate.domain.exception.InvalidFormatException;
import com.felpeto.realestate.domain.exception.InvalidPropertyNumberException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UnprocessableEntityExceptionMapperTest {

  private final UnprocessableEntityExceptionMapper mapper = new UnprocessableEntityExceptionMapper();

  @Test
  @DisplayName("Given InvalidFormatException when map toResponse then return BAD_REQUEST")
  void givenInvalidFormatExceptionWhenMapToResponseThenReturnBadRequest() {
    final var message = "InvalidFormatException";
    final var exception = new InvalidFormatException(message, "parameter", "target", "field",
        "violation message");

    final var response = mapper.toResponse(exception);
    final var body = (ErrorResponseDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(422);
    assertThat(body.getMessage()).isEqualTo(message);
    assertThat(body.getCode()).isEqualTo("INVALID_FORMAT_EXCEPTION");
    assertThat(body.getParameter()).isEqualTo("parameter");
    assertThat(body.getStatus()).isEqualTo(422);
    assertThat(body.getTimestamp())
        .isBetween(LocalDateTime.now().minusSeconds(1), LocalDateTime.now());

    final ErrorDetails errorDetail = ErrorDetails.builder()
        .field("field")
        .target("target")
        .violationMessage("violation message")
        .build();
    assertThat(body.getDetails()).hasSize(1).containsExactly(errorDetail);
  }

  @Test
  @DisplayName("Given InvalidPropertyNumberException when map toResponse then return BAD_REQUEST")
  void givenInvalidPropertyNumberExceptionWhenMapToResponseThenReturnBadRequest() {
    final var message = "InvalidFormatException";
    final var exception = new InvalidPropertyNumberException(message, "parameter", "target",
        "field",
        "violation message");

    final var response = mapper.toResponse(exception);
    final var body = (ErrorResponseDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(422);
    assertThat(body.getMessage()).isEqualTo(message);
    assertThat(body.getCode()).isEqualTo("INVALID_FORMAT_EXCEPTION");
    assertThat(body.getParameter()).isEqualTo("parameter");
    assertThat(body.getStatus()).isEqualTo(422);
    assertThat(body.getTimestamp())
        .isBetween(LocalDateTime.now().minusSeconds(1), LocalDateTime.now());

    final ErrorDetails errorDetail = ErrorDetails.builder()
        .field("field")
        .target("target")
        .violationMessage("violation message")
        .build();
    assertThat(body.getDetails()).hasSize(1).containsExactly(errorDetail);
  }
}