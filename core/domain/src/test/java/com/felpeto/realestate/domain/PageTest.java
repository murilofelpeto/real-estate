package com.felpeto.realestate.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.felpeto.realestate.domain.vo.SortMode;
import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import java.util.stream.Stream;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PageTest {

  private static Stream<Arguments> pagingException() {
    return Stream.of(
        arguments(0, 0, "", "limit must be between 1 and 100"),
        arguments(101, 0, "", "limit must be between 1 and 100"),
        arguments(1, -1, "", "offset must be equal or greater than zero"),
        arguments(1, 0, "", "sort must must not be empty or null and start with + or -"),
        arguments(1, 0, null, "sort must must not be empty or null and start with + or -"),
        arguments(1, 0, "uuid", "sort must must not be empty or null and start with + or -"));
  }

  @Test
  void validToString() {
    ToStringVerifier.forClass(Page.class).withClassName(NameStyle.SIMPLE_NAME).verify();
  }

  @Test
  void validEqualsAndHashcode() {
    EqualsVerifier.simple().forClass(Page.class).verify();
  }

  @ParameterizedTest
  @MethodSource("pagingException")
  void givenInvalidPagingWhenCallPagingFromThenThrowException(
      Integer limit, Integer offset, String sort, String message) {

    assertThatThrownBy(() -> Page.from(limit, offset, sort))
        .isInstanceOf(RuntimeException.class)
        .hasMessage(message);
  }

  @Test
  void whenLimitAndOffSetAreWithinLimitShouldReturnValidPaging() {
    assertThat(Page.from(3, 2, "+created_at"))
        .isNotNull()
        .isInstanceOf(Page.class)
        .satisfies(
            page -> {
              assertThat(page.getLimit()).isEqualTo(3);
              assertThat(page.getOffset()).isEqualTo(2);
              assertThat(page.getSort()).isEqualTo("created_at");
              assertThat(page.getSortMode()).isEqualTo(SortMode.ASC);
            });
  }

  @Test
  void shouldReturnValidPagingWhenLimitWithinLowerPossibleValue() {
    assertThat(Page.from(1, 0, "-created_at"))
        .isNotNull()
        .isInstanceOf(Page.class)
        .satisfies(
            page -> {
              assertThat(page.getLimit()).isEqualTo(1);
              assertThat(page.getOffset()).isZero();
              assertThat(page.getSort()).isEqualTo("created_at");
              assertThat(page.getSortMode()).isEqualTo(SortMode.DESC);
            });
  }

  @Test
  void shouldReturnValidPagingWhenLimitWithinHigherPossibleValue() {
    assertThat(Page.from(100, 0, "+created_at"))
        .isNotNull()
        .isInstanceOf(Page.class)
        .satisfies(
            page -> {
              assertThat(page.getLimit()).isEqualTo(100);
              assertThat(page.getOffset()).isZero();
              assertThat(page.getSort()).isEqualTo("created_at");
              assertThat(page.getSortMode()).isEqualTo(SortMode.ASC);
            });
  }

}