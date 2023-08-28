package com.felpeto.realestate.domain.person.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.felpeto.realestate.domain.exception.InvalidDocumentException;
import com.github.javafaker.Faker;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CnpjValidatorTest {

  private static final String INVALID_CNPJ = "Invalid CNPJ";
  private static final String FIELD = "cnpj";
  private static final String TARGET = CnpjValidator.class.getSimpleName();
  private static final String INVALID_FORMAT_MESSAGE = "You must provide a CNPJ in the format XX.XXX.XXX/XXXX-XX";
  private static final String INVALID_DIGIT_MESSAGE = "The CNPJ does not exist.";
  private final String REGEX = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}";

  private final Faker faker = new Faker();

  private static Stream<String> invalidCnpj() {
    return Stream.of("00000000000000", "11111111111111", "22222222222222", "33333333333333",
        "44444444444444", "55555555555555", "66666666666666", "77777777777777",
        "88888888888888", "99999999999999", "1234567897898");
  }

  @Test
  @DisplayName("Given a valid CNPJ when validate then do not throw exception")
  void givenValidCnpjWhenValidateThenDoNotThrowException() {
    final var incompleteCnpj = faker.expression(faker.regexify(REGEX));
    final var cnpj = generateUnformattedCnpj(incompleteCnpj);
    assertDoesNotThrow(() -> CnpjValidator.validate(cnpj));
  }

  @Test
  @DisplayName("Given a valid CNPJ with punctuation when validate then do not throw exception")
  void givenValidCnpjWithPunctuationWhenValidateThenDoNotThrowException() {
    final var incompleteCnpj = faker.expression(faker.regexify(REGEX));
    final var cnpj = generateFormattedCnpj(incompleteCnpj);
    assertDoesNotThrow(() -> CnpjValidator.validate(cnpj));
  }

  @Test
  @DisplayName("Given non existent CNPJ when validate then throw exception")
  void givenNonExistentCnpjWhenValidateThenThrowException() {
    final var pattern = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}/[0-9]{4}-[0-9]{2}";
    final var cnpj = faker.expression(faker.regexify(pattern));

    final var exception = catchThrowableOfType(() -> CnpjValidator.validate(cnpj),
        InvalidDocumentException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_CNPJ);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(INVALID_DIGIT_MESSAGE);
  }

  @ParameterizedTest
  @DisplayName("Given invalid CNPJ when validate then throw exception")
  @MethodSource("invalidCnpj")
  void givenInvalidCnpjWhenValidateThenThrowException(final String CNPJ) {
    final var exception = catchThrowableOfType(() -> CnpjValidator.validate(CNPJ),
        InvalidDocumentException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_CNPJ);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(INVALID_FORMAT_MESSAGE);
  }

  private String generateUnformattedCnpj(final String document) {
    final var formattedCnpj = document.replace(".", "")
        .replace("-", "")
        .replace("/", "");

    final var dig13 = calculateDigit(formattedCnpj, 12);

    final var incompleteCnpj = formattedCnpj.concat(String.valueOf(dig13));
    final var dig14 = calculateDigit(incompleteCnpj, 13);

    return incompleteCnpj.concat(String.valueOf(dig14));
  }

  private int calculateDigit(String number, int position) {
    int sm = 0, num, peso = 2;
    for (int i = position - 1; i >= 0; i--) {
      num = number.charAt(i) - '0';
      sm += num * peso;
      peso = peso == 9 ? 2 : peso + 1;
    }
    int r = sm % 11;
    return (r < 2) ? 0 : 11 - r;
  }

  private String generateFormattedCnpj(final String document) {
    final var cnpj = generateUnformattedCnpj(document);
    return String.format("%s.%s.%s/%s-%s",
        cnpj.substring(0, 2),
        cnpj.substring(2, 5),
        cnpj.substring(5, 8),
        cnpj.substring(8, 12),
        cnpj.substring(12));
  }
}