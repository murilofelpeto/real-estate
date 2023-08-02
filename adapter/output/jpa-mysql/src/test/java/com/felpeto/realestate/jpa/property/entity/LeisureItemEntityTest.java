package com.felpeto.realestate.jpa.property.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.UUID;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LeisureItemEntityTest {

  private final Faker faker = new Faker();

  @Test
  void validToString() {
    ToStringVerifier.forClass(LeisureItemEntity.class)
        .withClassName(NameStyle.SIMPLE_NAME)
        .verify();
  }

  @Test
  void validEqualsAndHashcode() {
    EqualsVerifier.simple().forClass(LeisureItemEntity.class)
        .withOnlyTheseFields("uuid", "item")
        .verify();
  }

  @Test
  @DisplayName("Given no args when use no args constructor then return Leisure item entity")
  void givenNoArgsWhenUseNoArgsConstructorThenReturnLeisureItemEntity() {
    final var entity = new LeisureItemEntity();
    final var item = faker.animal().name();
    final var uuid = UUID.randomUUID();
    final var id = faker.number().numberBetween(1L, 100L);

    entity.setItem(item);
    entity.setUuid(uuid);
    entity.setLeisureItemId(id);

    assertThat(entity.getItem()).isEqualTo(item);
    assertThat(entity.getLeisureItemId()).isEqualTo(id);
    assertThat(entity.getUuid()).isEqualTo(uuid);
  }

  @Test
  @DisplayName("Given valid information when build leisure item entity then return valid leisure item")
  void givenValidInformationWhenBuildLeisureItemEntityThenReturnValidLeisureItem() {
    final var item = faker.animal().name();
    final var uuid = UUID.randomUUID();
    final var id = faker.number().numberBetween(1L, 100L);

    final var entity = LeisureItemEntity.builder()
        .item(item)
        .uuid(uuid)
        .leisureItemId(id)
        .build();

    assertThat(entity.getItem()).isEqualTo(item);
    assertThat(entity.getLeisureItemId()).isEqualTo(id);
    assertThat(entity.getUuid()).isEqualTo(uuid);
  }

  @Test
  @DisplayName("Given entity without uuid when call pre persist then return entity with uuid")
  void givenEntityWithoutUuidWhenCallPrePersistThenReturnEntityWithUuid() {
    final var item = faker.animal().name();
    final var id = faker.number().numberBetween(1L, 100L);

    final var entity = LeisureItemEntity.builder()
        .item(item)
        .leisureItemId(id)
        .build();
    assertThat(entity.getUuid()).isNull();

    entity.prePersist();

    assertThat(entity.getUuid()).isNotNull();
  }
}