package com.felpeto.realestate.usecase.property.port;

import com.felpeto.realestate.domain.Page;
import com.felpeto.realestate.domain.property.Property;
import com.felpeto.realestate.domain.property.vo.PropertyFilter;
import java.util.List;

public interface PropertyGateway {

  List<Property> findAll(final Page page, final PropertyFilter propertyFilter);

}
