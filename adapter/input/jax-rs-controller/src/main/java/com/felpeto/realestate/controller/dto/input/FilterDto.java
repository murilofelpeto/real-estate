package com.felpeto.realestate.controller.dto.input;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.ws.rs.QueryParam;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {

  @Schema(name = "propertyKind")
  @QueryParam("propertyKind")
  private String propertyKind;

  @Schema(name = "country")
  @QueryParam("country")
  private String country;

  @Schema(name = "state")
  @QueryParam("state")
  private String state;

  @Schema(name = "city")
  @QueryParam("city")
  private String city;

  @Schema(name = "neighborhood")
  @QueryParam("neighborhood")
  private String neighborhood;

  @Schema(name = "size")
  @Positive(message = "Size must be positive")
  @Min(value = 10, message = "Size must be greater or equal to 10")
  @QueryParam("size")
  private Integer size;

  @Schema(name = "rooms", requiredMode = REQUIRED)
  @Positive(message = "Rooms must be greater than zero")
  @QueryParam("rooms")
  private Integer rooms;

  @QueryParam("isRent")
  private Boolean isRent;

  @QueryParam("isSale")
  private Boolean isSale;

  @QueryParam("isFurnished")
  private Boolean isFurnished;

  @Schema(name = "rentPrice", description = "This field is mandatory if isRent equals True")
  @Digits(fraction = 2, integer = Integer.MAX_VALUE)
  @QueryParam("totalPrice")
  private BigDecimal totalPrice;

  @Schema(name = "garage", requiredMode = REQUIRED)
  @PositiveOrZero(message = "garage must be positive")
  @QueryParam("garage")
  private Integer garage;
}
