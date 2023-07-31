package com.felpeto.realestate.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.felpeto.realestate.controller.dto.input.PageDto;
import com.felpeto.realestate.domain.vo.SortMode;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageMapperTest {

  private final Faker faker = new Faker();

  @Test
  @DisplayName("Given pageDto when map to Page then return valid Page")
  void givenPageDtoWhenMapToPageThenReturnValidPage() {
    final var pageDto = createPage();

    final var page = PageMapper.toPage(pageDto);

    assertThat(page.getLimit()).isEqualTo(pageDto.getLimit());
    assertThat(page.getOffset()).isEqualTo(pageDto.getOffset());
    assertThat(page.getSort()).isEqualTo("total_price");
    assertThat(page.getSortMode()).isEqualTo(SortMode.ASC);
  }

  private PageDto createPage() {
    return PageDto.builder()
        .limit(2)
        .offset(2)
        .sort("+total_price")
        .build();
  }
}