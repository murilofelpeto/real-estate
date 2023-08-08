package com.felpeto.realestate.controller.handler;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.felpeto.realestate.controller.handler.dto.ErrorDetails;
import com.felpeto.realestate.controller.handler.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConstraintViolationExceptionMapperTest {

  @Mock
  private UriInfo uriInfo;

  @InjectMocks
  private ConstraintViolationExceptionMapper exceptionMapper;

  @Test
  @DisplayName("Given empty violations when map to response then return error dto with empty list")
  void givenEmptyViolationsWhenMapToResponseThenReturnErrorDtoWithEmptyList() {
    final LocalDateTime now = LocalDateTime.now();
    final var exception = new ConstraintViolationException(emptySet());

    when(uriInfo.getAbsolutePath()).thenReturn(URI.create("/path"));

    final var response = exceptionMapper.toResponse(exception);
    final var body = (ErrorResponseDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(body.getCode()).isEqualTo("INVALID_FORMAT_EXCEPTION");
    assertThat(body.getDetails()).isEmpty();
    assertThat(body.getMessage()).isEqualTo(exception.getMessage());
    assertThat(body.getParameter()).isEqualTo("/path");
    assertThat(body.getStatus()).isEqualTo(400);
    assertThat(body.getTimestamp()).isBetween(now, now.plusSeconds(1));
  }

  @Test
  @DisplayName("Given violation when map to response then return detailed validation")
  void givenViolationWhenMapToResponseThenReturnDetailedValidation() {
    final var violation = mock(ConstraintViolation.class);
    final var path = mock(Path.class);
    final var leafBean = mock(Object.class);
    final var violations = new HashSet<ConstraintViolation<?>>();
    violations.add(violation);

    final var exception = new ConstraintViolationException(violations);

    when(violation.getPropertyPath()).thenReturn(path);
    when(violation.getPropertyPath().toString()).thenReturn("findAll.pageDto.limit");
    when(violation.getLeafBean()).thenReturn(leafBean);
    when(violation.getLeafBean().toString()).thenReturn("TargetObject@123");
    when(violation.getMessage()).thenReturn("Invalid value");
    when(uriInfo.getAbsolutePath()).thenReturn(URI.create("/path"));

    final var response = exceptionMapper.toResponse(exception);
    final var body = (ErrorResponseDto) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(400);
    assertThat(body.getDetails()).hasSize(1)
        .containsExactly(ErrorDetails.builder()
            .field("limit")
            .target("TargetObject")
            .violationMessage("Invalid value")
            .build());


  }
}