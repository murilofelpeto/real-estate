package com.felpeto.realestate.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.dto.input.FilterDto;
import com.felpeto.realestate.controller.dto.input.PageDto;
import com.felpeto.realestate.controller.dto.output.PropertyResponseDto;
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PropertyControllerTest {

  private static final Faker faker = new Faker();
  private static final PropertyController controller = new PropertyController();

  @Test
  void givenPaginationAndFilterWhenGetPropertyThenReturnValidProperty() {
    final var pageDto = PageDto.builder()
        .limit(2)
        .offset(2)
        .sort("ASC")
        .build();

    final var filterDto = FilterDto.builder()
        .city(faker.address().city())
        .country(faker.address().country())
        .garage(faker.number().numberBetween(1, 99))
        .isFurnished(faker.bool().bool())
        .isRent(faker.bool().bool())
        .isSale(faker.bool().bool())
        .neighborhood(faker.pokemon().name())
        .propertyKind(faker.programmingLanguage().name())
        .rooms(faker.number().numberBetween(1, 9999))
        .size(faker.number().numberBetween(60, 9999999))
        .state(faker.address().stateAbbr())
        .totalPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1000, 999999)))
        .build();

    final var response = controller.findAll(pageDto, filterDto);
    final var responseEntity = (List<PropertyResponseDto>) response.getEntity();

    assertThat(response.getStatus()).isEqualTo(200);
    final var propertyResponseDto = responseEntity.stream().findFirst().get();
    final var propertyResponseDtos = buildPropertyResponse(propertyResponseDto.getId());

    assertThat(responseEntity).containsAll(propertyResponseDtos);

  }

  private List<PropertyResponseDto> buildPropertyResponse(final UUID id) {
    final var condominiumItems = Map.of(
        "Available", List.of("Barbecue"),
        "Unavailable", List.of("Swimming pool"));

    final var images = Map.of(
        "Property", List.of("Image.png", "Image2.png"),
        "Condominium", List.of("Image.png", "Image2.png"));

    final var propertyItems = Map.of(
        "Available", List.of("air conditioner"),
        "Unavailable", List.of("Kitchen"));

    return List.of(PropertyResponseDto.builder()
        .city("São Paulo")
        .complement("N/A")
        .condominiumItems(condominiumItems)
        .condominiumValue(new BigDecimal("500.00"))
        .country("Brazil")
        .description("Beautiful house located in São Paulo")
        .garage(2)
        .id(id)
        .images(images)
        .isCondominium(true)
        .isFurnished(true)
        .isRent(true)
        .isSale(true)
        .neighborhood("Vila Mariana")
        .number(3010)
        .propertyItems(propertyItems)
        .propertyKind("House")
        .rentPrice(new BigDecimal("3000.00"))
        .rooms(3)
        .salePrice(new BigDecimal("500000.36"))
        .size(300)
        .state("SP")
        .streetName("st. wolf")
        .taxes(new BigDecimal("150.00"))
        .zipCode("18309-098")
        .build());
  }
}