package com.felpeto.realestate.controller.handler.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class ErrorDto {

  private String message;

}
