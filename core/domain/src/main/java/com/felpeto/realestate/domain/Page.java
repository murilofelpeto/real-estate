package com.felpeto.realestate.domain;

import com.felpeto.realestate.domain.exception.InvalidNumberLimitException;
import com.felpeto.realestate.domain.exception.InvalidStringFormatException;
import com.felpeto.realestate.domain.vo.SortMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class Page {

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

  private static final String PLUS = "+";
  private static final String MINUS = "-";

  private final int limit;
  private final int offset;
  private final String sort;
  private final SortMode sortMode;

  private Page(final int limit, final int offset, final String sort) {
    this.limit = limit;
    this.offset = offset;
    this.sortMode = sort.startsWith(PLUS) ? SortMode.ASC : SortMode.DESC;
    this.sort = sort.substring(1);
  }

  public static Page from(final int limit, final int offset, final String sort) {
    if (limit < 1 || limit > 100) {
      throw new InvalidNumberLimitException(LIMIT_MESSAGE,
          LIMIT_FIELD,
          TARGET,
          LIMIT_FIELD,
          LIMIT_VIOLATION_MESSAGE);
    }

    if (offset < 0) {
      throw new InvalidNumberLimitException(OFFSET_MESSAGE,
          OFFSET_FIELD,
          TARGET,
          OFFSET_FIELD,
          OFFSET_VIOLATION_MESSAGE);
    }

    if (StringUtils.isBlank(sort) || (!sort.startsWith(PLUS) && !sort.startsWith(MINUS))) {
      throw new InvalidStringFormatException(SORT_MESSAGE,
          SORT_FIELD,
          TARGET,
          SORT_FIELD,
          SORT_VIOLATION_MESSAGE);
    }

    return new Page(limit, offset, sort);
  }
}
