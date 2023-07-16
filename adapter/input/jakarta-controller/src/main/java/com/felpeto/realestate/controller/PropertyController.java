package com.felpeto.realestate.controller;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.controller.dto.input.PageDto;
import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
@Path("/v1/property")
public class PropertyController {

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
              description = "empty list"),
          @ApiResponse(
              responseCode = "422",
              description = "Unprocessable Entity",
              content =
              @Content(
                  mediaType = APPLICATION_JSON,
                  schema = @Schema(implementation = Object.class)))
      })
  public Response findAll(@Valid @BeanParam final PageDto pageDto,
      @Valid @BeanParam final FilterDto filterDto) {
    final var condominiumItems = Map.of(
        "Available", List.of("Barbecue"),
        "Unavailable", List.of("Swimming pool"));

    final var images = Map.of(
        "Property", List.of("Image.png", "Image2.png"),
        "Condominium", List.of("Image.png", "Image2.png"));

    final var propertyItems = Map.of(
        "Available", List.of("air conditioner"),
        "Unavailable", List.of("Kitchen"));

    final var response = List.of(PropertyResponseDto.builder()
        .city("São Paulo")
        .complement("N/A")
        .condominiumItems(condominiumItems)
        .condominiumValue(new BigDecimal("500.00"))
        .country("Brazil")
        .description("Beautiful house located in São Paulo")
        .garage(2)
        .id(UUID.randomUUID())
        .images(images)
        .isCondominium(true)
        .isFurnished(true)
        .isRent(true)
        .isSale(true)
        .neighborhood("Vila Mariana")
        .number(3010)
        .propertyItems(propertyItems)
        .propertyKind("House")
        .rentPrice(new BigDecimal("3000.00"))
        .rooms(3)
        .salePrice(new BigDecimal("500000.36"))
        .size(300)
        .state("SP")
        .streetName("st. wolf")
        .taxes(new BigDecimal("150.00"))
        .zipCode("18309-098")
        .build());

    return Response.ok(response).build();

  }

}
