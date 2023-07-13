package com.felpeto.realestate.controller.dto.output;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
public class PropertyResponseDto {

  private UUID id;
  private String propertyKind;
  private String country;
  private String state;
  private String city;
  private String neighborhood;
  private String zipCode;
  private String streetName;
  private Integer number;
  private String complement;
  private Integer size;
  private Integer rooms;
  private Boolean isRent;
  private Boolean isSale;
  private Boolean isFurnished;
  private BigDecimal rentPrice;
  private BigDecimal salePrice;
  private BigDecimal taxes;
  private Boolean isCondominium;
  private BigDecimal condominiumValue;
  private Integer garage;
  private String description;
  private Map<String, List<String>> propertyItems;
  private Map<String, List<String>> condominiumItems;
  private Map<String, List<String>> images;
}
