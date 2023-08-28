package com.felpeto.realestate.domain.person.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class DocumentTest {

  private static final String MANDATORY_FIELD = "Document number is mandatory";
  private static final String FIELD = "DocumentNumber.value";
  private static final String TARGET = Document.class.getSimpleName();
  private static final String VIOLATION_MESSAGE = "The Document number must not be blank or null";
  private static final String DOCUMENT_TYPE_MESSAGE = "Document type is mandatory";
  private final Faker faker = new Faker();

  private static Stream<String> invalidParams() {
    return Stream.of(null, "", "  ");
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Document.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid document when create Document then return valid Document")
  void givenValidDocumentWhenCreateDocumentThenReturnValidDocument() {
    final var cpf = generateValidCpf();
    final var documentType = DocumentType.CPF;
    final var document = new Document(documentType, cpf);

    assertThat(document.type()).isEqualTo(documentType);
    assertThat(document.number()).isEqualTo(cpf);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid param when create Document then throw exception")
  void givenInvalidParamsWhenCreateDocumentThenThrowException(final String number) {
    final var type = DocumentType.CPF;

    final var exception = catchThrowableOfType(() -> new Document(type, number),
        InvalidStringFormatException.class);

    assertThat(exception.getMessage()).isEqualTo(MANDATORY_FIELD);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(VIOLATION_MESSAGE);
  }

  @Test
  @DisplayName("Given invalid document type when create Document then throw exception")
  void givenInvalidDocumentWhenCreateDocumentThenThrowException() {
    final var cpf = generateValidCpf();
    assertThatThrownBy(() -> new Document(null, cpf))
        .hasMessage(DOCUMENT_TYPE_MESSAGE)
        .isExactlyInstanceOf(NullPointerException.class);
  }

  private String generateValidCpf() {
    final String cpfRegex = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}";
    final var incompleteCpf = faker.expression(faker.regexify(cpfRegex));
    return generateUnformattedCpf(incompleteCpf);
  }

  private String generateUnformattedCpf(final String document) {
    final var formattedCpf = document.replace(".", "").replace("-", "");
    final var dig10 = calculateDigit(formattedCpf, 9);

    final var incompleteCpf = formattedCpf.concat(String.valueOf(dig10));
    final var dig11 = calculateDigit(incompleteCpf, 10);

    return incompleteCpf.concat(String.valueOf(dig11));
  }

  private int calculateDigit(String cpf, int position) {
    int sum = 0;
    int weight = position + 1;

    for (int i = 0; i < position; i++) {
      int num = cpf.charAt(i) - '0';
      sum += num * weight;
      weight--;
    }

    int remainder = 11 - (sum % 11);
    return (remainder == 10 || remainder == 11) ? 0 : remainder;
  }
}