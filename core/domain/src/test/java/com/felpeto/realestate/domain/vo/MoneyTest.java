package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MoneyTest {

  private final Faker faker = new Faker();

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Money.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Money.class)
        .suppress(Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when build Money then return valid Money")
  void givenValidParametersWhenBuildMoneyThenReturnValidMoney() {
    final var number = faker.number().randomDouble(2, 2, 100);
    final var value = BigDecimal.valueOf(number).setScale(2, RoundingMode.HALF_UP);

    final var money = Money.of(value);

    assertThat(money.getValue()).isEqualTo(value);
    assertThat(money.getValue().scale()).isEqualTo(2);
  }

  @Test
  @DisplayName("Given value of ONE when build Money then return valid Money")
  void givenValueOneWhenBuildMoneyThenReturnValidMoney() {
    final var one = BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP);
    final var money = Money.of(one);
    assertThat(money.getValue()).isEqualTo(one);
    assertThat(money.getValue().scale()).isEqualTo(2);
  }

  @Test
  @DisplayName("Given below of ONE when build Money then throw exception")
  void givenBelowOneWhenBuildMoneyThenThrowException() {
    final var zero = BigDecimal.ZERO;
    assertThatThrownBy(() -> Money.of(zero))
        .hasMessage("value must be greater than or equal to 1: " + zero)
        .isExactlyInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Given invalid parameters when build Money then throw exception")
  void givenInvalidParametersWhenBuildMoneyThenThrowException() {

    assertThatThrownBy(() -> Money.of(null))
        .hasMessage("Money is mandatory")
        .isExactlyInstanceOf(NullPointerException.class);
  }
}