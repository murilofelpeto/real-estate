package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.math.BigDecimal;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SaleTest {

  private final Faker faker = new Faker();

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Sale.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Sale.class)
        .withIgnoredFields("isSale")
        .verify();
  }

  @Test
  @DisplayName("Given isSale true when build Sale then return valid Sale")
  void givenIsSaleTrueWhenBuildSaleThenReturnValidSale() {
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    final var sale = Sale.of(true, price);

    assertThat(sale.isSale()).isTrue();
    assertThat(sale.getPrice()).isEqualTo(price);
  }

  @Test
  @DisplayName("Given isSale false when build Sale then return Sale with null fields")
  void givenIsSaleFalseWhenBuildSaleThenReturnSaleWithNullFields() {
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    final var sale = Sale.of(false, price);

    assertThat(sale.isSale()).isFalse();
    assertThat(sale.getPrice()).isNull();
  }

  @Test
  @DisplayName("Given invalid parameters when build Sale then throw exception")
  void givenInvalidParametersWhenBuildSaleThenThrowException() {

    assertThatThrownBy(() -> Sale.of(true, null))
        .hasMessage("Price is mandatory")
        .isExactlyInstanceOf(NullPointerException.class);
  }
}