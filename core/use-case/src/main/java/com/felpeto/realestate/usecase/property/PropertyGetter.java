package com.felpeto.realestate.usecase.property;

import com.felpeto.realestate.domain.Filter;
import com.felpeto.realestate.domain.Page;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.usecase.property.port.PropertyGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class PropertyGetter {

  private final PropertyGateway propertyGateway;

  @Inject
  public PropertyGetter(final PropertyGateway propertyGateway) {
    this.propertyGateway = propertyGateway;
  }

  public List<Property> findAll(final Page page, final Filter filter) {
    return propertyGateway.findAll(page, filter);
  }
}
