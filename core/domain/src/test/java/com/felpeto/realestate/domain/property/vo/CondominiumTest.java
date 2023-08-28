package com.felpeto.realestate.domain.property.vo;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CondominiumTest {

  private static final String MANDATORY_FIELD = "[Condominium] | {0} is mandatory";
  private static final Faker faker = new Faker();

  private static Stream<Arguments> invalidParams() {
    final var leisureItems = List.of(faker.options().option(LeisureItem.class));
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    return Stream.of(
        Arguments.of(null, leisureItems, "price"),
        Arguments.of(price, null, "items"));
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Condominium.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Condominium.class)
        .withIgnoredFields("isCondominium")
        .verify();
  }

  @Test
  @DisplayName("Given isCondominium true when build Condominium then return valid Condominium")
  void givenIsCondominiumTrueWhenBuildCondominiumThenReturnValidCondominium() {
    final var leisureItems = List.of(faker.options().option(LeisureItem.class));
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    final var condominium = Condominium.of(true, price, leisureItems);

    assertThat(condominium.isCondominium()).isTrue();
    assertThat(condominium.getItems()).containsExactlyElementsOf(leisureItems);
    assertThat(condominium.getPrice()).isEqualTo(price);
  }

  @Test
  @DisplayName("Given isCondominium false when build Condominium then return condominium with null fields")
  void givenIsCondominiumFalseWhenBuildCondominiumThenReturnCondominiumWithNullFields() {
    final var leisureItems = List.of(faker.options().option(LeisureItem.class));
    final var price = Money.of(BigDecimal.valueOf(faker.number().randomDouble(2, 50, 999)));

    final var condominium = Condominium.of(false, price, leisureItems);

    assertThat(condominium.isCondominium()).isFalse();
    assertThat(condominium.getItems()).isEmpty();
    assertThat(condominium.getPrice()).isNull();
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Condominium then throw exception")
  void givenInvalidParametersWhenBuildCondominiumThenThrowException(
      final Money price,
      final List<LeisureItem> items,
      final String fieldName) {

    assertThatThrownBy(() -> Condominium.of(true, price, items))
        .hasMessage(format(MANDATORY_FIELD, fieldName))
        .isExactlyInstanceOf(NullPointerException.class);
  }

}