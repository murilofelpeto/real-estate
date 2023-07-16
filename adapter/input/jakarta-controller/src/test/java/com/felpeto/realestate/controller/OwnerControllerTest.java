package com.felpeto.realestate.controller;

import static jakarta.ws.rs.core.Response.Status.CREATED;
import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.dto.input.OwnerRequestDto;
import com.github.javafaker.Faker;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OwnerControllerTest {

  private final Faker faker = new Faker();
  private final OwnerController controller = new OwnerController();


  @Test
  @DisplayName("Given owner request dto when create owner then return created status")
  void givenOwnerRequestDtoWhenCreateOwnerThenReturnCreatedStatus() {
    final OwnerRequestDto ownerRequestDto = createOwner();

    final Response response = controller.createOwner(ownerRequestDto);
    assertThat(response.getStatus()).isEqualTo(CREATED.ordinal());
  }

  private OwnerRequestDto createOwner() {
    return OwnerRequestDto.builder()
        .city(faker.address().city())
        .complement(faker.address().lastName())
        .country(faker.address().country())
        .document(faker.idNumber().ssnValid())
        .email(faker.internet().emailAddress())
        .name(faker.name().firstName())
        .neighborhood(faker.pokemon().location())
        .number(faker.number().randomDigitNotZero())
        .phoneNumber(faker.phoneNumber().cellPhone())
        .state(faker.address().stateAbbr())
        .streetName(faker.address().streetName())
        .zipCode(faker.address().zipCode())
        .build();
  }

}