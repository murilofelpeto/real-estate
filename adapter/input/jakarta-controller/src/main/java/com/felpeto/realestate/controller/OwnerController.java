package com.felpeto.realestate.controller;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.Response.Status.CREATED;

import com.felpeto.realestate.controller.dto.input.OwnerRequestDto;
import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/v1/owner")
public class OwnerController {


  @POST
  @Operation(
      summary = "Create an owner",
      responses = {
          @ApiResponse(
              responseCode = "201",
              description = "Return the created owner",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  schema = @Schema(implementation = Object.class))),
          @ApiResponse(
              responseCode = "409",
              description = "There is a owner with the same information"),
          @ApiResponse(
              responseCode = "422",
              description = "Unprocessable Entity",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  schema = @Schema(implementation = Object.class)))
      })
  public Response createOwner(@Valid @NotNull final OwnerRequestDto ownerRequestDto) {
    return Response.status(CREATED).build();
  }

}
