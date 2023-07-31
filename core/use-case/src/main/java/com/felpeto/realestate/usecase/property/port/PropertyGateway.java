package com.felpeto.realestate.usecase.property.port;

import com.felpeto.realestate.domain.Filter;
import com.felpeto.realestate.domain.Page;
import com.felpeto.realestate.domain.property.Property;
import java.util.List;

public interface PropertyGateway {

  List<Property> findAll(final Page page, final Filter filter);

}
