package com.felpeto.realestate.domain.property.vo;

import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.State;
import lombok.Builder;

@Builder
public record PropertyFilter(PropertyKind propertyKind,
                             Country country,
                             State state,
                             City city,
                             Neighborhood neighborhood,
                             Size size,
                             Size rooms,
                             Boolean isRent,
                             Boolean isSale,
                             Boolean isFurnished,
                             Size garage) {

}
