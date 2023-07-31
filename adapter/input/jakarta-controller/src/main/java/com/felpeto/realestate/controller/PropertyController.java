package com.felpeto.realestate.controller;

import static com.felpeto.realestate.controller.mapper.FilterMapper.toFilter;
import static com.felpeto.realestate.controller.mapper.PageMapper.toPage;
import static com.felpeto.realestate.controller.mapper.PropertyDtoMapper.toPropertiesResponseDto;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.controller.dto.input.PageDto;
import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import com.felpeto.realestate.usecase.property.PropertyGetter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/v1/property")
public class PropertyController {

  private final PropertyGetter propertyGetter;

  @Inject
  public PropertyController(final PropertyGetter propertyGetter) {
    this.propertyGetter = propertyGetter;
  }

  @GET
  @Produces(APPLICATION_JSON)
  @Operation(
      summary = "Get all properties",
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "Return a list of properties",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  array = @ArraySchema(schema = @Schema(implementation = PropertyResponseDto.class)))),
          @ApiResponse(
              responseCode = "204",
              description = "empty list")
      })
  public Response findAll(
      @Valid @BeanParam final PageDto pageDto,
      @Valid @BeanParam final FilterDto filterDto) {

    final var properties = propertyGetter.findAll(toPage(pageDto), toFilter(filterDto));

    if (properties.isEmpty()) {
      return Response.noContent().build();
    }
    final var response = toPropertiesResponseDto(properties);
    return Response.ok(response).build();

  }

}
