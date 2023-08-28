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

class CpfValidatorTest {

  private static final String INVALID_CPF = "Invalid CPF";
  private static final String FIELD = "cpf";
  private static final String TARGET = CpfValidator.class.getSimpleName();
  private static final String INVALID_FORMAT_MESSAGE = "You must provide a cpf in the format XXX.XXX.XXX-XX";
  private static final String INVALID_DIGIT_MESSAGE = "The CPF does not exist.";

  private final String REGEX = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}";
  private final Faker faker = new Faker();

  private static Stream<String> invalidCpf() {
    return Stream.of("00000000000", "11111111111", "22222222222", "33333333333",
        "44444444444", "55555555555", "66666666666", "77777777777",
        "88888888888", "99999999999", "123456789");
  }

  @Test
  @DisplayName("Given a valid CPF when validate then do not throw exception")
  void givenValidCpfWhenValidateThenDoNotThrowException() {
    final var incompleteCpf = faker.expression(faker.regexify(REGEX));
    final var cpf = generateUnformattedCpf(incompleteCpf);
    assertDoesNotThrow(() -> CpfValidator.validate(cpf));
  }

  @Test
  @DisplayName("Given a valid CPF with punctuation when validate then do not throw exception")
  void givenValidCpfWithPunctuationWhenValidateThenDoNotThrowException() {
    final var incompleteCpf = faker.expression(faker.regexify(REGEX));
    final var cpf = generateFormattedCpf(incompleteCpf);
    assertDoesNotThrow(() -> CpfValidator.validate(cpf));
  }

  @Test
  @DisplayName("Given non existent cpf when validate then throw exception")
  void givenNonExistentCpfWhenValidateThenThrowException() {
    final var pattern = "[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}";
    final var cpf = faker.expression(faker.regexify(pattern));

    final var exception = catchThrowableOfType(() -> CpfValidator.validate(cpf),
        InvalidDocumentException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_CPF);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(INVALID_DIGIT_MESSAGE);
  }

  @ParameterizedTest
  @DisplayName("Given invalid CPF when validate then throw exception")
  @MethodSource("invalidCpf")
  void givenInvalidCpfWhenValidateThenThrowException(final String cpf) {
    final var exception = catchThrowableOfType(() -> CpfValidator.validate(cpf),
        InvalidDocumentException.class);

    assertThat(exception.getMessage()).isEqualTo(INVALID_CPF);
    assertThat(exception.getParameter()).isEqualTo(FIELD);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(FIELD);
    assertThat(exception.getViolationMessage()).isEqualTo(INVALID_FORMAT_MESSAGE);
  }

  private String generateUnformattedCpf(final String document) {
    final var formattedCpf = document.replace(".", "").replace("-", "");
    final var dig10 = calculateDigit(formattedCpf, 9);

    final var incompleteCpf = formattedCpf.concat(String.valueOf(dig10));
    final var dig11 = calculateDigit(incompleteCpf, 10);

    return incompleteCpf.concat(String.valueOf(dig11));
  }

  private String generateFormattedCpf(final String document) {
    final var cpf = generateUnformattedCpf(document);
    return String.format(
        "%s.%s.%s-%s",
        cpf.substring(0, 3),
        cpf.substring(3, 6),
        cpf.substring(6, 9),
        cpf.substring(9));
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