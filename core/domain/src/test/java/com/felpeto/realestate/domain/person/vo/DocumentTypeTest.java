package com.felpeto.realestate.domain.person.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DocumentTypeTest {

  @Test
  @DisplayName("Given valid CPF when call validate then do nothing")
  void givenValidCpfWhenCallValidateThenDoNothing() {
    assertThatCode(() -> DocumentType.CPF.validate("922.775.070-32"))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Given valid CNPJ when call validate then do nothing")
  void givenValidCnpjWhenCallValidateThenDoNothing() {
    assertThatCode(() -> DocumentType.CNPJ.validate("86.920.365/0001-50"))
        .doesNotThrowAnyException();
  }

  @Test
  @DisplayName("Given CPF with punctuation when call normalize then return normalized CPF")
  void givenCpfWithPunctuationWhenCallNormalizeThenReturnNormalizedCpf() {
    final var cpf = DocumentType.CPF.normalize("922.775.070-32");
    assertThat(cpf).isEqualTo("92277507032");
  }

  @Test
  @DisplayName("Given CNPJ with punctuation when call normalize then return normalized CNPJ")
  void givenCnpjWithPunctuationWhenCallNormalizeThenReturnNormalizedCnpj() {
    final var cnpj = DocumentType.CNPJ.normalize("86.920.365/0001-50");
    assertThat(cnpj).isEqualTo("86920365000150");
  }
}