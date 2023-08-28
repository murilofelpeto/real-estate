package com.felpeto.realestate.domain.person.vo;

import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.domain.exception.InvalidDocumentException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class CnpjValidator {

  private static final Set<String> INVALID_DOCUMENTS = new HashSet<>(
      Arrays.asList("00000000000000", "11111111111111", "22222222222222", "33333333333333",
          "44444444444444", "55555555555555", "66666666666666", "77777777777777", "88888888888888",
          "99999999999999"));

  private static final String INVALID_CNPJ = "Invalid CNPJ";
  private static final String FIELD = "cnpj";
  private static final String TARGET = CnpjValidator.class.getSimpleName();
  private static final String INVALID_FORMAT_MESSAGE = "You must provide a CNPJ in the format XX.XXX.XXX/XXXX-XX";
  private static final String INVALID_DIGIT_MESSAGE = "The CNPJ does not exist.";

  public static void validate(final String cnpj) {
    final var number = cnpj.replace(".", "").replace("-", "").replace("/", "");

    if (number.length() != 14 || INVALID_DOCUMENTS.contains(number)) {
      throw new InvalidDocumentException(INVALID_CNPJ, FIELD, TARGET, FIELD,
          INVALID_FORMAT_MESSAGE);
    }

    final var dig13 = calculateDigit(number, 12);
    final var dig14 = calculateDigit(number, 13);

    if ((dig13 != Character.getNumericValue(number.charAt(12)))
        || (dig14 != Character.getNumericValue(number.charAt(13)))) {

      throw new InvalidDocumentException(INVALID_CNPJ, FIELD, TARGET, FIELD, INVALID_DIGIT_MESSAGE);
    }
  }

  private static int calculateDigit(String number, int position) {
    int sm = 0;
    int num;
    int peso = 2;
    for (int i = position - 1; i >= 0; i--) {
      num = number.charAt(i) - '0';
      sm += num * peso;
      peso = peso == 9 ? 2 : peso + 1;
    }
    int r = sm % 11;
    return (r < 2) ? 0 : 11 - r;
  }
}
