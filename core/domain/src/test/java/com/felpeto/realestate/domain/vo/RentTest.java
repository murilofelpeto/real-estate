package com.felpeto.realestate.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.math.BigDecimal;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RentTest {

  private final Faker faker = new Faker();

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Rent.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Rent.class)
        .withIgnoredFields("isRent")
        .verify();
  }

  @Test
  @DisplayName("Given isRent true when build Rent then return valid Rent")
  void givenIsRentTrueWhenBuildRentThenReturnValidRent() {
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    final var rent = Rent.of(true, price);

    assertThat(rent.isRent()).isTrue();
    assertThat(rent.getPrice()).isEqualTo(price);
  }

  @Test
  @DisplayName("Given isRent false when build Rent then return rent with null fields")
  void givenIsRentFalseWhenBuildRentThenReturnRentWithNullFields() {
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    final var rent = Rent.of(false, price);

    assertThat(rent.isRent()).isFalse();
    assertThat(rent.getPrice()).isNull();
  }

  @Test
  @DisplayName("Given invalid parameters when build Rent then throw exception")
  void givenInvalidParametersWhenBuildRentThenThrowException() {

    assertThatThrownBy(() -> Rent.of(true, null))
        .hasMessage("Price is mandatory")
        .isExactlyInstanceOf(NullPointerException.class);
  }
}