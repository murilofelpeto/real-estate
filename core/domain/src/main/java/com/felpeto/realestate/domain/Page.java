package com.felpeto.realestate.domain;

import com.felpeto.realestate.domain.vo.SortMode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
@EqualsAndHashCode
public final class Page {

  private static final String PLUS = "+";
  private static final String MINUS = "-";
  private static final String OFFSET_MESSAGE = "offset must be equal or greater than zero";
  private static final String LIMIT_MESSAGE = "limit must be between 1 and 100";
  private static final String SORT_MESSAGE =
      "sort must must not be empty or null and start with + or -";

  private final int limit;
  private final int offset;
  private final String sort;
  private final SortMode sortMode;

  private Page(final int limit, final int offset, final String sort) {
    if (limit < 1 || limit > 100) {
      throw new RuntimeException(LIMIT_MESSAGE);
    }

    if (offset < 0) {
      throw new RuntimeException(OFFSET_MESSAGE);
    }

    if (StringUtils.isBlank(sort) || (!sort.startsWith(PLUS) && !sort.startsWith(MINUS))) {

      throw new RuntimeException(SORT_MESSAGE);
    }

    this.limit = limit;
    this.offset = offset;
    this.sortMode = sort.startsWith(PLUS) ? SortMode.ASC : SortMode.DESC;
    this.sort = sort.substring(1);
  }

  public static Page from(final int limit, final int offset, final String sort) {
    return new Page(limit, offset, sort);
  }
}
