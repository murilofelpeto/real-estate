package com.felpeto.realestate.domain.person.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DocumentNormalizerTest {
  
  @Test
  @DisplayName("Given document with punctuation when call normalizer then return document without punctuation")
  void givenDocumentWithPunctuationWhenCallNormalizerThenReturnDocumentWithoutPunctuation() {
    final var cnpj = "111.111.111/1111-11";
    final var document = DocumentNormalizer.keepOnlyNumbers(cnpj);

    assertThat(document).isEqualTo("111111111111111");
  }
}