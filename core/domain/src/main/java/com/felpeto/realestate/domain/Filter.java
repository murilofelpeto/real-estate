package com.felpeto.realestate.domain;

import com.felpeto.realestate.domain.vo.City;
import com.felpeto.realestate.domain.vo.Country;
import com.felpeto.realestate.domain.vo.Neighborhood;
import com.felpeto.realestate.domain.vo.PropertyKind;
import com.felpeto.realestate.domain.vo.Size;
import com.felpeto.realestate.domain.vo.State;
import lombok.Builder;

@Builder
public record Filter(PropertyKind propertyKind,
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
