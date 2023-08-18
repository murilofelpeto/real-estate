package com.felpeto.realestate.jpa.property;

import static com.felpeto.realestate.jpa.property.mapper.PropertyEntityMapper.toProperties;

import com.felpeto.realestate.domain.Filter;
import com.felpeto.realestate.domain.Page;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.Size;
import com.felpeto.realestate.domain.vo.State;
import com.felpeto.realestate.jpa.property.entity.PropertyEntity;
import com.felpeto.realestate.usecase.property.port.PropertyGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Function;

@Named
@ApplicationScoped
public class PropertyRepository implements PropertyGateway {

  private static final String SELECT_FILTERED_PROPERTY = """
      select p from PropertyEntity p
      where (p.propertyKind = :propertyKind OR :propertyKind is null)
      and (p.country = :country OR :country is null)
      and (p.state = :state OR :state is null)
      and (p.city = :city OR :city is null)
      and (p.neighborhood = :neighborhood OR :neighborhood is null)
      and (p.landSize = :size OR :size is null)
      and (p.isRent = :isRent OR :isRent is null)
      and (p.isSale = :isSale OR :isSale is null)
      and (p.isFurnished = :isFurnished OR :isFurnished is null)
      and (p.garage = :garage OR :garage is null)
      order by p.{sort} {sortMode}
      """;


  private final EntityManager entityManager;

  @Inject
  public PropertyRepository(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public List<Property> findAll(final Page page, final Filter filter) {
    final var selectProperty = SELECT_FILTERED_PROPERTY
        .replace("{sort}", page.getSort())
        .replace("{sortMode}", page.getSortMode().name());

    final var query = entityManager.createQuery(selectProperty, PropertyEntity.class);
    addQueryParameters(query, filter);
    query.setFirstResult(page.getOffset() * page.getLimit());
    query.setMaxResults(page.getLimit());

    final var propertyEntityList = query.getResultList();
    return toProperties(propertyEntityList);
  }

  private void addQueryParameters(
      final TypedQuery<PropertyEntity> query,
      final Filter filter) {

    query.setParameter("propertyKind", map(filter.propertyKind(), PropertyKind::name));
    query.setParameter("country", map(filter.country(), Country::getValue));
    query.setParameter("state", map(filter.state(), State::getValue));
    query.setParameter("city", map(filter.city(), City::getValue));
    query.setParameter("neighborhood", map(filter.neighborhood(), Neighborhood::getValue));
    query.setParameter("size", map(filter.size(), Size::getValue));
    query.setParameter("garage", map(filter.garage(), Size::getValue));
    query.setParameter("isRent", map(filter.isRent(), Boolean::valueOf));
    query.setParameter("isSale", map(filter.isSale(), Boolean::valueOf));
    query.setParameter("isFurnished", map(filter.isFurnished(), Boolean::valueOf));
  }

  private <T, R> R map(T value, Function<T, R> mapper) {
    return value == null ? null : mapper.apply(value);
  }
}
