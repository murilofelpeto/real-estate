package com.felpeto.realestate.controller.handler.annotation.validator;

import com.felpeto.realestate.controller.handler.annotation.Regex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class RegexConstraintValidator implements ConstraintValidator<Regex, String> {

  private Pattern pattern;

  @Override
  public void initialize(final Regex regex) {
    pattern = Pattern.compile(regex.exp());
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (Objects.isNull(value)) {
      return false;
    }

    return pattern.matcher(value).matches();
  }
}
