package com.felpeto.realestate.controller.handler.annotation.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.felpeto.realestate.controller.handler.annotation.Regex;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Annotation;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class RegexConstraintValidatorTest {

  private static final String PATTERN = "^[0-9]{5}.[0-9]{1}.[0-9]{7}-[0-9]{2}$";
  private RegexConstraintValidator validator = new RegexConstraintValidator();

  private static Stream<String> invalidParams() {
    return Stream.of("", "111.2-3333-44", null);
  }

  @Test
  void givenValidPatternWhenCallValidatorThenReturnTrue() {
    final var value = "11111.2.3333333-44";
    final var context = mock(ConstraintValidatorContext.class);
    final var regex = createRegexAnnotation(PATTERN);

    validator.initialize(regex);

    boolean isValid = validator.isValid(value, context);
    assertThat(isValid).isTrue();
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  void givenInvalidPatternWhenCallValidatorThenReturnFalse(final String value) {
    final var context = mock(ConstraintValidatorContext.class);
    final var regex = createRegexAnnotation(PATTERN);

    validator.initialize(regex);

    boolean isValid = validator.isValid(value, context);
    assertThat(isValid).isFalse();
  }

  private Regex createRegexAnnotation(final String exp) {
    return new Regex() {
      @Override
      public Class<? extends Annotation> annotationType() {
        return Regex.class;
      }

      @Override
      public String exp() {
        return exp;
      }

      @Override
      public String message() {
        return "";
      }

      @Override
      public Class<? extends Payload>[] payload() {
        return new Class[0];
      }

      @Override
      public Class<?>[] groups() {
        return new Class[0];
      }
    };

  }
}