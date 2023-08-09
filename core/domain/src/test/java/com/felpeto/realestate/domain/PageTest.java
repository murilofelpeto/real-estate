package com.felpeto.realestate.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.felpeto.realestate.domain.exception.InvalidNumberLimitException;
import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.felpeto.realestate.domain.exception.UnprocessableEntityException;
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

  private static final String TARGET = Page.class.getSimpleName();
  private static final String LIMIT_FIELD = "limit";
  private static final String LIMIT_MESSAGE = "limit must be between 1 and 100";
  private static final String LIMIT_VIOLATION_MESSAGE = "When you want to paginate a query, you must provide a number between 1 and 100";
  private static final String OFFSET_FIELD = "offset";
  private static final String OFFSET_MESSAGE = "offset must be equal or greater than zero";
  private static final String OFFSET_VIOLATION_MESSAGE = "The page you can access must be greater than 0";
  private static final String SORT_FIELD = "sort";
  private static final String SORT_MESSAGE = "sort must must not be empty or null and start with + or -";
  private static final String SORT_VIOLATION_MESSAGE = "When you sort a query, the valid values are + for ASC and - for DESC";


  private static Stream<Arguments> pagingException() {
    return Stream.of(
        arguments(
            0,
            0,
            "",
            LIMIT_MESSAGE,
            LIMIT_FIELD,
            LIMIT_VIOLATION_MESSAGE,
            InvalidNumberLimitException.class),
        arguments(
            101,
            0,
            "",
            LIMIT_MESSAGE,
            LIMIT_FIELD,
            LIMIT_VIOLATION_MESSAGE,
            InvalidNumberLimitException.class),
        arguments(
            1,
            -1,
            "",
            OFFSET_MESSAGE,
            OFFSET_FIELD,
            OFFSET_VIOLATION_MESSAGE,
            InvalidNumberLimitException.class),
        arguments(
            1,
            0,
            "",
            SORT_MESSAGE,
            SORT_FIELD,
            SORT_VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        arguments(
            1,
            0,
            null,
            SORT_MESSAGE,
            SORT_FIELD,
            SORT_VIOLATION_MESSAGE,
            InvalidStringFormatException.class),
        arguments(
            1,
            0,
            "uuid",
            SORT_MESSAGE,
            SORT_FIELD,
            SORT_VIOLATION_MESSAGE,
            InvalidStringFormatException.class));
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
      final Integer limit,
      final Integer offset,
      final String sort,
      final String message,
      final String field,
      final String violationMessage,
      final Class<? extends UnprocessableEntityException> clazz) {

    final var exception = catchThrowableOfType(() -> Page.from(limit, offset, sort), clazz);

    assertThat(exception.getMessage()).isEqualTo(message);
    assertThat(exception.getParameter()).isEqualTo(field);
    assertThat(exception.getTarget()).isEqualTo(TARGET);
    assertThat(exception.getField()).isEqualTo(field);
    assertThat(exception.getViolationMessage()).isEqualTo(violationMessage);
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