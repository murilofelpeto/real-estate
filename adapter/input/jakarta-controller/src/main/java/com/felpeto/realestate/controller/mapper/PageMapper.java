package com.felpeto.realestate.controller.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.felpeto.realestate.controller.dto.input.PageDto;
import com.felpeto.realestate.domain.Page;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class PageMapper {

  public static Page toPage(final PageDto pageDto) {
    return Page.from(pageDto.getLimit(), pageDto.getOffset(), pageDto.getSort());
  }
}
