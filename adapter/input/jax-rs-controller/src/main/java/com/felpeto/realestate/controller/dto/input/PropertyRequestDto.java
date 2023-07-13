package com.felpeto.realestate.controller.dto.input;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.felpeto.realestate.controller.handler.annotation.Registration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class PropertyRequestDto {

  @Schema(
      name = "registration",
      requiredMode = REQUIRED,
      minLength = 15,
      maxLength = 15,
      description = "Here you will insert your property registration number",
      example = "CCCCC.L.NNNNNNN-DD")
  @NotBlank(message = "Registration is mandatory")
  @Registration
  private String registration;


  @Schema(
      name = "property_kind",
      requiredMode = REQUIRED,
      allowableValues = {
          "single-family",
          "multi-family",
          "townhouses",
          "condominiums",
          "apartments",
          "manufactured",
          "public",
          "retail",
          "office",
          "co-working",
          "land",
          "heavy-manufacturing",
          "light-manufacturing",
          "warehouses",
          "distribution-facilities"})
  @NotBlank(message = "property kind is mandatory")
  private String propertyKind;

  @Schema(name = "country", requiredMode = REQUIRED)
  @NotBlank(message = "Country is mandatory")
  private String country;

  @Schema(
      name = "state",
      requiredMode = REQUIRED,
      minLength = 2,
      maxLength = 2,
      description = "We expect an abbreviation of the state")
  @NotBlank(message = "State is mandatory")
  @Size(min = 2, max = 2, message = "State should be abbreviate with length of 2")
  private String state;

  @Schema(name = "city", requiredMode = REQUIRED)
  @NotBlank(message = "City is mandatory")
  private String city;

  @Schema(name = "neighborhood", requiredMode = REQUIRED)
  @NotBlank(message = "Neighborhood is mandatory")
  private String neighborhood;

  @Schema(name = "zip_code", requiredMode = REQUIRED)
  @NotBlank(message = "zip code is mandatory")
  private String zipCode;

  @Schema(name = "street_name", requiredMode = REQUIRED)
  @NotBlank(message = "Street name is mandatory")
  private String streetName;

  @Schema(name = "number", requiredMode = REQUIRED)
  @NotNull
  @Positive(message = "Number is mandatory")
  private Integer number;

  @Schema(name = "complement")
  private String complement;

  @Schema(name = "size", requiredMode = REQUIRED)
  @NotNull(message = "Size is mandatory")
  @Positive(message = "Size must be positive")
  @Min(value = 10, message = "Size must be greater or equal to 10")
  private Integer size;

  @Schema(name = "rooms", requiredMode = REQUIRED)
  @NotNull(message = "Rooms is mandatory")
  @Positive(message = "Rooms must be greater than zero")
  private Integer rooms;

  @Schema(name = "isRent", requiredMode = REQUIRED)
  @NotNull(message = "You must inform property is for rent")
  private Boolean isRent;

  @Schema(name = "isSale", requiredMode = REQUIRED)
  @NotNull(message = "You must inform property is for sale")
  private Boolean isSale;

  @Schema(name = "isFurnished", requiredMode = REQUIRED)
  @NotNull(message = "You must inform property has furniture")
  private Boolean isFurnished;

  @Schema(name = "rentPrice", description = "This field is mandatory if isRent equals True")
  @Digits(fraction = 2, integer = Integer.MAX_VALUE)
  private BigDecimal rentPrice;

  @Schema(name = "salePrice", description = "This field is mandatory if isSale equals True")
  @Digits(fraction = 2, integer = Integer.MAX_VALUE)
  private BigDecimal salePrice;

  @Schema(name = "taxes", requiredMode = REQUIRED)
  @Digits(fraction = 2, integer = Integer.MAX_VALUE)
  @NotNull(message = "Taxes is mandatory")
  @Positive(message = "Taxes must be greater than zero")
  private BigDecimal taxes;

  @Schema(name = "isCondominium", requiredMode = REQUIRED)
  @NotNull(message = "You must inform property in on a condominium")
  private Boolean isCondominium;

  @Schema(name = "salePrice", description = "This field is mandatory if isCondominium equals True")
  @Digits(fraction = 2, integer = Integer.MAX_VALUE)
  private BigDecimal condominiumValue;

  @Schema(name = "garage", requiredMode = REQUIRED)
  @NotNull(message = "garage is mandatory")
  @PositiveOrZero(message = "garage must be positive")
  private Integer garage;

  @Schema(name = "description", requiredMode = REQUIRED)
  @NotBlank(message = "Description name is mandatory")
  private String description;

  @Schema(name = "propertyItems", requiredMode = REQUIRED)
  @NotEmpty(message = "Property items is mandatory")
  private Map<String, List<String>> propertyItems;

  @Schema(name = "condominiumItems", description = "This field is mandatory when the property is in a condominium")
  private Map<String, List<String>> condominiumItems;
}

