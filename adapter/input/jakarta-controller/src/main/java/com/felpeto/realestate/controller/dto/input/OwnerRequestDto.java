package com.felpeto.realestate.controller.dto.input;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import com.felpeto.realestate.controller.handler.annotation.PhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
public class OwnerRequestDto {

  @Schema(name = "name", requiredMode = REQUIRED)
  @NotBlank(message = "Name is mandatory")
  private String name;

  @Schema(name = "email", requiredMode = REQUIRED)
  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email must be valid")
  private String email;

  @Schema(name = "document", requiredMode = REQUIRED)
  @NotBlank(message = "Document is mandatory")
  private String document;

  @Schema(name = "document type", requiredMode = REQUIRED, exampleClasses = DocumentType.class)
  @NotBlank(message = "Email is mandatory")
  private DocumentType documentType;

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

  @Schema(name = "phoneNumber", requiredMode = REQUIRED)
  @NotBlank(message = "Phone number is mandatory")
  @PhoneNumber
  private String phoneNumber;
}
