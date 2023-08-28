package com.felpeto.realestate.domain.person.vo;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public enum DocumentType {
  CPF(CpfValidator::validate, DocumentNormalizer::keepOnlyNumbers),
  CNPJ(CnpjValidator::validate, DocumentNormalizer::keepOnlyNumbers);

  private final Consumer<String> validate;
  private final UnaryOperator<String> normalize;

  DocumentType(final Consumer<String> validate, final UnaryOperator<String> normalize) {
    this.validate = validate;
    this.normalize = normalize;
  }

  public void validate(final String document) {
    validate.accept(document);
  }

  public String normalize(final String document) {
    return normalize.apply(document);
  }
}
