package com.felpeto.realestate.domain.person.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidDocumentException;
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
  @DisplayName("Given invalid CNPJ when call validate then throw exception")
  void givenInvalidCnpjWhenCallValidateThenThrowException() {
    final String invalidCnpj = "Invalid CNPJ";
    final String field = "cnpj";
    final String target = CnpjValidator.class.getSimpleName();
    final String cnpjDoesNotExist = "The CNPJ does not exist.";

    final var exception = catchThrowableOfType(() -> DocumentType.CNPJ.validate("12345678901235"),
        InvalidDocumentException.class);

    assertThat(exception.getMessage()).isEqualTo(invalidCnpj);
    assertThat(exception.getParameter()).isEqualTo(field);
    assertThat(exception.getTarget()).isEqualTo(target);
    assertThat(exception.getField()).isEqualTo(field);
    assertThat(exception.getViolationMessage()).isEqualTo(cnpjDoesNotExist);
  }

  @Test
  @DisplayName("Given invalid CPF when call validate then throw exception")
  void givenInvalidCpfWhenCallValidateThenThrowException() {
    final String invalidCpf = "Invalid CPF";
    final String field = "cpf";
    final String target = CpfValidator.class.getSimpleName();
    final String cpfDoesNotExist = "The CPF does not exist.";

    final var exception = catchThrowableOfType(() -> DocumentType.CPF.validate("12345678902"),
        InvalidDocumentException.class);

    assertThat(exception.getMessage()).isEqualTo(invalidCpf);
    assertThat(exception.getParameter()).isEqualTo(field);
    assertThat(exception.getTarget()).isEqualTo(target);
    assertThat(exception.getField()).isEqualTo(field);
    assertThat(exception.getViolationMessage()).isEqualTo(cpfDoesNotExist);
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