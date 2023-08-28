package com.felpeto.realestate.domain.person.vo;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ContactTest {

  private static final Faker faker = new Faker();
  private static final String MANDATORY_FIELD = "[Contact] | {0} is mandatory";

  private static Stream<Arguments> invalidParams() {
    final var email = Email.of(faker.internet().emailAddress());
    final var areaCode = faker.number().numberBetween(1, 99);
    final var number = faker.number().numberBetween(111111, 99999999);
    final var phoneNumber = PhoneNumber.of(areaCode, number);
    return Stream.of(
        Arguments.of(null, phoneNumber, "email"),
        Arguments.of(email, null, "phoneNumber")
    );
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Contact.class)
        .verify();
  }

  @Test
  @DisplayName("Should return valid Equals and Hashcode")
  void validEqualsAndHashCode() {
    EqualsVerifier.forClass(Contact.class)
        .suppress(Warning.NULL_FIELDS)
        .verify();
  }

  @Test
  @DisplayName("Given valid information when create Contact then return valid Contact")
  void givenValidInformationWhenCreateContactThenReturnValidContact() {
    final var email = Email.of(faker.internet().emailAddress());
    final var areaCode = faker.number().numberBetween(1, 99);
    final var number = faker.number().numberBetween(111111, 99999999);
    final var phoneNumber = PhoneNumber.of(areaCode, number);
    final var contact = new Contact(email, phoneNumber);

    assertThat(contact.email()).isEqualTo(email);
    assertThat(contact.phoneNumber()).isEqualTo(phoneNumber);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build Contact then throw exception")
  void givenInvalidParametersWhenBuildContactThenThrowException(
      final Email email,
      final PhoneNumber phoneNumber,
      final String field) {
    assertThatThrownBy(() -> new Contact(email, phoneNumber))
        .hasMessage(format(MANDATORY_FIELD, field))
        .isExactlyInstanceOf(NullPointerException.class);
  }
}