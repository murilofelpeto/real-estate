package com.felpeto.realestate.domain.person.vo;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class DocumentNormalizer {

  public static String keepOnlyNumbers(String document) {
    return document.replaceAll("\\D", "");
  }
}
