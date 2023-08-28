package com.felpeto.realestate.domain.person.vo;

import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.exception.InvalidDocumentException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CpfValidator {

  private static final Set<String> INVALID_CPFS = new HashSet<>(Arrays.asList(
      "00000000000", "11111111111", "22222222222", "33333333333",
      "44444444444", "55555555555", "66666666666", "77777777777",
      "88888888888", "99999999999"));

  private static final String INVALID_CPF = "Invalid CPF";
  private static final String FIELD = "cpf";
  private static final String TARGET = CpfValidator.class.getSimpleName();
  private static final String INVALID_FORMAT_MESSAGE = "You must provide a cpf in the format XXX.XXX.XXX-XX";
  private static final String INVALID_DIGIT_MESSAGE = "The CPF does not exist.";


  public static void validate(String cpf) {
    final var number = cpf.replace(".", "").replace("-", "");

    if (number.length() != 11 || INVALID_CPFS.contains(number)) {
      throw new InvalidDocumentException(INVALID_CPF,
          FIELD,
          TARGET,
          FIELD,
          INVALID_FORMAT_MESSAGE);
    }

    int dig10 = calculateDigit(number, 9);
    int dig11 = calculateDigit(number, 10);

    if ((dig10 != Character.getNumericValue(number.charAt(9)))
        || (dig11 != Character.getNumericValue(number.charAt(10)))) {

      throw new InvalidDocumentException(INVALID_CPF,
          FIELD,
          TARGET,
          FIELD,
          INVALID_DIGIT_MESSAGE);
    }
  }

  private static int calculateDigit(String cpf, int position) {
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
