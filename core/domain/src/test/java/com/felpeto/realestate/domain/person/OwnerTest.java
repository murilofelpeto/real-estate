package com.felpeto.realestate.domain.person;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.felpeto.realestate.domain.person.vo.Contact;
import com.felpeto.realestate.domain.person.vo.Document;
import com.felpeto.realestate.domain.person.vo.DocumentType;
import com.felpeto.realestate.domain.person.vo.Email;
import com.felpeto.realestate.domain.person.vo.Name;
import com.felpeto.realestate.domain.person.vo.PhoneNumber;
import com.felpeto.realestate.domain.property.vo.PropertyNumber;
import com.felpeto.realestate.domain.vo.Address;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.State;
import com.felpeto.realestate.domain.vo.StreetName;
import com.felpeto.realestate.domain.vo.ZipCode;
import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OwnerTest {

  private static final String MANDATORY_FIELD = "[Owner] | {0} is mandatory";
  private static final Faker faker = new Faker();

  private static Contact createContact() {
    final var email = Email.of(faker.internet().emailAddress());
    final var areaCode = faker.number().numberBetween(1, 99);
    final var number = faker.number().numberBetween(111111, 99999999);
    final var phoneNumber = PhoneNumber.of(areaCode, number);
    return new Contact(email, phoneNumber);
  }

  private static Document createDocument() {
    return new Document(DocumentType.CNPJ, "92.687.003/0001-63");
  }

  private static Name createName() {
    return Name.of(faker.name().fullName());
  }

  private static Address createAddress() {
    return Address.builder()
        .city(City.of(faker.address().city()))
        .complement(faker.ancient().god())
        .country(Country.of(faker.country().name()))
        .neighborhood(Neighborhood.of(faker.pokemon().name()))
        .number(PropertyNumber.of(faker.number().numberBetween(1, 9999)))
        .state(State.of(faker.address().stateAbbr()))
        .streetName(StreetName.of(faker.address().streetName()))
        .zipCode(ZipCode.of(faker.address().zipCode()))
        .build();
  }

  private static Stream<Arguments> invalidParams() {
    final var address = createAddress();
    final var contact = createContact();
    final var document = createDocument();
    final var name = createName();

    return Stream.of(
        Arguments.of(null, contact, document, name, "address"),
        Arguments.of(address, null, document, name, "contact"),
        Arguments.of(address, contact, null, name, "document"),
        Arguments.of(address, contact, document, null, "name")
    );
  }

  @Test
  @DisplayName("Should return a valid toString")
  void validToString() {
    ToStringVerifier.forClass(Owner.class)
        .verify();
  }

  @Test
  @DisplayName("Given valid parameters when create owner then return valid owner")
  void givenValidParametersWhenCreateOwnerThenReturnValidOwner() {
    final var address = createAddress();
    final var contact = createContact();
    final var document = createDocument();
    final var name = createName();
    final var owner = new Owner(name, document, address, contact);

    assertThat(owner.address()).isEqualTo(address);
    assertThat(owner.contact()).isEqualTo(contact);
    assertThat(owner.document()).isEqualTo(document);
    assertThat(owner.name()).isEqualTo(name);
  }

  @ParameterizedTest
  @MethodSource("invalidParams")
  @DisplayName("Given invalid parameters when build owner then throw exception")
  void givenInvalidParametersWhenBuildOwnerThenThrowException(
      final Address address,
      final Contact contact,
      final Document document,
      final Name name,
      final String field) {

    assertThatThrownBy(() -> new Owner(name, document, address, contact))
        .hasMessage(format(MANDATORY_FIELD, field))
        .isExactlyInstanceOf(NullPointerException.class);
  }
}