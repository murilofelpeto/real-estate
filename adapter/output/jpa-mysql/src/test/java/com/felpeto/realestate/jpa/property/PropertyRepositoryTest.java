package com.felpeto.realestate.jpa.property;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.felpeto.realestate.domain.Filter;
import com.felpeto.realestate.domain.Page;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.LeisureItem;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.Size;
import com.felpeto.realestate.domain.vo.State;
import com.felpeto.realestate.jpa.property.entity.LeisureItemEntity;
import com.felpeto.realestate.jpa.property.entity.PropertyEntity;
import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PropertyRepositoryTest {

  private static final String REGEX = "[0-9]{5}\\.[0-9]{1}\\.[0-9]{7}-[0-9]{2}";
  private static final String SELECT_FILTERED_PROPERTY =
      "select p from PropertyEntity p"
          + " where (p.propertyKind = :propertyKind OR :propertyKind is null)"
          + " and (p.country = :country OR :country is null)"
          + " and (p.state = :state OR :state is null)"
          + " and (p.city = :city OR :city is null)"
          + " and (p.neighborhood = :neighborhood OR :neighborhood is null)"
          + " and (p.landSize = :size OR :size is null)"
          + " and (p.isRent = :isRent OR :isRent is null)"
          + " and (p.isSale = :isSale OR :isSale is null)"
          + " and (p.isFurnished = :isFurnished OR :isFurnished is null)"
          + " and (p.garage = :garage OR :garage is null)"
          + " order by p.{sort} {sortMode}";

  private final Faker faker = new Faker();

  @Mock
  private EntityManager entityManager;

  @Mock
  private TypedQuery<PropertyEntity> propertyEntityQuery;

  @InjectMocks
  private PropertyRepository propertyRepository;


  @Test
  @DisplayName("Given page and filter when call findAll then return list of properties")
  void givenPageAndFilterWhenCallFindAllThenReturnListOfProperties() {
    final var limit = faker.number().numberBetween(5, 30);
    final var offset = faker.number().numberBetween(3, 29);
    final var page = Page.from(limit, offset, "+total_value");
    final var filter = Filter.builder()
        .city(City.of(faker.address().city()))
        .country(Country.of(faker.address().country()))
        .garage(Size.of(faker.number().numberBetween(0, 10)))
        .isFurnished(faker.bool().bool())
        .isRent(faker.bool().bool())
        .isSale(faker.bool().bool())
        .neighborhood(Neighborhood.of(faker.rickAndMorty().location()))
        .propertyKind(faker.options().option(PropertyKind.class))
        .rooms(Size.of(faker.number().numberBetween(0, 10)))
        .size(Size.of(faker.number().numberBetween(0, 10)))
        .state(State.of(faker.address().stateAbbr()))
        .build();

    final var entity = createEntity();
    final var selectProperty = SELECT_FILTERED_PROPERTY
        .replace("{sort}", page.getSort())
        .replace("{sortMode}", page.getSortMode().name());

    when(entityManager.createQuery(selectProperty, PropertyEntity.class))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("propertyKind", filter.propertyKind().name()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("country", filter.country().getValue()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("state", filter.state().getValue()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("city", filter.city().getValue()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("neighborhood", filter.neighborhood().getValue()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("size", filter.size().getValue()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("isRent", filter.isRent()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("isSale", filter.isSale()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("isFurnished", filter.isFurnished()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("garage", filter.garage().getValue()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setFirstResult(page.getOffset() * page.getLimit()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setMaxResults(page.getLimit()))
        .thenReturn(propertyEntityQuery);

    when(propertyEntityQuery.getResultList()).thenReturn(List.of(entity));

    final var properties = propertyRepository.findAll(page, filter);

    assertThat(properties).hasSize(1);

    verify(entityManager).createQuery(selectProperty, PropertyEntity.class);
    verify(propertyEntityQuery, times(5)).setParameter(anyString(), anyString());
    verify(propertyEntityQuery, times(3)).setParameter(anyString(), anyBoolean());
    verify(propertyEntityQuery, times(2)).setParameter(anyString(), anyInt());
    verify(propertyEntityQuery).setMaxResults(page.getLimit());
    verify(propertyEntityQuery).setFirstResult(page.getOffset() * page.getLimit());
    verifyNoMoreInteractions(entityManager, propertyEntityQuery);

  }

  @Test
  @DisplayName("Given page and empty filter when call findAll then return list of properties")
  void givenPageAndEmptyFilterWhenCallFindAllThenReturnListOfProperties() {
    final var limit = faker.number().numberBetween(5, 30);
    final var offset = faker.number().numberBetween(3, 29);
    final var page = Page.from(limit, offset, "+total_value");
    final var filter = Filter.builder()
        .city(null)
        .country(null)
        .garage(null)
        .isFurnished(null)
        .isRent(null)
        .isSale(null)
        .neighborhood(null)
        .propertyKind(null)
        .rooms(null)
        .size(null)
        .state(null)
        .build();

    final var entity = createEntity();
    final var selectProperty = SELECT_FILTERED_PROPERTY
        .replace("{sort}", page.getSort())
        .replace("{sortMode}", page.getSortMode().name());

    when(entityManager.createQuery(selectProperty, PropertyEntity.class))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("propertyKind", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("country", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("state", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("city", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("neighborhood", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("size", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("isRent", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("isSale", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("isFurnished", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setParameter("garage", null))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setFirstResult(page.getOffset() * page.getLimit()))
        .thenReturn(propertyEntityQuery);
    when(propertyEntityQuery.setMaxResults(page.getLimit()))
        .thenReturn(propertyEntityQuery);

    when(propertyEntityQuery.getResultList()).thenReturn(List.of(entity));

    final var properties = propertyRepository.findAll(page, filter);

    assertThat(properties).hasSize(1);

    verify(entityManager).createQuery(selectProperty, PropertyEntity.class);
    verify(propertyEntityQuery, times(10)).setParameter(anyString(), any());
    verify(propertyEntityQuery).setMaxResults(page.getLimit());
    verify(propertyEntityQuery).setFirstResult(page.getOffset() * page.getLimit());
    verifyNoMoreInteractions(entityManager, propertyEntityQuery);

  }

  private PropertyEntity createEntity() {
    final var propertyKind = faker.options().option(PropertyKind.class).getKind();
    final boolean isRent = faker.bool().bool();
    final var condominiumLeisureItems = createLeisureItemEntity();
    final var propertyLeisureItems = createLeisureItemEntity();

    return PropertyEntity.builder()
        .id(faker.number().numberBetween(1L, 100L))
        .buildingArea(faker.number().numberBetween(1, 100))
        .city(faker.address().city())
        .complement(faker.ancient().god())
        .condominiumLeisureItems(condominiumLeisureItems)
        .condominiumPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
        .country(faker.address().country())
        .description(faker.lorem().paragraph())
        .garage(faker.number().numberBetween(1, 10))
        .houseNumber(faker.number().numberBetween(1, 9999))
        .isCondominium(faker.bool().bool())
        .isFurnished(faker.bool().bool())
        .isRent(isRent)
        .isSale(!isRent)
        .landSize(faker.number().numberBetween(1, 100))
        .neighborhood(faker.rickAndMorty().location())
        .propertyKind(propertyKind)
        .propertyLeisureItems(propertyLeisureItems)
        .registration(faker.expression(faker.regexify(REGEX)))
        .rentPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
        .rooms(faker.number().numberBetween(1, 100))
        .salePrice(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
        .state(faker.address().stateAbbr())
        .streetName(faker.address().streetName())
        .taxes(BigDecimal.valueOf(faker.number().randomDouble(2, 1, 100)))
        .uuid(UUID.randomUUID())
        .zipcode(faker.address().zipCode())
        .build();
  }

  private Set<LeisureItemEntity> createLeisureItemEntity() {
    final var items = new HashSet<LeisureItemEntity>();
    for (int i = 0; i < 10; i++) {
      final var leisureItemEntity = LeisureItemEntity.builder()
          .item(faker.options().option(LeisureItem.class).getDescription())
          .leisureItemId(faker.number().numberBetween(1L, 100L))
          .uuid(UUID.randomUUID())
          .build();
      items.add(leisureItemEntity);
    }
    return items;
  }
}