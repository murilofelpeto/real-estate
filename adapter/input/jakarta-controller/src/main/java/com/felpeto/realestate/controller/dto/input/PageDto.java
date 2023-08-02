package com.felpeto.realestate.controller.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
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
public class PageDto {

  @Schema(name = "_limit")
  @Min(value = 1, message = "_limit must be greater than 0")
  @Max(value = 50, message = "_limit must be less than or equal to 50")
  @QueryParam("_limit")
  @DefaultValue("10")
  private int limit;


  @Schema(name = "_offset")
  @Min(value = 0, message = "_offset must be greater than 0")
  @QueryParam("_offset")
  @DefaultValue("0")
  private int offset;

  @Schema(name = "_sort")
  @QueryParam("_sort")
  @DefaultValue("+propertyKind")
  private String sort;
}
